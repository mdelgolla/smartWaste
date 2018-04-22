package com.wastebanking.Models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by user1 on 11/25/2017.
 */

public class WBWasteDisposalLocations extends SugarRecord {
    public String type;
    public String centerId;
    public Double lat;
    public Double longitude;
    public String name;
    public String address;
    public String contact;
    public ArrayList<String>acceptedWaste;


    public WBWasteDisposalLocations(){}

    public WBWasteDisposalLocations(String type,String centerId,Double lat,Double longitude,String name,String address,String contact,ArrayList<String>acceptedWaste){
        this.type=type;
        this.centerId=centerId;
        this.lat=lat;
        this.longitude=longitude;
        this.name=name;
        this.address=address;
        this.contact=contact;
        this.acceptedWaste=acceptedWaste;

    }
    public String getType(){return type;}
    public String getCenterId(){return centerId;}
    public Double getLat(){return lat;}
    public Double getLongitude(){return longitude;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getContact(){return contact;}

    public ArrayList<String> getAcceptedWaste() {
        return acceptedWaste;
    }

    public void setType(String type){this.type=type;}
    public void setCenterId(String centerId){this.centerId=centerId;}
    public void setLat(Double lat){this.lat=lat;}
    public void setLongitude(Double longitude){this.longitude=longitude;}
    public void setName(String name){this.name=name;}
    public void setAddress(String address){this.address=address;}
    public void setContact(String contact){this.contact=contact;}
    public void setAcceptedWaste(ArrayList<String> acceptedWaste){this.acceptedWaste=acceptedWaste;}
}
