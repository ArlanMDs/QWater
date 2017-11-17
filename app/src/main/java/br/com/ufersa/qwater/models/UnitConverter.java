package br.com.ufersa.qwater.models;

/**
 * Classe responsável por converter unidades de concentração
 */
public class UnitConverter {
    //Valor das massas atômicas
    private final double Ca = 40.08, Mg = 24.31, K = 39.10, Na = 29.99, CO3 = 60.0089, HCO3 = 61.0168, Cl = 35.453;

    /**
     * Converte valores de meq/L^-1 para mg/L^-1
     * @param element elemento químico envolvido na conversão
     * @param mEqL valor em meq/L^-1
     * @return valor em mg/L^-1
     */
    public double mEqLToMgL(String element, double mEqL){

        switch (element){
            case "Ca":
                return mEqL * (Ca / 2);

            case "Mg":
                return mEqL * (Mg / 2);

            case "K":
                return mEqL * K;

            case "Na":
                return mEqL * Na;

            case "CO3":
                return mEqL * (CO3 / 2);

            case "HCO3":
                return mEqL * HCO3;

            case "Cl":
                return mEqL * Cl;

        }

        return 0.0;
    }

    /**
     * Converte valores de mg/L^-1 para meq/L^-1
     * @param element elemento químico envolvido na conversão
     * @param mgL valor em mg/L^-1
     * @return valor e meq/L^-1
     */
    public double mgLToMeqL(String element, double mgL){

        switch (element){
            case "Ca":
                return mgL / (Ca/2);

            case "Mg":
                return mgL / (Mg/2);

            case "K":
                return mgL / K;

            case "Na":
                return mgL / Na;

            case "CO3":
                return mgL / (CO3/2);

            case "HCO3":
                return mgL / HCO3;

            case "Cl":
                return mgL / Cl;

        }
        return 0.0;
    }

    public double mmolLToMeqL(String element, double mmolL){

        switch (element){
            case "Ca":
                return mmolL * 2;

            case "Mg":
                return mmolL * 2;

            case "K":
                return mmolL;

            case "Na":
                return mmolL;

            case "CO3":
                return mmolL * 2;

            case "HCO3":
                return mmolL;

            case "Cl":
                return mmolL;

        }
        return 0.0;
    }
}
