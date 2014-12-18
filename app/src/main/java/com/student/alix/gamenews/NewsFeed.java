package com.student.alix.gamenews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NewsFeed extends Activity {

    TextView DebugOut;
    FeedParserAsync feedParserTask;
    ArrayList<NewsDataItem> NewsDataArray = new ArrayList<NewsDataItem>();
    ListView newsListView;
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //Debug out
        //DebugOut = (TextView)findViewById(R.id.text_debug);
        loadingSpinner = (ProgressBar)findViewById(R.id.loading_spinner);
        newsListView = (ListView)findViewById(R.id.news_list_view);

        //Read feed
        ReadRSSFeed();
    }

    //Run RSS read/parse task
    void ReadRSSFeed() {

        //DebugOut.setText("Loading..."); //Loading text
        loadingSpinner.setVisibility(View.VISIBLE);

        NewsDataArray = new ArrayList<NewsDataItem>();                              //Assign empty arraylist
        String FeedURL = "http://www.psnation.com/category/news/ps4-news/feed/";    //String to access RSS feed from
        feedParserTask = new FeedParserAsync(this, FeedURL);                        //Feed parser object

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
                                 //Debug
                                 //DebugOut.setText(NewsDataArray.get(1).getDescription());
                             } catch (InterruptedException ex) {
                                 ex.printStackTrace();
                             } catch (ExecutionException ex) {
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
        //newsListView
        ArrayAdapter<NewsDataItem> arrAdapt = new ArrayAdapter<NewsDataItem>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                NewsDataArray);

        loadingSpinner.setVisibility(View.GONE);
        newsListView.setAdapter(arrAdapt);

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

        DebugOut.setText("Done.");
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
        return super.onOptionsItemSelected(item);
    }
}
