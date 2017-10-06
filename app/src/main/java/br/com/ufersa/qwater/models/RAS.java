package br.com.ufersa.qwater.models;


import static java.lang.Math.sqrt;

public class RAS {
    private Double Ca;
    private Double Mg;
    private Double Na;
    private Double CO3;
    private Double HCO3;
    private Double CE;
    private Double pHc;

    public RAS(Double Ca, Double Mg, Double Na, Double CE, Double HCO3){
        this.Ca = Ca;
        this.Mg = Mg;
        this.Na = Na;
        this.CE = CE;
        this.HCO3 = HCO3;

    }

    public Double calculaRas(){

        return Na / sqrt((Ca+Mg)/2);
    }

    public Double calculaRASCorrigido(){

        return Na / sqrt( (calculaCalcioCorrigido()+Mg) /2 );
    }


    private Double calculaCalcioCorrigido(){

        return (0.956 + (0.0564*CE) + (1.0645 * (Math.pow(CE,0.09565)))) * Math.pow((HCO3/Ca),-0.667);
    }

    /**
     *  O pHc se refere ao pH de equilíbrio para o CaCO3, serve para calcular a RAS ajustada e pode ser calculada de acordo com a seguinte fórmula:
     *  pHc = (pK - pKc) + p (Ca + Mg) + pAlc
     * @return valor do pHc
     */
    private Double calculaPHc() {
        Double pKpKc = calculaPKpKc();
        Double pCaMg = calculaPCaMg();
        Double pAlc = calculaPAlc();
        return  pKpKc + pCaMg + pAlc;
    }

    //TODO fazer
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

}
