package br.com.ufersa.qwater.models;


import static java.lang.Math.sqrt;

public class SARCalculator {
    private Double Ca;
    private Double Mg;
    private Double Na;
    private Double CO3;
    private Double HCO3;
    private Double CEa;

    public SARCalculator(Double Ca, Double Mg, Double Na, Double CEa, Double HCO3){
        this.Ca = Ca;
        this.Mg = Mg;
        this.Na = Na;
        this.CEa = CEa;
        this.HCO3 = HCO3;


    }

    private void formatUnits() {


    }

    public Double calculateSAR(){

        return Na / sqrt((Ca+Mg)/2);
    }

    /**
     * calcula o RAS corrigido de acordo com os valores do objeto. Antes do cálculo é feito uma checagem
     * no formato das unidades de medida, pois a fórmula usa o formato meq/l
     * @param CEaUnit unidade do CEa
     * @param elementsUnit unidade dos elementos
     * @return valor do RAS corrigido
     */
    public Double calculateCorrectedSAR(int CEaUnit, int elementsUnit){

        formatUnits(CEaUnit,elementsUnit);

        return Na / sqrt( (calculateCorrectedCalcium()+Mg) /2 );
    }

    /**
     * formata os dados para mEq/L e dS/m, que são as unidades usadas nas fórmulas de cálculo do RAS corrigido
     * @param CEaUnit unidade do CEa
     * @param elementsUnit unidade dos elementos
     */
    private void formatUnits(int CEaUnit, int elementsUnit) {
        /*
             spinners positions
             molecules:
             mmol/l = 0
             mg/l = 1
             mEq/L = 2

             CEa:
             dS/m = 0
             mS/cm = 1
             uS/cm = 2

         */
        //elements convertion

        UnitConverter uc = new UnitConverter();

        switch (elementsUnit){
            case 0:
                Ca = uc.mmolLToMeqL("Ca",Ca);
                Mg = uc.mmolLToMeqL("Mg",Mg);
                Na = uc.mmolLToMeqL("Na",Na);
                HCO3 = uc.mmolLToMeqL("HCO3",HCO3);
                //CO3;

                break;
            case 1:

                Ca = uc.mgLToMeqL("Ca",Ca);
                Mg = uc.mgLToMeqL("Mg",Mg);
                Na = uc.mgLToMeqL("Na",Na);
                HCO3 = uc.mgLToMeqL("HCO3",HCO3);

                //CO3;
                break;

            default:
                    //nothing to do, already mEq/L
                break;

        }

        //CEa convertion

        //Caso estiver no formato uS/cm, é necessário dividir por 1000 para transformar em dS/m.
        if(CEaUnit == 2)
            CEa = CEa / 1000;

    }

    /**
     * usa a fórmula para cálculo do RAS corrigido dada no apêndice 2, do livro "A qualidade da água para irrigação", autor: José Franscismar de Medeiros
     * @return valor do RAS corrigido
     */
    private Double calculateCorrectedCalcium(){

        return (0.956 + (0.0564* CEa) + (1.0645 * (Math.pow(CEa,0.09565)))) * Math.pow((HCO3/Ca),-0.667);
    }
/*
    /**
     *  O pHc se refere ao pH de equilíbrio para o CaCO3, serve para calcular a RAS ajustada e pode ser calculada de acordo com a seguinte fórmula:
     *  pHc = (pK - pKc) + p (Ca + Mg) + pAlc
     * @return valor do pHc
     /
    private Double calculaPHc() {
        Double pKpKc = calculaPKpKc();
        Double pCaMg = calculaPCaMg();
        Double pAlc = calculaPAlc();
        return  pKpKc + pCaMg + pAlc;
    }


    private Double calculaPAlc() {
        Double pAlc=0.0;
        return pAlc;
    }

    private Double calculaPCaMg() {
        Double soma = Ca + Mg;
        Double pCaMg = 0.0;
        if(soma >=0.05 && soma <0.10) {
            pCaMg = 4.6;
        }else if (soma >=0.10 && soma <0.15) {
            pCaMg = 4.3;
        }else if (soma >=0.15 && soma <0.20) {
            pCaMg = 4.1;
        }else if (soma >=0.20 && soma <0.25) {
            pCaMg = 4.0;
        }else if (soma >=0.25 && soma <0.30) {
            pCaMg = 3.9;
        }else if (soma >=0.30 && soma <0.40) {
            pCaMg = 3.8;
        }else if (soma >=0.40 && soma <0.50) {
            pCaMg = 3.7;
        }else if (soma >=0.50 && soma <0.75) {
            pCaMg = 3.6;
        }else if (soma >=0.75 && soma <1.00) {
            pCaMg = 3.4;
        }else if (soma >=1.00 && soma <1.25) {
            pCaMg = 3.3;
        }else if (soma >=1.25 && soma <1.5) {
            pCaMg = 3.2;
        }else if (soma >=1.50 && soma <2.0) {
            pCaMg = 3.4;
        }else if (soma >=2.0 && soma <2.5) {
            pCaMg = 3.0;
        }else if (soma >=2.5 && soma <3.0) {
            pCaMg = 2.9;
        }else if (soma >=3.0 && soma <4.0) {
            pCaMg = 2.8;
        }else if (soma >=4.0 && soma <5.0) {
            pCaMg = 2.7;
        }else if (soma >=5.0 && soma <6.0) {
            pCaMg = 2.6;
        }else if (soma >=6.0 && soma <8.0) {
            pCaMg = 2.5;
        }else if (soma >=8.0 && soma <10.0) {
            pCaMg = 2.4;
        }else if (soma >=10.0 && soma <12.5) {
            pCaMg = 2.3;
        }else if (soma >=12.5 && soma <15.0) {
            pCaMg = 2.2;
        }else if (soma >=15.0 && soma <20.0) {
            pCaMg = 2.1;
        }else if (soma >=20.0 && soma <30.0) {
            pCaMg = 2.0;
        }else if (soma >=30.0 && soma <50.0) {
            pCaMg = 1.8;
        }else if (soma >=50.0 && soma <80.0) {
            pCaMg = 1.6;
        }else if (soma >=80.0) {
            pCaMg = 1.4;
        }

        return pCaMg;
    }

    private Double calculaPKpKc() {
        Double soma = Ca - Mg + Na;
        Double pKpck = 0.0;
        if(soma >=0.05 && soma <0.50) {
            pKpck = 2.0;
        }else if (soma >=0.50 && soma <2.0){
            pKpck = 2.1;
        }else if(soma >=2.0 && soma <8.0){
            pKpck = 2.2;
        }else if (soma >=8.0 && soma <20.0){
            pKpck = 2.3;
        }else if (soma >=20.0 && soma <50.0){
            pKpck = 2.4;
        }else if (soma >=50.0){
            pKpck = 2.5;
        }

        return pKpck;
    }
*/
}
