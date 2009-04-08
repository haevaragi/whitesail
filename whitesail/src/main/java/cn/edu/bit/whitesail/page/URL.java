/**
* @(#)URL.java Apr 6, 2009
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

/**
 *
 * 
 * @author baifan
 * @since 
 */
 public class URL {
        public String from;
        public String to;

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof URL && ((URL)obj).to.equals(this.to));
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + (this.to != null ? this.to.hashCode() : 0);
            return hash;
        }
    }
