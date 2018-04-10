package hk.ust.cse.comp107x.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class ChatClient extends AppCompatActivity implements View.OnClickListener {

    Button sendButton;
    EditText messageText;
    ListView messageList;
    MyAdapter mAdapter = null;
    ArrayList<Message> messages = null;
    int in_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);

        messageText = (EditText) findViewById(R.id.messageText);

        // TODO

        // Get the reference to the ListView on the UI
        messageList = (ListView) findViewById(R.id.messageList);

        // Create a new ArrayList of Message objects
        messages = new ArrayList<Message>();

        // Create a new custom ArrayAdapter. This custom Adapter is
        // implemented by us, and illustrates how an ArrayAdapter is
        // constructed given the data (from the Message objects)
        mAdapter = new MyAdapter(this, messages);

        // Set the ListView's adapter to be the adapter that we just constructed.
        messageList.setAdapter((ListAdapter) mAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sendButton:

                String messString = messageText.getText().toString();

                // If the message is not empty string
                if (!messString.equals("")) {

                    // TODO

                    // Create a new Message object and initialize it with the information
                    Message message = new Message("", messString, true, new Date());

                    // Add the Message object to the ArrayList
                    messages.add(message);

                    // Notify the adapter that the data has changed due to the addition
                    // of a new message object. This triggers an update of the ListView
                    mAdapter.notifyDataSetChanged();
                    sendMessage();
                    message = null;
                    messageText.setText("");
                }

                break;

            default:
                break;
        }
    }

    public void sendMessage() {

        String[] incoming = {"Hey, How's it going?",
                "Super! Let's do lunch tomorrow",
                "How about Mexican?",
                "Great, I found this new place around the corner",
                "Ok, see you at 12 then!"};

        if (in_index < incoming.length) {
            Message message = new Message("John", incoming[in_index], false,  new Date());
            messages.add(message);
            in_index++;
            mAdapter.notifyDataSetChanged();
        }

    }
}

