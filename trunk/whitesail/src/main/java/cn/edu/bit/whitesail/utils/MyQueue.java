/**
* @(#)MyQueue.java Mar 15, 2009
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

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @version
 * @author baifan
 * @since JDK 1.6
 */
public class MyQueue<T> implements Queue<T>{

    private final List<T> list= new LinkedList<T>();
    private final int capacity = 3000;
    @Override
    public void add(T t) {
        synchronized (list) {
            list.add(t);
        }
    }

    @Override
    public T get() {
          synchronized (list) {
        return list.isEmpty() ? null :  list.remove(0);
          }
    }

    @Override
    public boolean isFull() {
         synchronized (list) {
             return list.size() == capacity ? true : false;
         }
    }

    @Override
    public boolean isEmpty() {
         synchronized (list) {
             return list.isEmpty();
         }
    }

    @Override
    public void add(List<? extends T > t) {
         synchronized (list) {
             list.addAll(t);
         }
    }


}
