package com.student.alix.gamenews;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NewsFeed extends Activity {

    TextView DebugOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //Debug out
        DebugOut = (TextView)findViewById(R.id.text_debug);
        DebugOut.setText("Loading...");

        //String to access RSS feed from
        String FeedURL = "http://www.psnation.com/category/news/ps4-news/feed/";

        //Array to store news data in
        ArrayList<NewsDataItem> NewsDataArray = new ArrayList<NewsDataItem>();

        //Feed parser object
        FeedParserAsync fpa = new FeedParserAsync(this, FeedURL);

        try
        {   //Run the background task
            NewsDataArray = fpa.execute("").get();
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
        catch(ExecutionException ex)
        {
            ex.printStackTrace();
        }

        //Debug
        DebugOut.setText(NewsDataArray.get(1).getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
