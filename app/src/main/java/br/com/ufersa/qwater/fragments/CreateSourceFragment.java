package br.com.ufersa.qwater.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.ufersa.qwater.BuildConfig;
import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.MainActivity;
import br.com.ufersa.qwater.beans.WaterSource;
import br.com.ufersa.qwater.database.AppDatabase;

// referências: https://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
// https://android.jlelse.eu/room-store-your-data-c6d49b4d53a3

public class CreateSourceFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText nameTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView addressTextView;
    private Button sourceOKButton;
    private Button startUpdatesButton;
    private ConstraintLayout layoutAddress;
    private Spinner spinner;
    private AppDatabase appDatabase;
    private View view;

    private static final String TAG = MainActivity.class.getSimpleName();

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_water_source, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initiate();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
        getActivity().setTitle(getString(R.string.adicionar_fonte));




    }

    private void initiate(){

        spinner = view.findViewById(R.id.SOURCES_SPINNER);

        //prepara o bd
        appDatabase = AppDatabase.getInstance(view.getContext());

        nameTextView = view.findViewById(R.id.EDIT_SOURCE_NAME);
        latitudeTextView = view.findViewById(R.id.EDIT_SOURCE_LATITUDE);
        longitudeTextView = view.findViewById(R.id.EDIT_SOURCE_LONGITUDE);
        addressTextView = view.findViewById(R.id.EDIT_SOURCE_ADDRESS);
        layoutAddress = view.findViewById(R.id.LAYOUT_SOURCE_ADDRESS);

        startUpdatesButton = view.findViewById(R.id.BUTTON_START_LOCATION_UPDATES);
        startUpdatesButton.setOnClickListener(this);

        sourceOKButton = view.findViewById(R.id.SOURCE_OK_BUTTON);
        sourceOKButton.setOnClickListener(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        mSettingsClient = LocationServices.getSettingsClient(view.getContext());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SOURCE_OK_BUTTON:

                mRequestingLocationUpdates = false;
                stopLocationUpdates();

                AsyncInsert asyncInsert = new AsyncInsert();
                asyncInsert.execute();
                break;

            case R.id.BUTTON_START_LOCATION_UPDATES:

                // Requesting ACCESS_FINE_LOCATION using Dexter library
                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                mRequestingLocationUpdates = true;
                                startLocationUpdates();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    // open device settings when the permission is
                                    // denied permanently
                                    openSettings();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

                break;
        }

    }

    //TODO resolver o problema do leak https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private class AsyncInsert extends AsyncTask<Void, Void, Void> {

        private String name;
        private String type;
        private double latitude;
        private double longitude;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //TODO pedir número mínimo de caracteres para o nome?
            // se não houver campo nulo, cria o objeto e adiciona-o no bd
            if (nameTextView.getText().length() > 0 && latitudeTextView.getText().length() > 0 && longitudeTextView.getText().length() > 0) {

                name = String.valueOf(nameTextView.getText());
                type = String.valueOf(spinner.getSelectedItem().toString());
                latitude = Double.valueOf(String.valueOf(latitudeTextView.getText()));
                longitude = Double.valueOf(String.valueOf(longitudeTextView.getText()));
            }else {
                Toast.makeText(getActivity(), R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show();
                this.cancel(true);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.waterSourceDao().insert(new WaterSource(name, type, latitude, longitude));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getActivity(), getString(R.string.local_adicionado), Toast.LENGTH_SHORT).show();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new CreateReportFragment())
                    .commit();
        }
    }


    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }


        }

        updateLocationUI();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Spinner spinner = (Spinner) parent;
//        if(spinner.getId() == R.id.SOURCES_SPINNER)
//        {
//            switch (position) {
//                case 0:
//
//                    break;
//                case 1:
//
//                    break;
//                case 2:
//
//                    break;
//                case 3:
//
//                    break;
//                case 4:
//
//                    break;
//            }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            //mostra o layout do endereço
            layoutAddress.setVisibility(View.VISIBLE);
            //permite dar ok
            sourceOKButton.setEnabled(true);

            double latitude = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();

            latitudeTextView.setText(String.valueOf(latitude));
            longitudeTextView.setText(String.valueOf(longitude));

            // atualiza o endereço aproximado
            try {
                Geocoder geoCoder = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    geoCoder = new Geocoder(getContext(), Locale.getDefault());
                }
                List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++)
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");

                    addressTextView.setText(strReturnedAddress.toString());

                } else
                    addressTextView.setText("No Address returned!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }

            // giving a blink animation on TextView
            addressTextView.setAlpha(0);
            addressTextView.animate().alpha(1).setDuration(300);
            latitudeTextView.setAlpha(0);
            latitudeTextView.animate().alpha(1).setDuration(300);
            longitudeTextView.setAlpha(0);
            longitudeTextView.animate().alpha(1).setDuration(300);

        }

        toggleButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
            startUpdatesButton.setEnabled(false);

        } else {
            startUpdatesButton.setEnabled(true);
            sourceOKButton.setEnabled(false);

        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getActivity(), "Obtendo coordenadas...", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    private void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(), "Parando de obter coordenadas.", Toast.LENGTH_SHORT).show();
                        toggleButtons();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

}
