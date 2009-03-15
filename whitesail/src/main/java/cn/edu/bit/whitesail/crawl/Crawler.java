/**
 * @(#)Crawler.java Mar 7, 2009
 *
 *Copyright 2009 BaiFan
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package cn.edu.bit.whitesail.crawl;

import cn.edu.bit.whitesail.WhiteSail;
import cn.edu.bit.whitesail.parser.HtmlParser;
import cn.edu.bit.whitesail.parser.Parser;
import cn.edu.bit.whitesail.utils.MD5Signature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public class Crawler implements Runnable {

    private final static Log LOG = LogFactory.getLog(Crawler.class);
    private HttpClient httpClient;
    private static transient boolean isFinished = false;
    private Parser parser;

    public Crawler () {
        httpClient = new DefaultHttpClient();
        parser = new HtmlParser();
    }
    
    @Override
    public void run() {
        while (!isFinished) {
            String anchor = fetchURL();
            WhiteSail.VISITIED_URL_TABLE.add(anchor);
            if (anchor == null)
                continue;
            if (LOG.isInfoEnabled()) {
                LOG.info("download "+ anchor);
            }
            byte[] contents = download(anchor);
            if (contents == null)
                continue;
            if (!WhiteSail.VISITIED_PAGE_TABLE.add(MD5Signature.calculate(contents)))
                continue;
            
            WhiteSail.UNVISITED_URL_TABLE.add(parser.extractURLFromContent(contents, anchor));            
        }
    }

    private String fetchURL() {
        String result = null;
        while ((result = WhiteSail.UNVISITED_URL_TABLE.get()) == null) {
            try {
                LOG.info("unvisitable is empty , crawler sleeps for a while");
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                LOG.warn("crawler therad catch InterruptedException");
                continue;
            }
        }
        return result;
    }

    private byte[] download(String URL) {
        byte[] result = null;
        try {
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse response = httpClient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            result = out.toByteArray();
        } catch (IOException ex) {
            LOG.warn("downloading error,abandon");
            result = null;
        }
        return result;
    }

   
}
