package br.com.ufersa.qwater.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Arlan on 14-Mar-18.
 */
public class CaTableTest {
    @Test
    public void interpolate() {
        CaTable table = new CaTable();

        assertEquals(0.3175, table.interpolate(25, 7), 0.00001);
    }

}