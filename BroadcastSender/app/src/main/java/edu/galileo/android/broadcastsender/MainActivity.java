package edu.galileo.android.broadcastsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText etMessage = (EditText) findViewById(R.id.etMessage);
                String message = etMessage.getText().toString();
                Intent intent = new Intent();
                intent.setAction("com.beginner.micromaster.NEW_BROADCAST");
                intent.putExtra("broadcast", message);
                sendBroadcast(intent);
            }
        });
    }
}
