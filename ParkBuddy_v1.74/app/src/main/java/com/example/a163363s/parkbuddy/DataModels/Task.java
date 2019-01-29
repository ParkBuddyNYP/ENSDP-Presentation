package com.example.a163363s.parkbuddy.DataModels;
import java.util.Date;
public class Task {
    private int id;
    private String description;
    private String colour;           // R- red, G- green, B- blue
    private String status;           // ACTIVE / COMPLETE
    private Date datedue;

    public int getId()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }

    public String getColour()
    {
        return colour;
    }

    public String getStatus()
    {
        return status;
    }

    public Date getDatedue()
    {
        return datedue;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setDatedue(Date datedue)
    {
        this.datedue = datedue;
    }

    public Task(int id, String description, String colour, String status, Date datedue)
    {
        this.id = id;
        this.description = description;
        this.colour = colour;
        this.status = status;
        this.datedue = datedue;
    }
}
