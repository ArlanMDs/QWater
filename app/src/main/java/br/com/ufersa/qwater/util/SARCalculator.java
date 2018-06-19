package br.com.ufersa.qwater.util;

public class SARCalculator {
//    private double ca;
//    private double mg;
//    private double na;
//    private double co3;
//    private double hco3;
//    private double cea;
//
    public SARCalculator(){}

    /**
     * calcula o valor do RAS normal. Antes do cálculo é feita uma checagem
     * no formato das unidades de medida, pois a fórmula usa o formato mEq/L
     * @param moleculesSpinnerPosition  posição do spinner de escolha do formato dos elementos
     * @return valor do RAS normal
     */
    public double calculateNormalSAR(int moleculesSpinnerPosition, double ca, double mg, double na){
        //formatNaMgCaToMeq_L(moleculesSpinnerPosition);
        //double ca = this.ca, mg = this.mg, na = this.na;
        double caMg = (ca + mg) / 2;
        return na / Math.pow(caMg,0.5);
        //return na / Math.sqrt((ca + mg)/2);
    }

    /**
     * calcula o valor do RAS corrigido. Antes do cálculo é feita uma checagem
     * no formato das unidades de medida, pois a fórmula usa o formato mmol/L para as moléculas e ds/m para a
     * CEa, já a fórmula para calcular o cálcio corrigido utiliza mEq/L
     * @param ceaSpinnerPosition unidade do cea
     * @param moleculesSpinnerPosition unidade dos elementos
     * @return valor do RAS corrigido
     */
    public double calculateCorrectedSAR(int moleculesSpinnerPosition, int ceaSpinnerPosition, double ca, double mg, double na, double cea, double hco3){

        //formatCaHco3ToMeq_L(moleculesSpinnerPosition);
        //formatNaMgToMmol_L(moleculesSpinnerPosition);
        //formatCeaToDs_m(ceaSpinnerPosition);
        ca = calculateCorrectedCalcium(cea, hco3, ca);
        double caMg = (ca + mg) / 2;
        return na / Math.pow(caMg,0.5);
    }

    /**
     * usa a fórmula para cálculo do RAS corrigido dada no apêndice 2, do livro "A qualidade da água para irrigação", autor: José Franscismar de Medeiros
     * @return valor do RAS corrigido
     */
    private double calculateCorrectedCalcium(double cea, double hco3, double ca){
          CaTable table = new CaTable();
          //TODO nesse momento, caso ultrapasse o valor limite de 30 hco3/ca ou de 8 ce, é impossível calcular
          return table.interpolate(hco3 / ca, cea);
    }

//    /**
//     * formata os dados para mEq/L, que são as unidades usadas nas fórmulas de cálculo do cálcio corrigido
//     * @param elementsUnit unidade dos elementos
//     */
//    private void formatNaMgCaToMeq_L(int elementsUnit) {
//        /*
//         spinner positions
//         molecules:
//         mmol/l = 0
//         mg/l = 1
//         mEq/L = 2
//        */
//        UnitConverter uc = new UnitConverter();
//
//        switch (elementsUnit){
//
//            case 0:
//                na = uc.mmol_LToMeq_L("na", na);
//                mg = uc.mmol_LToMeq_L("mg", mg);
//                ca = uc.mmol_LToMeq_L("ca", ca);
//
//                break;
//            case 1:
//                na = uc.mg_LToMeq_L("na", na);
//                mg = uc.mg_LToMeq_L("mg", mg);
//                ca = uc.mg_LToMeq_L("ca", ca);
//
//                break;
//            case 2:
//                //nothing to do, already meq/l
//                break;
//
//            default:
//                break;
//        }
//
//    }
//
//    /**
//     * formata os dados para mEq/L, que são as unidades usadas nas fórmulas de cálculo do cálcio corrigido
//     * @param elementsUnit unidade dos elementos
//     */
//    private void formatCaHco3ToMeq_L(int elementsUnit) {
//        /*
//         spinner positions
//         molecules:
//         mmol/l = 0
//         mg/l = 1
//         mEq/L = 2
//        */
//        UnitConverter uc = new UnitConverter();
//
//        switch (elementsUnit){
//
//            case 0:
//                ca = uc.mmol_LToMeq_L("ca", ca);
//                hco3 = uc.mmol_LToMeq_L("hco3", hco3);
//
//                break;
//            case 1:
//
//                ca = uc.mg_LToMeq_L("ca", ca);
//                hco3 = uc.mg_LToMeq_L("hco3", hco3);
//
//                break;
//
//            case 2:
//                //nothing to do, already meq/l
//
//                break;
//
//            default:
//                break;
//        }
//
//    }
//
//    /**
//     * formata os dados para mmol/L, que são as unidades usadas nas fórmulas de cálculo do RAS corrigido
//     * @param elementsUnit unidade dos elementos
//     */
//    private void formatNaMgToMmol_L(int elementsUnit) {
//        /*
//         spinner positions
//         molecules:
//         mmol/l = 0
//         mg/l = 1
//         mEq/L = 2
//        */
//        UnitConverter uc = new UnitConverter();
//
//        switch (elementsUnit){
//
//            case 0:
//                //nothing to do, already mmol/L
//                break;
//            case 1:
//
//                mg = uc.mg_LToMmol_L("mg", mg);
//                na = uc.mg_LToMeq_L("na", na);
//
//                //co3;
//                break;
//
//            case 2:
//                mg = uc.meq_LToMmol_L("mg", mg);
//                na = uc.meq_LToMmol_L("na", na);
//                break;
//
//            default:
//                break;
//        }
//
//    }
//
//    /**
//     * formata a ce para dS/m
//     * @param spinnerCeaUnit unidade da condutividade elétrica cea
//     */
//    private void formatCeaToDs_m(int spinnerCeaUnit){
//        /*
//         cea:
//         dS/m = 0
//         mS/cm = 1
//         uS/cm = 2
//        */
//
//        //Caso estiver no formato uS/cm, é necessário dividir por 1000 para transformar em dS/m.
//        //dS/m e mS/cm são valores equivalentes
//        if(spinnerCeaUnit == 2)
//            cea = cea / 1000;
//
//    }

}
