package com.example.a163363s.parkbuddy.DataModels;

public class Location {
    private String id;
    private String title;
    private int imageResource;
    private int cameraimageResource;


    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public int getImageResource()
    {
        return imageResource;
    }

    public int getCameraimageResource() {
        return cameraimageResource;
    }

    public Location(String id, String title, int imageResource, int cameraimageResource) {
        this.id = id;
        this.title = title;
        this.imageResource = imageResource;
        this.cameraimageResource = cameraimageResource;

    }


}
