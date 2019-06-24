package com.online_guide.diploma.audioguide.entities;

import com.orm.SugarRecord;
import com.orm.dsl.*;
import static com.online_guide.diploma.audioguide.constants.BaseField.*;

public class Attraction extends SugarRecord {

    @Unique
    private long id;

    @Column(name = ATTRACTION_ADDRESS)
    private String address;

    @Column(name = COORD_X)
    private double coordX;

    @Column(name = COORD_Y)
    private double coordY;

    @Column(name = INFO)
    private String info;

    @Column(name = ATTRACTION_NAME)
    private String attractionName;

    @Unique
    @Column(name = CITY_ID)
    private long cityId;

    public Attraction() {
    }

    public Attraction(String name, String address, long cityId, double coordX, double coordY, String info) {
        this.info = info;
        this.attractionName = name;
        this.address = address;
        this.coordX = coordX;
        this.coordY = coordY;
        this.cityId = cityId;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public long getID() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCoordX() {
        return coordX;
    }

    public String getName() {
        return attractionName;
    }

    public void setName(String name) {
        this.attractionName = name;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return attractionName;
    }
}
