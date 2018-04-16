package com.wastebanking.Models;

import com.orm.SugarRecord;

/**
 * Created by user1 on 11/25/2017.
 */

public class WBWasteDisposalLocations extends SugarRecord {
    public String type;
    public int zoneId;
    public Double lat;
    public Double longitude;
    public String address;
    public String zoom;


    public WBWasteDisposalLocations(){}

    public WBWasteDisposalLocations(String type,int zoneId,Double lat,Double longitude,String address, String zoom){
        this.type=type;
        this.zoneId=zoneId;
        this.lat=lat;
        this.longitude=longitude;
        this.address=address;
        this.zoom=zoom;

    }
    public String getType(){return type;}
    public int getZoneId(){return zoneId;}
    public Double getLat(){return lat;}
    public Double getLongitude(){return longitude;}
    public String getAddress(){return address;}
    private String getZoom(){return zoom;}

    public void setType(String type){this.type=type;}
    public void setZoneId(int zoneId){this.zoneId=zoneId;}
    public void setLat(Double lat){this.lat=lat;}
    public void setLongitude(Double longitude){this.longitude=longitude;}
    public void setAddress(String address){this.address=address;}
    public void setZoom(String zoom){this.zoom=zoom;}
}
