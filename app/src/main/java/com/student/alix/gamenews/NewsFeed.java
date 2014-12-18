package com.student.alix.gamenews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * NewsFeed (Activity)
 */

public class NewsFeed extends Activity {

    TextView DebugOut;
    FeedParserAsync feedParserTask;
    ArrayList<NewsDataItem> NewsDataArray = new ArrayList<NewsDataItem>();
    ListView newsListView;
    ProgressBar loadingSpinner;
    SharedPreferences prefs;
    String platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //Loading indicator
        loadingSpinner = (ProgressBar)findViewById(R.id.loading_spinner);

        //Listview to output news items to
        newsListView = (ListView)findViewById(R.id.news_list_view);

        //Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Read feed
        ReadRSSFeed();
    }

    //Run RSS read/parse task
    void ReadRSSFeed() {
        //Determine platform from preferences
        platform = prefs.getString("platform","none");

        //DebugOut.setText("Loading..."); //Loading text
        loadingSpinner.setVisibility(View.VISIBLE); //Loading spinner

        //Assign empty arraylist
        NewsDataArray = new ArrayList<NewsDataItem>();

        //String to access RSS feed from
        String FeedURL = "";
        switch (platform)
        {
            case "xbox":
                FeedURL = "http://www.totalxbox.com/rss/feed.php?priority=1&limit=10"; break;
            case "ps":
                FeedURL = "http://www.psnation.com/category/news/ps4-news/feed/"; break;
            case "nint":
                FeedURL = "http://www.nintendolife.com/feeds/news"; break;
        }

        //Feed parser object
        feedParserTask = new FeedParserAsync(this, FeedURL);

        //Run the background task
        feedParserTask.execute("");

        //Handler to run task and update progress
        final Handler hnd = new Handler();
        //Runnable
        hnd.post(new Runnable() {
                     public void run() {
                         //Check if the task is finished
                         if (feedParserTask.getStatus() == AsyncTask.Status.FINISHED) {
                             //Retrieve result from task
                             try {
                                 NewsDataArray = feedParserTask.get();
                                 DisplayNews();
                             }
                             catch (InterruptedException ex)
                             {
                                 ex.printStackTrace();
                             }
                             catch (ExecutionException ex)
                             {
                                 ex.printStackTrace();
                             }
                         } else {   //Run this again every 200ms until task is complete
                             hnd.postDelayed(this, 200);
                         }
                     }
                 }
        );
    }

    //Populate list with news data
    void DisplayNews()
    {
        //Array adapter
        ArrayAdapter<NewsDataItem> arrAdapt = new ArrayAdapter<NewsDataItem>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                NewsDataArray);

        loadingSpinner.setVisibility(View.GONE); //Hide loading spinner
        newsListView.setAdapter(arrAdapt); //Set the listview's adapter to this array adapter

        //List view click listener
        newsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String link = NewsDataArray.get(position).getLink();
                        Log.e("n",link);

                        //Open URL in default browser
                        Intent openLink = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        startActivity(openLink);
                    }
                }
        );
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
        if(id == R.id.action_refresh)
        {
            newsListView.setAdapter(null);
            ReadRSSFeed();
        }
        else if(id == R.id.action_settings)
        {
            //Open preference menu
            Intent openPrefsIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
            startActivity(openPrefsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
