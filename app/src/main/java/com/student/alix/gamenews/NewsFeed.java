package com.student.alix.gamenews;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NewsFeed extends Activity {

    TextView DebugOut;
    FeedParserAsync feedParserTask;
    ArrayList<NewsDataItem> NewsDataArray = new ArrayList<NewsDataItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //Debug out
        DebugOut = (TextView)findViewById(R.id.text_debug);
        DebugOut.setText("");

        //Toast
        //Toast.makeText(this,"Loading...", Toast.LENGTH_SHORT);

        //Read feed
        ReadRSSFeed();
    }

    //Run RSS read/parse task
    void ReadRSSFeed()
    {

        NewsDataArray = new ArrayList<NewsDataItem>();                              //Assign empty arraylist
        String FeedURL = "http://www.psnation.com/category/news/ps4-news/feed/";    //String to access RSS feed from
        feedParserTask = new FeedParserAsync(this, FeedURL);                        //Feed parser object

        //Run the background task
        feedParserTask.execute("");

        //Handler to run task and update progress
        final Handler hnd = new Handler();
        //Runnable
        hnd.post(new Runnable()
            {
                public void run()
                {
                    //Task finished
                    if(feedParserTask.getStatus() == AsyncTask.Status.FINISHED)
                    {
                        //Retrieve result from task
                        try
                        {
                            NewsDataArray = feedParserTask.get();

                            //Debug
                            DebugOut.setText(NewsDataArray.get(1).getDescription());
                        }
                        catch(InterruptedException ex)
                        {
                            ex.printStackTrace();
                        }
                        catch(ExecutionException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {   //Run this again every 200ms until task is complete
                        hnd.postDelayed(this, 200);
                    }
                }
        });


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
