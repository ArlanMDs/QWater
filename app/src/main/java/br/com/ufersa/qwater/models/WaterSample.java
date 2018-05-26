package br.com.ufersa.qwater.models;


import android.os.Parcel;
import android.os.Parcelable;

public class WaterSample implements Parcelable {

    private int id;
    private double ca;
    private double mg;
    private double k;
    private double na;
    private double co3;
    private double hco3;
    private double cl;
    private double cea;
    private double normalSAR;
    private double correctedSAR;
    private long createdAt; //A hora é criada na hora de colocar no banco, em Update.java

    public WaterSample(){

    }

    protected WaterSample(Parcel in) {
        id = in.readInt();
        ca = in.readDouble();
        mg = in.readDouble();
        k = in.readDouble();
        na = in.readDouble();
        co3 = in.readDouble();
        hco3 = in.readDouble();
        cl = in.readDouble();
        cea = in.readDouble();
        normalSAR = in.readDouble();
        correctedSAR = in.readDouble();
        createdAt = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(ca);
        dest.writeDouble(mg);
        dest.writeDouble(k);
        dest.writeDouble(na);
        dest.writeDouble(co3);
        dest.writeDouble(hco3);
        dest.writeDouble(cl);
        dest.writeDouble(cea);
        dest.writeDouble(normalSAR);
        dest.writeDouble(correctedSAR);
        dest.writeLong(createdAt);
    }

    public static final Creator<WaterSample> CREATOR = new Creator<WaterSample>() {
        @Override
        public WaterSample createFromParcel(Parcel in) {
            return new WaterSample(in);
        }

        @Override
        public WaterSample[] newArray(int size) {
            return new WaterSample[size];
        }
    };

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getCa() {
        return ca;
    }

    public void setCa(double ca) {
        this.ca = ca;
    }

    public double getMg() {
        return mg;
    }

    public void setMg(double mg) {
        this.mg = mg;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getNa() {
        return na;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public double getCo3() {
        return co3;
    }

    public void setCo3(double co3) {
        this.co3 = co3;
    }

    public double getHco3() {
        return hco3;
    }

    public void setHco3(double hco3) {
        this.hco3 = hco3;
    }

    public double getCl() {
        return cl;
    }

    public void setCl(double cl) {
        this.cl = cl;
    }

    public double getCorrectedSAR() {
        return correctedSAR;
    }

    public void setCorrectedSAR(double correctedSAR) {
        this.correctedSAR = correctedSAR;
    }

    public double getCea() {
        return cea;
    }

    public void setCea(double cea) {
        this.cea = cea;
    }

    public double getNormalSAR() {
        return normalSAR;
    }

    public void setNormalSAR(double normalSAR) {
        this.normalSAR = normalSAR;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

}
