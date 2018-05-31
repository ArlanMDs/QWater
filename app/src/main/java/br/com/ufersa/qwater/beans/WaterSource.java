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
    private long souLatitude;
    private long souLongitude;

    public WaterSource(){

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

    public long getSouLatitude() {
        return souLatitude;
    }

    public void setSouLatitude(long souLatitude) {
        this.souLatitude = souLatitude;
    }

    public long getSouLongitude() {
        return souLongitude;
    }

    public void setSouLongitude(long souLongitude) {
        this.souLongitude = souLongitude;
    }


}
