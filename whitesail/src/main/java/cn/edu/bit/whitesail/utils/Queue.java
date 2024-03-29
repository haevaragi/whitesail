/**
* @(#)Queue.java Mar 15, 2009
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

import java.util.List;

/**
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public interface Queue<T> {
    void add(T t);
    void add(List<? extends T> t);

    /**
     * fetch and remove a item from queue
     * @return item (may be null if queue is empty)
     */
    T get();
    
    boolean isFull();
    boolean isEmpty();
}
