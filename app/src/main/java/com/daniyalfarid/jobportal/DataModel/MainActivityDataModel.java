package com.daniyalfarid.jobportal.DataModel;

public class MainActivityDataModel {
    private String title;
    private String link;
    private String ID;

    public MainActivityDataModel() {
    }

    public MainActivityDataModel(String title, String link, String ID) {
        this.title = title;
        this.link = link;
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String icon) {
        this.link = icon;
    }

    public String getID() {
        return ID;
    }

}
