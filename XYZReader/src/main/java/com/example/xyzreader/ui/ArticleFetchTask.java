package com.example.xyzreader.ui;

import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Html;

import com.example.xyzreader.data.ArticleLoader;

public class ArticleFetchTask extends AsyncTask<String, Void, String>{

    ArticleDetailFragment container;
    Cursor mCursor;
    public ArticleFetchTask(ArticleDetailFragment f, Cursor c) {
        this.container = f;
        this.mCursor = c;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            result = Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY).replaceAll("(\r\n|\n)", "<br />")).toString();
        }catch(Exception ex) { result = ""; }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        container.showProgressBar();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // The activity can be null if it is thrown out by Android while task is running!
        if(container!=null && container.getActivity()!=null) {
            container.hideProgressBar();
            container.populateResult(result);
            this.container = null;
        }
    }
}