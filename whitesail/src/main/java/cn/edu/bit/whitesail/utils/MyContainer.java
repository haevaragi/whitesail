/**
 * @(#)MyContainer.java Mar 7, 2009
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

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @version 0.1
 * @author baifan
 * @since JDK 1.6
 */
public class MyContainer<T> implements Container<T> {

    final private Set<T> container = new HashSet<T>();
    private int capacity = 3000;
    
    @Override
    public boolean  add(T t) {
        synchronized (container) {
            return container.add(t);
        }
    }

    @Override
    public void delete(T t) {
        synchronized (container) {
            container.remove(t);
        }
    }

    @Override
    public boolean contains(T t) {
        synchronized(container) {
            return container.contains(t);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized(container) {
            return container.isEmpty();
        }
    }

    @Override
    public boolean isFull() {
        synchronized(container) {
            return container.size() == capacity ? true : false;
        }
    }


}
