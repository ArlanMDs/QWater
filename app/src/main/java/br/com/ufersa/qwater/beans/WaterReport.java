package br.com.ufersa.qwater.beans;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

// Tutorial da relação one-to-many no Romm:
// https://www.bignerdranch.com/blog/room-data-storage-for-everyone/
// https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82

// O parcelable é usado para passar um objeto (waterReport) da activity que faz a listagem via RecyclerView para a activity de detalhes

@Entity(foreignKeys = @ForeignKey(
        entity = WaterSource.class,
        parentColumns = "souName",
        childColumns = "wat_souName"))
public class WaterReport implements Parcelable{//TODO resolver o que fazer quando deletar um parent

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
    private double watPH;
    private double watNormalSar;
    private double watCorrectedSar;
    private long watDate; //A data é criada no momento de inserir no banco
    private String wat_souName;

    public WaterReport(){

    }

    public WaterReport(double watCa, double watMg, double watK, double watNa, double watCo3, double watHco3, double watCl, double watCea, double watPH) {
        this.watCa = watCa;
        this.watMg = watMg;
        this.watK = watK;
        this.watNa = watNa;
        this.watCo3 = watCo3;
        this.watHco3 = watHco3;
        this.watCl = watCl;
        this.watCea = watCea;
        this.watPH = watPH;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(watID);
        dest.writeDouble(watCa);
        dest.writeDouble(watMg);
        dest.writeDouble(watK);
        dest.writeDouble(watNa);
        dest.writeDouble(watCo3);
        dest.writeDouble(watHco3);
        dest.writeDouble(watCl);
        dest.writeDouble(watCea);
        dest.writeDouble(watPH);
        dest.writeDouble(watNormalSar);
        dest.writeDouble(watCorrectedSar);
        dest.writeLong(watDate);
    }

    protected WaterReport(Parcel in) {
        watID = in.readInt();
        watCa = in.readDouble();
        watMg = in.readDouble();
        watK = in.readDouble();
        watNa = in.readDouble();
        watCo3 = in.readDouble();
        watHco3 = in.readDouble();
        watCl = in.readDouble();
        watCea = in.readDouble();
        watPH = in.readDouble();
        watNormalSar = in.readDouble();
        watCorrectedSar = in.readDouble();
        watDate = in.readLong();
    }

    public static final Creator<WaterReport> CREATOR = new Creator<WaterReport>() {
        @Override
        public WaterReport createFromParcel(Parcel in) {
            return new WaterReport(in);
        }

        @Override
        public WaterReport[] newArray(int size) {
            return new WaterReport[size];
        }
    };

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

    public double getWatPH() {
        return watPH;
    }

    public void setWatPH(double watPH) {
        this.watPH = watPH;
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

    public long getWatDate() {
        return watDate;
    }

    public void setWatDate(long watDate) {
        this.watDate = watDate;
    }

    public String getWat_souName() {
        return wat_souName;
    }

    public void setWat_souName(String wat_souName) {
        this.wat_souName = wat_souName;
    }
}
