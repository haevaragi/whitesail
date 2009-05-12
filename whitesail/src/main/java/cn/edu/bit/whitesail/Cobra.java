package cn.edu.bit.whitesail;
import org.lobobrowser.html.*;
import org.lobobrowser.html.test.*;
import org.lobobrowser.html.parser.*;
import org.lobobrowser.html.domimpl.*;

import org.w3c.dom.*;
import org.w3c.dom.html2.*;

import java.net.*;
import java.io.*;


public class Cobra {
    private static final String TEST_URI = "http://localhost/test.html";

    public static void main(String[] args) throws Exception {
        UserAgentContext uacontext = new SimpleUserAgentContext();
        //((SimpleUserAgentContext)uacontext).setExternalCSSEnabled(false);
        ((SimpleUserAgentContext)uacontext).setScriptingEnabled(true);
        DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
        URL url = new URL(TEST_URI);
      
        InputStream in = url.openConnection().getInputStream();
        int length = 0;
        HTMLCollection images = null;
        try {
            Reader reader = new InputStreamReader(in, "ISO-8859-1");
            InputSourceImpl inputSource = new InputSourceImpl(reader, TEST_URI);
            Document d = builder.parse(inputSource);
            HTMLDocumentImpl document = (HTMLDocumentImpl) d;
            
            images = document.getLinks();
            length = images.getLength();
            
        } finally {
            in.close();
        }
         for(int i = 0; i < length; i++) {
                System.out.println(i + ": " + images.item(i));
            }
        System.out.println(length);
    }
}