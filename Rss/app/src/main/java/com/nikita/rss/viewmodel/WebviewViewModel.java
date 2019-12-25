package com.nikita.rss.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class WebviewViewModel extends AndroidViewModel {

    private String link;

    public WebviewViewModel(Application application) {
        super(application);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
