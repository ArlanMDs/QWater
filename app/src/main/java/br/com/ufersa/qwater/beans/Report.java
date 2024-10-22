package br.com.ufersa.qwater.beans;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Tutorial da relação one-to-many no Romm:
// https://www.bignerdranch.com/blog/room-data-storage-for-everyone/
// https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82

// O parcelable é usado para passar um objeto (report) entre activities

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "souId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Source.class,
                parentColumns = "name",
                childColumns = "souName",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Report implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double ca;
    private double mg;
    private double k;
    private double na;
    private double co3;
    private double hco3;
    private double cl;
    private double so4;
    private double b;
    private double cea;
    private double ph;
    private double normalSar;
    private double correctedSar;
    private long date;
    private int souId;
    private String souName;// Esse atributo poderia fazer parte da chave. No momento ele está aqui apenas para facilitar e não precisar fazer outra
                            // consulta no banco na hora de exibir o nome da source durante a listagem dos reports

    public Report(){

    }

    public Report(double ca, double mg, double k, double na, double co3, double hco3, double cl, double so4, double b, double cea, double ph) {
        this.ca = ca;
        this.mg = mg;
        this.k = k;
        this.na = na;
        this.co3 = co3;
        this.hco3 = hco3;
        this.cl = cl;
        this.so4 = so4;
        this.b = b;
        this.cea = cea;
        this.ph = ph;
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
        dest.writeDouble(so4);
        dest.writeDouble(b);
        dest.writeDouble(cea);
        dest.writeDouble(ph);
        dest.writeDouble(normalSar);
        dest.writeDouble(correctedSar);
        dest.writeLong(date);
        dest.writeInt(souId);
        dest.writeString(souName);
    }

    protected Report(Parcel in) {
        id = in.readInt();
        ca = in.readDouble();
        mg = in.readDouble();
        k = in.readDouble();
        na = in.readDouble();
        co3 = in.readDouble();
        hco3 = in.readDouble();
        cl = in.readDouble();
        so4 = in.readDouble();
        b = in.readDouble();
        cea = in.readDouble();
        ph = in.readDouble();
        normalSar = in.readDouble();
        correctedSar = in.readDouble();
        date = in.readLong();
        souId = in.readInt();
        souName = in.readString();
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
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

    public double getSo4() {
        return so4;
    }

    public void setSo4(double so4) {
        this.so4 = so4;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setCl(double cl) {
        this.cl = cl;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getCorrectedSar() {
        return correctedSar;
    }

    public void setCorrectedSar(double correctedSar) {
        this.correctedSar = correctedSar;
    }

    public double getCea() {
        return cea;
    }

    public void setCea(double cea) {
        this.cea = cea;
    }

    public double getNormalSar() {
        return normalSar;
    }

    public void setNormalSar(double normalSar) {
        this.normalSar = normalSar;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSouId() {
        return souId;
    }

    public void setSouId(int souId) {
        this.souId = souId;
    }

    public String getSouName() {
        return souName;
    }

    public void setSouName(String souName) {
        this.souName = souName;
    }
}
