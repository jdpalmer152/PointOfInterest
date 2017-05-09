package com.example.a2palmj38.pointsofinterest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class PoiActivity extends Activity implements View.OnClickListener
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker);

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        Bundle bundle = new Bundle();

        EditText POIName = (EditText) findViewById(R.id.name);
        bundle.putString("2palmj38.name", POIName.getText().toString());

        EditText POIType = (EditText) findViewById(R.id.type);
        bundle.putString("2palmj38.type", POIName.getText().toString());

        EditText POIDesc = (EditText) findViewById(R.id.description);
        bundle.putString("2palmj38.desc", POIDesc.getText().toString());

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }
}
