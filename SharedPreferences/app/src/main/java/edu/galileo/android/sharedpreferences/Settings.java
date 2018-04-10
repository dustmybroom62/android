package edu.galileo.android.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    private float[] conversionValues = {100f, 0.001f, 39.3701f};
    private String[] conversionLabels = {"Centimeter", "Kilometer", "Inch"};
    private String[] conversionLabelsShort = {"cm", "km", "in"};

    // view
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        createRadioButtons();
    }

    private void createRadioButtons() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rgConverterOptions);

        for (int i = 0; i < conversionLabels.length; i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(getString(R.string.option, conversionLabels[i]));
            final float value = conversionValues[i];
            final String labelShort = conversionLabelsShort[i];

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActualUnits(value, labelShort);
                }
            });
            mRadioGroup.addView(radioButton);

            // Selected
            if (value == getActualUnits()){
                radioButton.setChecked(true);
            }
        }
    }

    private float getActualUnits(){
        SharedPreferences mSharedPreferences = getSharedPreferences("ActualUnits", MODE_PRIVATE);
        return mSharedPreferences.getFloat("Unit", 100f);
    }

    private void setActualUnits(float value, String labelShort) {
        // Shared Preferences
        SharedPreferences mSharedPreferences = getSharedPreferences("ActualUnits", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        // Store values in Pairs (KEY, VALUE)
        mEditor.putFloat("Unit", value);
        mEditor.putString("Label", labelShort);
        // Apply Changes
        mEditor.apply();
    }
}
