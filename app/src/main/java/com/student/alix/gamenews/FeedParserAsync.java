package com.student.alix.gamenews;

/**
 * Created by Alix on 04/12/2014.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class FeedParserAsync extends AsyncTask<String, Integer, ArrayList<NewsDataItem>>
{
    private Context appContext;
    private String urlRSSToParse;

    public FeedParserAsync(Context currentAppContext, String urlRSS)
    {
        appContext = currentAppContext;
        urlRSSToParse = urlRSS;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(appContext, "Parsing initialized...",Toast.LENGTH_SHORT);
    }

    //Parsing
    @Override
    protected ArrayList<NewsDataItem> doInBackground(String... params)
    {
        ArrayList<NewsDataItem> parsedData;
        FeedRSSParser rssParser = new FeedRSSParser();

        try
        {
           rssParser.parseRSSData(urlRSSToParse);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }

        parsedData = rssParser.getRSSDataItems();

        return parsedData;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsDataItem> result)
    {
        Toast.makeText(appContext,"Done parsing!", Toast.LENGTH_SHORT);
    }
}
