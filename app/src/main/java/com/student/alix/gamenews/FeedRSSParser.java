package com.student.alix.gamenews;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * FeedRSSParser
 * (Code partially duplicated from Labs by Bobby Law)
 */

public class FeedRSSParser {

    private ArrayList<NewsDataItem> newsData = new ArrayList<NewsDataItem>();
    NewsDataItem tempData = null;

    public ArrayList<NewsDataItem> getRSSDataItems()
    {
        return this.newsData;
    }

    //Constructor
    public FeedRSSParser() {

    }

    //Parser
    public void parseRSSDataItems(XmlPullParser theParser, int theEventType)
    {
        try
        {
            while(theEventType != XmlPullParser.END_DOCUMENT) {
                //Start tag
                if (theEventType == XmlPullParser.START_TAG) {
                    //New item
                    if(theParser.getName().equalsIgnoreCase("item"))
                    {
                        tempData = new NewsDataItem("","","");
                    }
                    else if(tempData != null) { //Items exists
                        //Title
                        if (theParser.getName().equalsIgnoreCase("title")) {
                            tempData.setTitle(theParser.nextText());
                        }
                        //Descriptions
                        else if (theParser.getName().equalsIgnoreCase("description")) {
                            tempData.setDescription(theParser.nextText());
                        }
                        //Links
                        else if (theParser.getName().equalsIgnoreCase("link")) {
                            tempData.setLink(theParser.nextText());
                        }
                    }
                }
                else if(theEventType == XmlPullParser.END_TAG)
                {
                    if(theParser.getName().equalsIgnoreCase("item"))
                    {
                        //Finished with this item, go to next
                        newsData.add(tempData);
                        tempData = null;
                        Log.e("n","Added entry");
                    }
                }
            theEventType = theParser.next();
            }
        }
        catch(XmlPullParserException exc)
        {
            Log.e("n","Parsing error: " + exc.toString());
        }
        catch(IOException exc)
        {
            Log.e("n","IO error");
        }

        Log.e("n","End of document");
    }

    public void parseRSSData(String RSSItemsToParse) throws MalformedURLException
    {
        URL rssURL = new URL(RSSItemsToParse);
        InputStream rssInputStream;
        try
        {
            XmlPullParserFactory parseRSSfactory = XmlPullParserFactory.newInstance();
            parseRSSfactory.setNamespaceAware(true);
            XmlPullParser RSSxmlPP = parseRSSfactory.newPullParser();
            String xmlRSS = getStringFromInputStream(getInputStream(rssURL), "UTF-8");
            RSSxmlPP.setInput(new StringReader(xmlRSS));
            int eventType = RSSxmlPP.getEventType();

            parseRSSDataItems(RSSxmlPP, eventType);
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");
    }

    public InputStream getInputStream(URL url) throws IOException
    {
        return url.openConnection().getInputStream();
    }

    public static String getStringFromInputStream(InputStream stream, String charsetName) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, charsetName);
        StringWriter writer = new StringWriter();
        while( -1 != (n = reader.read(buffer))){writer.write(buffer,0,n);}
        return writer.toString();
    }
}
