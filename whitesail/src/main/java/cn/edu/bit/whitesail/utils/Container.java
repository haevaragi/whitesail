/**
* @(#)Container.java Mar 7, 2009
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

/**
 *<p>I need a Container ,so this interface <code> Container</code> will
 * define all the functions that I need.
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public interface Container<T> {
    /**
     * add a item t to the Container
     * @param t
     */
    boolean add(T t);

    /**
     * delete a item t from the Container
     * @param t
     */
    void delete(T t);

    /**
     * testing  whether the Container contains this item t.
     * @param t
     * @return the result
     */
    boolean contains(T t);

    boolean isEmpty();

    boolean isFull();
    
}
