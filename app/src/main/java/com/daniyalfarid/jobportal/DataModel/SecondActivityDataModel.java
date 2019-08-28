package com.daniyalfarid.jobportal.DataModel;

import java.util.ArrayList;

public class SecondActivityDataModel {
    String title;
    String description;
    Boolean active;
    String full_description;
    ArrayList<String> images;
    String email;
    String pushID;


    public SecondActivityDataModel() {
    }

    public SecondActivityDataModel(String title, String description, Boolean active,String full_description,ArrayList<String> images,String email,String pushID) {
        this.title = title;
        this.description = description;
        this.active = active;
        this.full_description = full_description;
        this.images = images;
        this.email = email;
        this.pushID = pushID;

    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }
}