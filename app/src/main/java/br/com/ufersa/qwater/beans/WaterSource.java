package br.com.ufersa.qwater.beans;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class WaterSource {

    @PrimaryKey
    @NonNull
    private String souName;
    private String souType;
    private double souLatitude;
    private double souLongitude;

    public WaterSource(@NonNull String souName, String souType, double souLatitude, double souLongitude) {
        this.souName = souName;
        this.souType = souType;
        this.souLatitude = souLatitude;
        this.souLongitude = souLongitude;
    }

    public String getSouType() {
        return souType;
    }

    public void setSouType(String souType) {
        this.souType = souType;
    }

    public String getSouName() {
        return souName;
    }

    public void setSouName(String souName) {
        this.souName = souName;
    }

    public double getSouLatitude() {
        return souLatitude;
    }

    public void setSouLatitude(double souLatitude) {
        this.souLatitude = souLatitude;
    }

    public double getSouLongitude() {
        return souLongitude;
    }

    public void setSouLongitude(double souLongitude) {
        this.souLongitude = souLongitude;
    }


}
