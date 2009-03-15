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

import cn.edu.bit.whitesail.WhiteSail;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
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
    public List<String> extractURLFromContent(byte[] rawContents,String anchor) {
        List<String> result = new ArrayList<String>();
        try {
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(new ByteArrayInputStream(rawContents)));
            getLinks(parser.getDocument(), result,anchor);
        } catch (SAXException ex) {
            LOG.warn("document parsing error");
            result = null;
        } catch (IOException ex) {
            LOG.warn("document parsing error");
            result = null;
        }
        return result;
    }

    private void getLinks(Node node, List<String> URLsToFill,String anchor) {
        String URL = null;
        if (node.getNodeName().equalsIgnoreCase("a") || node.getNodeName().equalsIgnoreCase("link")) {
            NamedNodeMap map = node.getAttributes();
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node item = map.item(i);
                if (item.getNodeName().equalsIgnoreCase("href")) {
                       URL = URLFormat(item.getNodeValue(),anchor);
                       if (null != URL)
                       URLsToFill.add(URL);
                }                        
            }
        }
        Node child = node.getFirstChild();
        while (child != null) {
            getLinks(child, URLsToFill,anchor);
            child = child.getNextSibling();
        }
    }

    private String URLFormat(String URL,String anchor) {
        String formatedURL = null;
        if (null == URL || URL.equals("") || URL.contains("#"))
            return null;

        if (!URL.startsWith("http://")) {
            formatedURL = anchor + URL;
        } else {
            formatedURL = URL;
        }        
        return formatedURL;
    }
}
