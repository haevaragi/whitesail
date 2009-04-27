/**
 * @(#)WhiteSail.java Mar 8, 2009
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
package cn.edu.bit.whitesail;


import cn.edu.bit.whitesail.crawl.Crawler;
import cn.edu.bit.whitesail.page.Page;
import cn.edu.bit.whitesail.page.URL;
import cn.edu.bit.whitesail.utils.Container;
import cn.edu.bit.whitesail.utils.MyContainer;
import cn.edu.bit.whitesail.utils.MyQueue;
import cn.edu.bit.whitesail.utils.Queue;
import java.io.File;

/**
 *
 * @version 0.1
 * @author baifan
 * @since 0.1
 */
public class WhiteSail {
    public final static Container<String> VISITIED_URL_TABLE = new MyContainer<String>();
    public final static Container<String> VISITIED_PAGE_TABLE = new MyContainer<String>();
    public final static Queue<URL> UNVISITED_URL_TABLE = new MyQueue<URL>();
    public final static String DATA_DIRECTORY = "./data";
    
    public static void main(String[] args) {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {             
                    directory.mkdirs();                
        }
        URL start = new URL();
        start.to = "http://www.bitren.com";
        UNVISITED_URL_TABLE.add(start);        
        new Crawler().run();
    }
}
