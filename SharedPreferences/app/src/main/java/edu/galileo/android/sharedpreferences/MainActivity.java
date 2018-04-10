package edu.galileo.android.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView tvActualUnits;
    private TextView tvResult;
    private EditText etMeters;
    private Button bnConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find view items
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvActualUnits = (TextView) findViewById(R.id.tvActualUnits);
        tvResult = (TextView) findViewById(R.id.tvResult);
        etMeters = (EditText) findViewById(R.id.etMeters);
        bnConverter = (Button) findViewById(R.id.btConverter);

        // Toolbar
        setSupportActionBar(mToolbar);

        // By Default
        //setDefaultText();

        bnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the conversion
                Convert(Float.parseFloat(etMeters.getText().toString()));
            }
        });
    }

    private void setDefaultText() {
        tvActualUnits.setText(getString(R.string.actual_units, getActualUnitsLabel() ));
        //etMeters.setText("");
        tvResult.setText(getString(R.string.result, "0"));
    }

    private String getActualUnitsLabel(){
        SharedPreferences mSharedPreferences = getSharedPreferences("ActualUnits", MODE_PRIVATE);
        return mSharedPreferences.getString("Label", "cm");
    }

    private float getActualUnits(){
        SharedPreferences mSharedPreferences = getSharedPreferences("ActualUnits", MODE_PRIVATE);
        return mSharedPreferences.getFloat("Unit", 100f);
    }

    private void Convert(float value) {
        tvResult.setText(getString(R.string.result, String.valueOf(value * getActualUnits())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, Settings.class));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onResume() {
        setDefaultText();
        float val = 0f;
        try {
            val = Float.parseFloat(etMeters.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Convert(val);

        super.onResume();
    }
}
