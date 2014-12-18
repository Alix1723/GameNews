package com.student.alix.gamenews;

import android.content.Context;
import android.os.AsyncTask;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * FeedParserAsync
 * (Code partially duplicated from Labs by Bobby Law)
 */

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
    }
}
