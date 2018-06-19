package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.ufersa.qwater.R;

public class ClassificationDialogueActivity extends AppCompatActivity {

    private TextView classificationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_dialogue);

        initiate();

        updateUI(getIncomingIntent());
    }

    private void updateUI(String classification) {
        switch (classification){

            case "S1":


                break;
            case "S2":


                break;
            case "S3":


                break;
            case "S4":


                break;
            case "C1":


                break;
            case "C2":


                break;
            case "C3":


                break;
            case "C4":


                break;

            default:

                break;

        }
    }

    private String getIncomingIntent(){
        if(getIntent().hasExtra("classification")) {

           return getIntent().getStringExtra("classification");
        }
        return "error 404";
    }

    private void initiate(){

        classificationTextView = findViewById(R.id.TEXTVIEW_CLASSIFICATION);
    }
}
