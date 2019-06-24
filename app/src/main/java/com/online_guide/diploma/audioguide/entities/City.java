package com.online_guide.diploma.audioguide.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.HashSet;
import java.util.Set;

import static com.online_guide.diploma.audioguide.constants.BaseField.*;

public class City extends SugarRecord {

    @Unique
    private long id;

    @Column(name = CITY_NAME)
    private String cityName;

    @Column(name = COORD_X)
    private double coordX;

    @Column(name = COORD_Y)
    private double coordY;

    @Column(name = INFO)
    private String info;

    @Column(name = REGION_ID)
    @Unique
    private int regionId;

    @Ignore
    private Region region;

    @Ignore
    private Set<Attraction> attractions;

    public City() {
    }

    public City(String cityName, Region region, double coordX, double coordY, String info) {
        this.cityName = cityName;
        this.region = region;
        this.coordX = coordX;
        this.coordY = coordY;
        this.info = info;
        attractions = new HashSet<>();
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }


    public long getID() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean addAtraction(Attraction attraction) {
        return this.attractions.add(attraction);
    }

    public boolean removeAttraction(Attraction attraction) {
        return this.attractions.remove(attraction);
    }

    public Set<Attraction> getAttractions() {
        return attractions;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCoordX() {
        return coordX;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "City{" +
                "attractions=" + attractions +
                ", id=" + id +
                ", cityName='" + cityName + '\'' +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                ", info='" + info + '\'' +
                ", regionId=" + regionId +
                ", region=" + region +
                '}';
    }
}
