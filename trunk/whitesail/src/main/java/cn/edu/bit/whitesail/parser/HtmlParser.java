/**
 * @(#)HtmlParser.java Mar 15, 2009
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
package cn.edu.bit.whitesail.parser;

import cn.edu.bit.whitesail.page.Page;
import cn.edu.bit.whitesail.page.URL;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.mozilla.intl.chardet.nsDetector;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @version
 * @author baifan
 * @since JDK 1.6
 */
public class HtmlParser implements Parser {

    private final static Log LOG = LogFactory.getLog(HtmlParser.class);

    @Override
    public List<URL> extractURLFromContent(Page page) {
        List<URL> result = new ArrayList<URL>();
        charDetectAndSet(page);
        try {
            DOMParser parser = new DOMParser();
            parser.setProperty("http://cyberneko.org/html/properties/default-encoding", page.encoding);
            parser.parse(new InputSource(new ByteArrayInputStream(page.rawContent)));
            getLinks(parser.getDocument(), result, page.URL);
        } catch (SAXException ex) {
            LOG.warn("document parsing error");
            result = null;
        } catch (IOException ex) {
            LOG.warn("document parsing error");
            result = null;
        }
        return result;
    }

    private void getLinks(Node node, List<URL> URLsToFill, String anchor) {
        URL u = null;
        
        if (node.getNodeName().equalsIgnoreCase("a") || node.getNodeName().equalsIgnoreCase("link")) {
            NamedNodeMap map = node.getAttributes();
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node item = map.item(i);
                if (item.getNodeName().equalsIgnoreCase("href")) {
                    u = URLFormat(item.getNodeValue(), anchor);
                    if (null != u) {
                        URLsToFill.add(u);
                    }
                }
            }
        }
        Node child = node.getFirstChild();
        while (child != null) {
            getLinks(child, URLsToFill, anchor);
            child = child.getNextSibling();
        }
    }

    private URL URLFormat(String to, String from) {
        URL formatedURL = new URL();
        if (null == to || to.equals("") || to.contains("#")) {
            return null;
        }

        if (!to.startsWith("http://")) {
            formatedURL.to = from + to;
        } else {
            formatedURL.to = to;
        }
        formatedURL.from = from;
        return formatedURL;
    }

    private void charDetectAndSet(Page page) {
        if (page.rawContent.length >= 4 && page.rawContent[0] == (byte) 0xEF && page.rawContent[1] == (byte) 0xBB && page.rawContent[2] == (byte) 0xBF) {
            page.encoding = "utf-8";
        } else if (page.rawContent.length >= 2 && ((page.rawContent[0] == (byte) 0xFF && page.rawContent[1] == 0xFE) || (page.rawContent[0] == (byte) 0xFE && page.rawContent[1] == 0xFF))) {
            page.encoding = "utf-16";
        } else if (page.rawContent.length >= 4 && ((page.rawContent[0] == 0 && page.rawContent[1] == 0 && page.rawContent[2] == (byte) 0xFE && page.rawContent[3] == (byte) 0xFF) || (page.rawContent[0] == (byte) 0xFF && page.rawContent[1] == (byte) 0xFE && page.rawContent[2] == 0 && page.rawContent[3] == 0))) {
            page.encoding = "utf-32";
        } else  {
            try {
                String temp = new String(page.rawContent, "ISO-8859-1");
                Matcher matcher = htmlCharsetPattern.matcher(temp);
                if (matcher.find()) {
                    page.encoding = matcher.group(1).toLowerCase();
                } else {
                    matcher = xmlCharsetPattern.matcher(temp);
                    if (matcher.find()) {
                        page.encoding = matcher.group(1).toLowerCase();
                    }
                }
            } catch (UnsupportedEncodingException ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("UnsupportedEncoding");
                }
            }
        }

        if (page.encoding == null) {
            nsDetector detecter = new nsDetector();
            detecter.DoIt(page.rawContent, page.rawContent.length, false);
            String[] charsets = detecter.getProbableCharsets();
            if (charsets != null && charsets.length > 0) {
                page.encoding = charsets[0];
            }            
        }

      
    }

    static
     Pattern htmlCharsetPattern = Pattern.compile(
            "charset\\s*=\\s*([0-9a-zA-Z\\-\\.\\:\\_]+)", Pattern.DOTALL + Pattern.CASE_INSENSITIVE);
    static Pattern xmlCharsetPattern = Pattern.compile(
            "encoding\\s*=\\s*\"?([0-9a-zA-Z\\\\-\\\\.\\\\:\\\\_]+)\"?",
            Pattern.DOTALL + Pattern.CASE_INSENSITIVE);
    
}
