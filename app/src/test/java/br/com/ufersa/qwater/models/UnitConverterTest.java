package br.com.ufersa.qwater.models;

import org.junit.Test;

/**
 * Created by Arlan on 18-Sep-17.
 */
public class UnitConverterTest {
    @Test
    public void meqL_1ToMgL_1() {
        UnitConverter unitConverter = new UnitConverter();

        //assertEquals(2.5,unitConverter.mEq_LToMg_L("Mg", 50), 0.01);
    }
    @Test
    public void mgL_1ToMeqL_1() {
        UnitConverter unitConverter = new UnitConverter();
        //assertEquals(1.48,unitConverter.mg_LToMeq_L("Ca", 18), 0.01);

    }
}