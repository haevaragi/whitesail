/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.bit.whitesail.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author baifan
 */
public class WhiteSailConfigTest {




    /**
     * Test of loadConfig method, of class WhiteSailConfig.
     */
    @Test
    public void testLoadConfig() {
        System.out.println("loadConfig");
        WhiteSailConfig instance = new WhiteSailConfig();
        instance.loadConfig();

        assertEquals(true, WhiteSailConfig.DATA_DIRECTORY != null);
        assertEquals(true, WhiteSailConfig.DEFAULT_ENCODING != null);
    }

}