package br.com.ufersa.qwater.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Arlan on 18-Sep-17.
 */
public class UnitConverterTest {
    @Test
    public void meqL_1ToMgL_1() throws Exception {
        UnitConverter unitConverter = new UnitConverter();

        assertEquals(2.5,unitConverter.meqL_1ToMgL_1("Ca", 50), 0.01);
    }
    @Test
    public void mgL_1ToMeqL_1() throws Exception{
        UnitConverter unitConverter = new UnitConverter();
        assertEquals(1.48,unitConverter.mgL_1ToMeqL_1("Mg", 18), 0.01);

    }
}