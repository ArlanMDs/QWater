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

            default:
                return -1;
        }
    }

    /**
     * Converte valores de mg/L para meq/L
     * @param element elemento  a ser transformado
     * @param mg_L valor em mg/L
     * @return valor e meq/L
     */
    public double mg_LToMeq_L(String element, double mg_L){

        switch (element){
            case "ca":
                return mg_L / (ca /2);

            case "mg":
                return mg_L / (mg /2);

            case "k":
                return mg_L / k;

            case "na":
                return mg_L / na;

            case "co3":
                return mg_L / (co3 /2);

            case "hco3":
                return mg_L / hco3;

            case "cl":
                return mg_L / cl;

            default:
                return -1;
        }
    }

    /**
     * Converte unidades de mmol/L para meq/L
     * @param element elemento a ser transformado
     * @param mmol_L valor em mmol/L
     * @return valor em meq/L
     */
    public double mmol_LToMeq_L(String element, double mmol_L){

        switch (element){
            case "ca":
                return mmol_L * 2;

            case "mg":
                return mmol_L * 2;

            case "k":
                return mmol_L;

            case "na":
                return mmol_L;

            case "co3":
                return mmol_L * 2;

            case "hco3":
                return mmol_L;

            case "cl":
                return mmol_L;

            default:
                return -1;
        }
    }

    /**
     * Converte unidades de meq/L para mmol/L
     * @param element elemento a ser transformado
     * @param meq_L valor em meq/L
     * @return valor em mmol/L
     */
    public double meq_LToMmol_L(String element, double meq_L){

        switch (element){
            case "ca":
                return meq_L / 2;

            case "mg":
                return meq_L / 2;

            case "k":
                return meq_L;

            case "na":
                return meq_L;

            case "co3":
                return meq_L / 2;

            case "hco3":
                return meq_L;

            case "cl":
                return meq_L;

            default:
                return -1;
        }
    }

    /**
     * Converte unidades de mg/L para mmol/L
     * @param element elemento a ser transformado
     * @param mg_L valor em mg/L
     * @return valor em mmol/L
     */
    public double mg_LToMmol_L(String element, double mg_L){

        switch (element){
            case "ca":
                return (mg_L / 2) / ca;

            case "mg":
                return (mg_L / 2) / mg;

            case "k":
                return mg_L / k;

            case "na":
                return mg_L / na;

            case "co3":
                return (mg_L / 2) / co3;

            case "hco3":
                return mg_L / hco3;

            case "cl":
                return mg_L / cl;

            default:
                return -1;
        }
    }
}
