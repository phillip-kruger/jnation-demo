package pt.jnation.movie.model;

import java.net.URL;

public class Review {
    private String username;
    private URL userUrl;
    private URL reviewLink;
    private boolean warningSpoilers;
    private String date; // 20 June 2014 TODO: Make date object
    private Double rate;
    private String helpful;
    private String title;
    private String content;

    public Review() {
    }

    public Review(String username, URL userUrl, URL reviewLink, boolean warningSpoilers, String date, Double rate, String helpful, String title, String content) {
        this.username = username;
        this.userUrl = userUrl;
        this.reviewLink = reviewLink;
        this.warningSpoilers = warningSpoilers;
        this.date = date;
        this.rate = rate;
        this.helpful = helpful;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public URL getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(URL userUrl) {
        this.userUrl = userUrl;
    }

    public URL getReviewLink() {
        return reviewLink;
    }

    public void setReviewLink(URL reviewLink) {
        this.reviewLink = reviewLink;
    }

    public boolean isWarningSpoilers() {
        return warningSpoilers;
    }

    public void setWarningSpoilers(boolean warningSpoilers) {
        this.warningSpoilers = warningSpoilers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getHelpful() {
        return helpful;
    }

    public void setHelpful(String helpful) {
        this.helpful = helpful;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
