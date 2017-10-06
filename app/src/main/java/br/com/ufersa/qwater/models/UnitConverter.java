package br.com.ufersa.qwater.models;

/**
 * Classe responsável por converter unidades de concentração
 */
public class UnitConverter {
    private final double Ca = 40.08, Mg = 24.31, K = 39.10, Na = 29.99, CO3 = 60.0089, HCO3 = 61.0168, Cl = 35.453;

    /**
     * Converte valores de meq/L^-1 para mg/L^-1
     * @param element elemento químico envolvido na conversão
     * @param meqL_1 valor em meq/L^-1
     * @return valor em mg/L^-1
     */
    public double meqL_1ToMgL_1(String element, double meqL_1){

        switch (element){
            case "Ca":
                return meqL_1 * (Ca / 2);

            case "Mg":
                return meqL_1 * (Mg / 2);

            case "K":
                return meqL_1 * K;

            case "Na":
                return meqL_1 * Na;

            case "CO3":
                return meqL_1 * (CO3 / 2);

            case "HCO3":
                return meqL_1 * HCO3;

            case "Cl":
                return meqL_1 * Cl;

        }

        return 0.0;
    }

    /**
     * Converte valores de mg/L^-1 para meq/L^-1
     * @param element elemento químico envolvido na conversão
     * @param mgL_1 valor em mg/L^-1
     * @return valor e meq/L^-1
     */
    public double mgL_1ToMeqL_1(String element, double mgL_1){

        switch (element){
            case "Ca":
                return mgL_1 / (Ca/2);

            case "Mg":
                return mgL_1 / (Mg/2);

            case "K":
                return mgL_1 / K;

            case "Na":
                return mgL_1 / Na;

            case "CO3":
                return mgL_1 / (CO3/2);

            case "HCO3":
                return mgL_1 / HCO3;

            case "Cl":
                return mgL_1 / Cl;

        }
        return 0.0;
    }
}
