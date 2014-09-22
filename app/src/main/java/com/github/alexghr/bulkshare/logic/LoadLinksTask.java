package com.github.alexghr.bulkshare.logic;

import android.content.Context;
import android.os.AsyncTask;

import com.github.alexghr.bulkshare.db.DBAccess;
import com.github.alexghr.bulkshare.db.Link;

import java.util.List;

public class LoadLinksTask extends AsyncTask<Integer, Void, List<Link>> {

    private final DBAccess dbAccess;
    private final OnFinishedLoadingListener listener;

    public LoadLinksTask(final Context context, final OnFinishedLoadingListener listener) {
        dbAccess = new DBAccess(context);
        this.listener = listener;
    }

    @Override
    protected List<Link> doInBackground(Integer... params) {
        if (params.length == 0) {
            return null;
        }
        return dbAccess.getLinks(params[0], false);
    }

    @Override
    protected void onPostExecute(List<Link> links) {
        if (listener != null) {
            listener.finishedLoading(links);
        }
    }

    public static interface OnFinishedLoadingListener {

        void finishedLoading(List<Link> links);
    }
}
