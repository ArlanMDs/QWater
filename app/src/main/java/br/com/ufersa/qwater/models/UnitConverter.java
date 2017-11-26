package br.com.ufersa.qwater.models;

/**
 * Classe responsável por converter unidades de concentração
 */
public class UnitConverter {
    //Valor das massas atômicas
    private final double ca = 40.08, mg = 24.31, k = 39.10, na = 29.99, co3 = 60.0089, hco3 = 61.0168, cl = 35.453;

    /**
     * Converte valores de meq/L para mg/L
     * @param element elemento  a ser transformado
     * @param mEqL valor em meq/L
     * @return valor em mg/L
     */
    public double mEq_LToMg_L(String element, double mEqL){

        switch (element){
            case "ca":
                return mEqL * (ca / 2);

            case "mg":
                return mEqL * (mg / 2);

            case "k":
                return mEqL * k;

            case "na":
                return mEqL * na;

            case "co3":
                return mEqL * (co3 / 2);

            case "hco3":
                return mEqL * hco3;

            case "cl":
                return mEqL * cl;

        }

        return 0.0;
    }

    /**
     * Converte valores de mg/L para meq/L
     * @param element elemento  a ser transformado
     * @param mgL valor em mg/L
     * @return valor e meq/L
     */
    public double mg_LToMeq_L(String element, double mgL){

        switch (element){
            case "ca":
                return mgL / (ca /2);

            case "mg":
                return mgL / (mg /2);

            case "k":
                return mgL / k;

            case "na":
                return mgL / na;

            case "co3":
                return mgL / (co3 /2);

            case "hco3":
                return mgL / hco3;

            case "cl":
                return mgL / cl;

        }
        return 0.0;
    }

    /**
     * Converte unidades de mmol/L para meq/L
     * @param element elemento a ser transformado
     * @param mmolL valor em mmol/L
     * @return valor em meq/L
     */
    public double mmol_LToMeq_L(String element, double mmolL){

        switch (element){
            case "ca":
                return mmolL * 2;

            case "mg":
                return mmolL * 2;

            case "k":
                return mmolL;

            case "na":
                return mmolL;

            case "co3":
                return mmolL * 2;

            case "hco3":
                return mmolL;

            case "cl":
                return mmolL;

        }
        return 0.0;
    }

    /**
     * Converte unidades de meq/L para mmol/L
     * @param element elemento a ser transformado
     * @param meqL valor em meq/L
     * @return valor em mmol/L
     */
    public double meq_LToMmol_L(String element, double meqL){

        switch (element){
            case "ca":
                return meqL / 2;

            case "mg":
                return meqL / 2;

            case "k":
                return meqL;

            case "na":
                return meqL;

            case "co3":
                return meqL / 2;

            case "hco3":
                return meqL;

            case "cl":
                return meqL;

        }
        return 0.0;
    }

    /**
     * Converte unidades de mg/L para mmol/L
     * @param element elemento a ser transformado
     * @param mgL valor em mg/L
     * @return valor em mmol/L
     */
    public double mg_LToMmol_L(String element, double mgL){

        switch (element){
            case "ca":
                return (mgL / 2) / ca;

            case "mg":
                return (mgL / 2) / mg;

            case "k":
                return mgL / k;

            case "na":
                return mgL / na;

            case "co3":
                return (mgL / 2) / co3;

            case "hco3":
                return mgL / hco3;

            case "cl":
                return mgL / cl;

        }
        return 0.0;
    }
}
