package edu.galileo.android.asynctaskloader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Larry on 3/23/2018.
 */

public class NamesAsyncTaskLoader extends AsyncTaskLoader<ArrayList<String>> {
    public NamesAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<String> loadInBackground() {
        return loadNamesFromDB();
    }

    private ArrayList<String> loadNamesFromDB(){
        ArrayList<String> names = new ArrayList<>();
        names.add("John");
        names.add("Mary");
        names.add("Emma");
        names.add("Yosef");
        names.add("Noah");
        names.add("Andrea");
        return names;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad(); // We are forcing the call of loadInBackground
    }
}
