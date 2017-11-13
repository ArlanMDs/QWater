package br.com.ufersa.qwater.models;



public class Report {

    private double normalSAR, correctedSAR, CEa;

    public double getCorrectedSAR() {
        return correctedSAR;
    }

    public void setCorrectedSAR(double correctedSAR) {
        this.correctedSAR = correctedSAR;
    }

    public double getCEa() {
        return CEa;
    }

    public void setCEa(double CEa) {
        this.CEa = CEa;
    }

    public double getNormalSAR() {
        return normalSAR;
    }

    public void setNormalSAR(double normalSAR) {
        this.normalSAR = normalSAR;
    }

}
