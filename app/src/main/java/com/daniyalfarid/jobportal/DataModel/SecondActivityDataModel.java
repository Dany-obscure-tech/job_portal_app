package com.daniyalfarid.jobportal.DataModel;

public class SecondActivityDataModel {
    String title;
    String description;

    public SecondActivityDataModel() {
    }

    public SecondActivityDataModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}