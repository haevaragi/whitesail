/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.bit.whitesail.utils;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author baifan
 */
public class MyContainerTest {

    private MyContainer<String> s;
    private MyContainer<byte[]> b;
    private String s1;
    private String s2;


    @Before
    public void init() {
        s = new MyContainer<String>();
        b = new MyContainer<byte[]>();
        s1 = "123";
        s2 = "123";
    }

    @Test
    public void testContains() {   
     
        s.add(s1);
     
        assertEquals(true, s.contains(s2));    
    }

       @Test
    public void testAdd() {       
       
        s.add(s1);
        
        assertEquals(false, s.add(s2));

    }
    
}