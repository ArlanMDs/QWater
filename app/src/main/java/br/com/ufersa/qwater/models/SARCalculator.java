package br.com.ufersa.qwater.models;


import static java.lang.Math.sqrt;

public class SARCalculator {
    private Double ca;
    private Double mg;
    private Double na;
    private Double co3;
    private Double hco3;
    private Double cea;

    /**
     * Construtor para calcular o RAS corrigido
     * @param ca valor do cálcio
     * @param mg valor do magnésio
     * @param na valor do sódio
     * @param cea valor da condutividade elétrica
     * @param hco3 valor do bicarbonato
     */
    public SARCalculator(Double ca, Double mg, Double na, Double cea, Double hco3){
        this.ca = ca;
        this.mg = mg;
        this.na = na;
        this.cea = cea;
        this.hco3 = hco3;
    }

    /**
     * Construtor para calcular o RAS normal
     * @param ca valor do cálcio
     * @param mg valor do magnésio
     * @param na valor do sódio
     */
    public SARCalculator(Double ca, Double mg, Double na){
        this.ca = ca;
        this.mg = mg;
        this.na = na;
    }

    /**
     * calcula o valor do RAS normal. Antes do cálculo é feita uma checagem
     * no formato das unidades de medida, pois a fórmula usa o formato mEq/L
     * @param moleculesSpinnerPosition  posição do spinner de escolha do formato dos elementos
     * @return valor do RAS normal
     */
    public Double calculateNormalSAR(int moleculesSpinnerPosition){
        formatNaMgCaToMeq_L(moleculesSpinnerPosition);
        return na / sqrt((ca + mg)/2);
    }

    /**
     * calcula o valor do RAS corrigido. Antes do cálculo é feita uma checagem
     * no formato das unidades de medida, pois a fórmula usa o formato mmol/L para as moléculas e ds/m para a
     * CEa, já a fórmula para calcular o cálcio corrigido utiliza mEq/L
     * @param ceaSpinnerPosition unidade do cea
     * @param moleculesSpinnerPosition unidade dos elementos
     * @return valor do RAS corrigido
     */
    public Double calculateCorrectedSAR(int moleculesSpinnerPosition, int ceaSpinnerPosition){

        formatCaHco3ToMeq_L(moleculesSpinnerPosition);
        formatNaMgToMmol_L(moleculesSpinnerPosition);
        formatCeaToDs_m(ceaSpinnerPosition);
        /*
            IMPORTANTE:
            o valor do cálcio corrigido está em mEq/L, dividí-lo por 2 é
            a forma de transformá-lo para mmol/L, que é a unidade usada nessa
            fórmula.
         */
        double ca = calculateCorrectedCalcium();
        return na / sqrt( (ca / 2 + mg) / 2 );
    }
    //TODO Tratar -1 da conversão de unidades, se necessário

    /**
     * formata os dados para mEq/L, que são as unidades usadas nas fórmulas de cálculo do cálcio corrigido
     * @param elementsUnit unidade dos elementos
     */
    private void formatNaMgCaToMeq_L(int elementsUnit) {
        /*
         spinner positions
         molecules:
         mmol/l = 0
         mg/l = 1
         mEq/L = 2
        */
        UnitConverter uc = new UnitConverter();

        switch (elementsUnit){

            case 0:
                na = uc.mmol_LToMeq_L("na", na);
                mg = uc.mmol_LToMeq_L("mg", mg);
                ca = uc.mmol_LToMeq_L("ca", ca);

                break;
            case 1:
                na = uc.mg_LToMeq_L("na", na);
                mg = uc.mg_LToMeq_L("mg", mg);
                ca = uc.mg_LToMeq_L("ca", ca);

                break;
            case 2:
                //nothing to do, already meq/l
                break;

            default:
                break;
        }

    }

    /**
     * formata os dados para mEq/L, que são as unidades usadas nas fórmulas de cálculo do cálcio corrigido
     * @param elementsUnit unidade dos elementos
     */
    private void formatCaHco3ToMeq_L(int elementsUnit) {
        /*
         spinner positions
         molecules:
         mmol/l = 0
         mg/l = 1
         mEq/L = 2
        */
        UnitConverter uc = new UnitConverter();

        switch (elementsUnit){

            case 0:
                ca = uc.mmol_LToMeq_L("ca", ca);
                hco3 = uc.mmol_LToMeq_L("hco3", hco3);

                break;
            case 1:

                ca = uc.mg_LToMeq_L("ca", ca);
                hco3 = uc.mg_LToMeq_L("hco3", hco3);

                break;

            case 2:
                //nothing to do, already meq/l

                break;

            default:
                break;
        }

    }

    /**
     * formata os dados para mmol/L, que são as unidades usadas nas fórmulas de cálculo do RAS corrigido
     * @param elementsUnit unidade dos elementos
     */
    private void formatNaMgToMmol_L(int elementsUnit) {
        /*
         spinner positions
         molecules:
         mmol/l = 0
         mg/l = 1
         mEq/L = 2
        */
        UnitConverter uc = new UnitConverter();

        switch (elementsUnit){

            case 0:
                //nothing to do, already mmol/L
                break;
            case 1:

                mg = uc.mg_LToMmol_L("mg", mg);
                na = uc.mg_LToMeq_L("na", na);

                //co3;
                break;

            case 2:
                mg = uc.meq_LToMmol_L("mg", mg);
                na = uc.meq_LToMmol_L("na", na);
                break;

            default:
                break;
        }

    }

    /**
     * formata a ce para dS/m
     * @param spinnerCeaUnit unidade da condutividade elétrica cea
     */
    private void formatCeaToDs_m(int spinnerCeaUnit){
        /*
         cea:
         dS/m = 0
         mS/cm = 1
         uS/cm = 2
        */

        //Caso estiver no formato uS/cm, é necessário dividir por 1000 para transformar em dS/m.
        //dS/m e mS/cm são valores equivalentes
        if(spinnerCeaUnit == 2)
            cea = cea / 1000;

    }

    /**
     * usa a fórmula para cálculo do RAS corrigido dada no apêndice 2, do livro "A qualidade da água para irrigação", autor: José Franscismar de Medeiros
     * @return valor do RAS corrigido
     */
    private Double calculateCorrectedCalcium(){

        return 0.956 + 0.0564 * cea + 1.0645 * Math.pow(cea, 0.09565) * Math.pow((hco3 / ca), -0.667);
    }

}
