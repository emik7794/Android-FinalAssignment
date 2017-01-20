package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class PostModel implements Serializable{

    private String title;
    private String author;
    private String date;
    private long comments;
    private String urlString;
    private String subreddit;
    private String webLink;
    private String commentsLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public String getSubreddit() { return subreddit; }

    public void setSubreddit(String subreddit) { this.subreddit = subreddit; }

    public String getWebLink() { return webLink; }

    public void setWebLink(String webLink) { this.webLink = webLink; }

    public String getCommentsLink() {
        return commentsLink;
    }

    public void setCommentsLink(String commentsLink) {
        this.commentsLink = commentsLink;
    }
}
