package com.example.king.fragement.midea;

public class NewsItem
{
    private int id;

    private String title;
   
    public String link;
   
    private String date;
    private String imgLink;
    private String content;
    private  int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private int newsType;

    public int getNewsType()
    {
        return newsType;
    }

    public void setNewsType(int newsType)
    {
        this.newsType = newsType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getImgLink()
    {
        return imgLink;
    }

    public void setImgLink(String imgLink)
    {
        this.imgLink = imgLink;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "TraceItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink
                + ", content=" + content + ", newsType=" + newsType + "]";
    }

}
