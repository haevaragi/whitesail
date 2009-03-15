/**
* @(#)MD5Signature.java Mar 8, 2009
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public class MD5Signature {

    private final static Log LOG = LogFactory.getLog(MD5Signature.class);

    public static String calculate(byte[] byteArray) {
        StringBuffer result = null;
        if (byteArray == null)
            return null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(byteArray);
            result = new StringBuffer(new BigInteger(1, m.digest()).toString(16));
            for (int i = 0; i < 32 - result.length(); i++)
               result.insert(0, '0');
        }
        catch (NoSuchAlgorithmException ex) {
            LOG.fatal("MD5 Hashing Failed,System is going down");
            System.exit(1);
        }
        return result.toString();
    }

}
