/**
* @(#)Page.java Mar 7, 2009
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


package cn.edu.bit.whitesail.page;


import cn.edu.bit.whitesail.utils.MD5Signature;
import java.util.Calendar;


/**
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public final class Page implements java.io.Serializable{
  

    public String URL;

    public String fromURL;
    
    public byte[] rawContent;

    public String MD5Hash;

    public int length;

    public String encoding;

    public String modifyTime;

    public Page(byte[] rawContents, String detectedEncoding) {
         encoding = detectedEncoding;
         this.rawContent =  rawContents;
         MD5Hash = MD5Signature.calculate(rawContent);
         length = rawContents.length;
         modifyTime = Calendar.getInstance().getTime().toString();
    }

  
  

    @Override
    public boolean equals(Object obj) {        
        return (obj instanceof Page && ((Page)obj).MD5Hash.equals(MD5Hash));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.rawContent != null ? this.rawContent.hashCode() : 0);
        return hash;
    }

    public byte[] toByteArray() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append(MD5Hash);
        sb.append("\n");
        sb.append(URL);
        sb.append("\n");
        sb.append(fromURL);
        sb.append(length);
        sb.append("\n");
        sb.append(modifyTime);
        sb.append("\n");
        sb.append(encoding);
        sb.append("\n");
        return sb.toString().getBytes();
    }
}
