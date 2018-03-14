package br.com.ufersa.qwater.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Arlan on 14-Mar-18.
 */
public class CaTableTest {
    @Test
    public void interpolate() throws Exception {
        CaTable table = new CaTable();

        assertEquals(3, table.interpolate(0.4, 0.3), 0.00001);
    }

}