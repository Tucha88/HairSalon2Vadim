package com.telran.classwork.hairsalon2.Fragments.Services;

import com.telran.classwork.hairsalon2.Models.Services;

import java.util.ArrayList;

/**
 * Created by vadim on 21.05.2017.
 */

public class ServicesArray {
    private ArrayList<Services> services = new ArrayList<>();

    public ServicesArray(ArrayList<Services> services) {
        this.services = services;
    }

    public ServicesArray() {

    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public void add(Services services) {
        this.services.add(services);
    }
}