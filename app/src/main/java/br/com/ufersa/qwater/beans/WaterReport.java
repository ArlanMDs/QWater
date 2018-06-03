package br.com.ufersa.qwater.beans;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

//Tutorial da relação one-to-many no Romm:
// https://www.bignerdranch.com/blog/room-data-storage-for-everyone/
//https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82

@Entity(foreignKeys = @ForeignKey(
        entity = WaterSource.class,
        parentColumns = "souName",
        childColumns = "wat_souName"))
public class WaterReport {//TODO resolver o que fazer quando deletar um parent

    @PrimaryKey(autoGenerate = true)
    private int watID;
    private double watCa;
    private double watMg;
    private double watK;
    private double watNa;
    private double watCo3;
    private double watHco3;
    private double watCl;
    private double watCea;
    private double watNormalSar;
    private double watCorrectedSar;
    private long watCreatedAt; //A hora é criada no momento de inserir no banco
    private String wat_souName;

    public WaterReport(){

    }

    public void setWatID(int watID){
        this.watID = watID;
    }

    public int getWatID() {
        return watID;
    }

    public double getWatCa() {
        return watCa;
    }

    public void setWatCa(double watCa) {
        this.watCa = watCa;
    }

    public double getWatMg() {
        return watMg;
    }

    public void setWatMg(double watMg) {
        this.watMg = watMg;
    }

    public double getWatK() {
        return watK;
    }

    public void setWatK(double watK) {
        this.watK = watK;
    }

    public double getWatNa() {
        return watNa;
    }

    public void setWatNa(double watNa) {
        this.watNa = watNa;
    }

    public double getWatCo3() {
        return watCo3;
    }

    public void setWatCo3(double watCo3) {
        this.watCo3 = watCo3;
    }

    public double getWatHco3() {
        return watHco3;
    }

    public void setWatHco3(double watHco3) {
        this.watHco3 = watHco3;
    }

    public double getWatCl() {
        return watCl;
    }

    public void setWatCl(double watCl) {
        this.watCl = watCl;
    }

    public double getWatCorrectedSar() {
        return watCorrectedSar;
    }

    public void setWatCorrectedSar(double watCorrectedSar) {
        this.watCorrectedSar = watCorrectedSar;
    }

    public double getWatCea() {
        return watCea;
    }

    public void setWatCea(double watCea) {
        this.watCea = watCea;
    }

    public double getWatNormalSar() {
        return watNormalSar;
    }

    public void setWatNormalSar(double watNormalSar) {
        this.watNormalSar = watNormalSar;
    }

    public long getWatCreatedAt() {
        return watCreatedAt;
    }

    public void setWatCreatedAt(long watCreatedAt) {
        this.watCreatedAt = watCreatedAt;
    }

    public String getWat_souName() {
        return wat_souName;
    }

    public void setWat_souName(String wat_souName) {
        this.wat_souName = wat_souName;
    }
}
