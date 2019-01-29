package com.example.a163363s.parkbuddy.DataManagers;

import com.example.a163363s.parkbuddy.DataModels.Location;
import com.example.a163363s.parkbuddy.R;

public class LocationDataManager {

    Location[] locations = {
            new Location("AIRPORT", "Changi Airport 1", R.drawable.terminal1, R.drawable.gantry),
            new Location("AIRPORT", "Changi Airport 2", R.drawable.terminal2, R.drawable.gantry2),
            new Location("AIRPORT", "Changi Airport 3", R.drawable.terminal3, R.drawable.gantry3),
            new Location("AIRPORT", "Ion", R.drawable.ion, R.drawable.gantry4),
            new Location("AIRPORT", "Changi Airport", R.drawable.terminal1, R.drawable.gantry2),
            new Location("AIRPORT", "Changi Airport", R.drawable.terminal1, R.drawable.gantry),
            new Location("AIRPORT", "Changi Airport", R.drawable.ion, R.drawable.gantry),
            new Location("AIRPORT", "Changi Airport", R.drawable.terminal2, R.drawable.gantry2),



    };

    public Location[] getLocations()
    {
        return locations;
    }
    public Location getLocationByID(String id)
    {
        //This loop behaves like a C# foreach loop.
        //IT retrieves each record from the products
        //array, and allows you to process them
        //one by one.
        for(Location location : locations)
        {
            if(location.getId() == id)
                return location;
        }
        return null;
    }



}
