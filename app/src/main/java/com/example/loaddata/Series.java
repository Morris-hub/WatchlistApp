package com.example.loaddata;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.UserNotAuthenticatedException;

import java.io.Serializable;
import java.util.ArrayList;

public class Series {

    private String title;
    private String season;
    private String episode;
    private String status;

    public Series( String title, String season, String episode){
        this.title = title;
        this.season = season;
        this.episode = episode;
        this.status = status;
    }


    public String getTitle() {
        return title;
    }

    public String getSeason() {
        return season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getStatus() {
        return status;
    }
}
