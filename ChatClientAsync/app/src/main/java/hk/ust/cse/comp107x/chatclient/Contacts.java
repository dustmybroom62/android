package hk.ust.cse.comp107x.chatclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contacts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Toolbar toolbar;
    String[] names;

    ListView friendView;

    ContactArrayAdapter mArrayAdapter;

    // This class stores all the information about your friend
    public class FriendInfo {
        int id;
        String name;
        String statusMsg;
        String imageURL;
    }

    List<FriendInfo> friendInfoList = null;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mContext = this;

        mArrayAdapter = new ContactArrayAdapter(this, friendInfoList);

        // If you are using a ListView widget, then your activity should implement
        // the onItemClickListener. Then you should set the OnItemClickListener for
        // teh ListView.
        friendView = (ListView) findViewById(R.id.friendListView);
        friendView.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Should set up to add new contacts", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Start the AsyncTask to process the Json string in the background and then initialize the listview

        // TODO create Asynctask for doing the background processing
        FriendsProcessor mytask = new FriendsProcessor();
        mytask.execute("friendsjson.txt");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Intent mIntent = new Intent(this,ChatClient.class);
        TextView friendName = (TextView) view.findViewById(R.id.friendName);
        mIntent.putExtra(getString(R.string.friend), friendName.getText().toString());
        startActivity(mIntent);

    }

    // This AsyncTask processes the Json string by reading it from a file in the assets folder and
    // then converts the string into a list of FriendInfo objects. You will also see the use of
    // a progress dialog to show that work is being processed in the background.

    private class FriendsProcessor extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        int delay = 5000 ; // ms

        public FriendsProcessor() {
            super();
        }

        // The onPreExecute is executed on the main UI thread before background processing is
        // started. In this method, we start the progressdialog.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Show the progress dialog on the screen
            progressDialog = ProgressDialog.show(mContext, "Wait!","Downloading Friends List");
        }

        // This method is executed in the background and will return a result to onPostExecute
        // method. It receives the file name as input parameter.
        @Override
        protected Integer doInBackground(String... strings) {

            // Open an input stream to read the file
            InputStream inputStream;
            BufferedReader in;

            // this try/catch is used to create a simulated delay for doing the background
            // processing so that you can see the progress dialog on the screen. If the
            // data to be processed is large, then you don't need this.
            try {
                // Pretend downloading takes a long time
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Now we read the file, line by line and construct the
            // Json string from the information read in.
            try {

                // TODO read the file and process the string
                inputStream = mContext.getAssets().open(strings[0]);


                in = new BufferedReader(new InputStreamReader(inputStream));

                String readLine;
                StringBuffer buf = new StringBuffer();

                while ((readLine = in.readLine()) != null) {
                    buf.append(readLine);
                }

                // Convert the read in information to a Json string
                String infoString = buf.toString();

                // now process the string using the method that we implemented in the previous exercise
                processFriendInfo(infoString);

                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return (Integer) 1;
        }

        // This method will be executed on the main UI thread and can access the UI and update
        // the listview. We dismiss the progress dialog after updating the listview.
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            mArrayAdapter = new ContactArrayAdapter(mContext, friendInfoList);
            friendView.setAdapter((ListAdapter) mArrayAdapter);

            progressDialog.dismiss();
        }

        // This method is called if we cancel the background processing
        @Override
        protected void onCancelled() {
            super.onCancelled();

            progressDialog.dismiss();
        }
    }

    // This class processes the Json string and converts it into a list of FriendInfo objects
    // We make use of the Gson library to do this automatically
    private void processFriendInfo(String infoString) {

        // Create a new Gson object
        // TODO Create a Gson Object
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        // Use the Gson library to automatically process the string and convert it into
        // the list of FriendInfo objects. The use of the library saves you the need for
        // writing your own code to process the Json string
        // TODO convert the string to a list of FriendInfo objects using Gson
        friendInfoList = new ArrayList<FriendInfo>();
        friendInfoList = Arrays.asList(gson.fromJson(infoString, FriendInfo[].class));
    }
}
