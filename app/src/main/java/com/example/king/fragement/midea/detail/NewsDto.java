package com.example.king.fragement.midea.detail;

/**
 * Created by test on 15-11-14.
 */

import com.example.king.fragement.midea.NewsItem;

import java.util.List;

public class NewsDto
{
    private List<NewsItem> newses;
    private String nextPageUrl ;
    public List<NewsItem> getNewses()
    {
        return newses;
    }
    public void setNewses(List<NewsItem> newses)
    {
        this.newses = newses;
    }
    public String getNextPageUrl()
    {
        return nextPageUrl;
    }
    public void setNextPageUrl(String nextPageUrl)
    {
        this.nextPageUrl = nextPageUrl;
    }



}
