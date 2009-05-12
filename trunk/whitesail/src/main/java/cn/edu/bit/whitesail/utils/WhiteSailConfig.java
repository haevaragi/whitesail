/**
* @(#)WhiteSailConfig.java Apr 28, 2009
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


package cn.edu.bit.whitesail.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * 
 * @author baifan
 * @since 
 */
public class WhiteSailConfig {

    private final static Log LOG = LogFactory.getLog(WhiteSailConfig.class);
    
    public void loadConfig() {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedInputStream(new FileInputStream("./src/main/config/whitesail.properties")));
            DATA_DIRECTORY = prop.getProperty("dataDirectory");
            DEFAULT_ENCODING = prop.getProperty("defaultEncoding");
        } catch (IOException ex) {
            LOG.fatal("config file load error( missing config file?)");
        }
    }

    public static String DATA_DIRECTORY;

    public static String DEFAULT_ENCODING;



}
