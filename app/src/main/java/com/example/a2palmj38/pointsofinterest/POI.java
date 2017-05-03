package com.example.a2palmj38.pointsofinterest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class POI extends Activity implements View.onClickListener
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

    }

    public void onClick(View view)
    {
        EditText latitudeEditText = (EditText) findViewById(R.id.latitudeEditText);
        double latitude = Double.parseDouble(latitudeEditText.getText().toString());

        EditText longitudeEditText = (EditText) findViewById()
    }
}
