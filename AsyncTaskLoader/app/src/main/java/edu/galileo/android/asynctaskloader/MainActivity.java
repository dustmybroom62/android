package edu.galileo.android.asynctaskloader;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> nameList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rvName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(nameList);
        recyclerView.setAdapter(recyclerViewAdapter);

        // loader
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int i, Bundle bundle) {
        Loader loader = new NamesAsyncTaskLoader(getApplicationContext());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        nameList.clear();
        nameList.addAll(data);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        nameList.clear();
    }
}
