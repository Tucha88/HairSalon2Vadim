package com.telran.classwork.hairsalon2.Models;

import java.util.ArrayList;

/**
 * Created by TelRan on 26.03.2017.
 */

public class HttpProvider {
    ArrayList<Services> services = new ArrayList<>();
    ArrayList<Adress> adresses = new ArrayList<>();
    private static final HttpProvider ourInstanceServ = new HttpProvider();
    private static final HttpProvider ourInstanceAdr = new HttpProvider();

    public static HttpProvider getInstanceServ() {
        return ourInstanceServ;
    }
    public static HttpProvider getInstanceAdr() {
        return ourInstanceAdr;
    }

    private HttpProvider() {
    }

    public ArrayList<Services> getServList() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return services;
    }

    public void addServices(Services serv){
        services.add(serv);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Adress> getAdrList() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return adresses;
    }

    public void addAdress(Adress adr){

        adresses.add(adr);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
