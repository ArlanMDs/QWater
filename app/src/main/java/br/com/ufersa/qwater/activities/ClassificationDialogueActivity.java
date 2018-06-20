package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.ufersa.qwater.R;

public class ClassificationDialogueActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView classificationTextView;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_dialogue);

        initiate();

        updateUI(getIncomingIntent());
    }

    private void updateUI(String classification) {
        int resourceId = this.getResources().getIdentifier(classification, "string", this.getPackageName());
        classificationTextView.setText(resourceId);
    }

    private String getIncomingIntent(){
        if(getIntent().hasExtra("classification")) {

           return getIntent().getStringExtra("classification");
        }
        return "classification_error";
    }

    private void initiate(){

        classificationTextView = findViewById(R.id.TEXTVIEW_CLASSIFICATION);
        exit = findViewById(R.id.CLASSIFICATION_EXIT_BUTTON);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.CLASSIFICATION_EXIT_BUTTON:
                finish();

            break;

        }
    }
}
