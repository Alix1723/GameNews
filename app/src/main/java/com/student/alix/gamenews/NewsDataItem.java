package com.student.alix.gamenews;

/**
 * Created by Alix on 04/12/2014.
 */
import java.io.Serializable;

public class NewsDataItem
{
    private String newsTitle;
    private String newsDescription;
    private String newsLink;


    //Member getters/setters
    public String getTitle()
    {
        return this.newsTitle;
    }

    public String getDescription()
    {
        return this.newsDescription;
    }

    public String getLink()
    {
        return this.newsLink;
    }

    public void setTitle(String nTitle)
    {
        this.newsTitle = nTitle;
    }

    public void setDescription(String nDesc)
    {
        this.newsDescription = nDesc;
    }

    public void setLink(String nLink) {
        this.newsLink = nLink;

    }

    //Constructors
    public NewsDataItem()
    {
        this.newsTitle = "";
        this.newsDescription = "";
        this.newsLink = "";
    }

    public NewsDataItem(String nTitle, String nDesc, String nLink)
    {
        this.newsTitle = nTitle;
        this.newsDescription = nDesc;
        this.newsLink = nLink;
    }

    @Override
    public String toString()
    {
        String str = newsTitle;//"NewsDataItem: " + newsTitle + ", " + newsDescription + ", " + newsLink;
        return str;
    }
}
