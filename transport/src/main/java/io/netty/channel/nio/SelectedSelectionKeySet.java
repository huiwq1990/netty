/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

//！！对于keyset的操作是单线程操作，底层使用数组实现
final class SelectedSelectionKeySet extends AbstractSet<SelectionKey> {
    /**
     * 维持了一个数组，而不是HashSet
     */
    SelectionKey[] keys;
    int size;

    SelectedSelectionKeySet() {
        keys = new SelectionKey[1024];
    }

//    主需要这个方法，改方法直接操作数组，实现时间复杂度为O(1)
    @Override
    public boolean add(SelectionKey o) {
        if (o == null) {
            return false;
        }

        keys[size++] = o;
        if (size == keys.length) {
            increaseCapacity();
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }
    //不支持
    @Override
    public boolean remove(Object o) {
        return false;
    }
    //不支持
    @Override
    public boolean contains(Object o) {
        return false;
    }
    //不支持
    @Override
    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }

    void reset() {
        reset(0);
    }

    void reset(int start) {
        Arrays.fill(keys, start, size, null);
        size = 0;
    }

    private void increaseCapacity() {
        SelectionKey[] newKeys = new SelectionKey[keys.length << 1];
        System.arraycopy(keys, 0, newKeys, 0, size);
        keys = newKeys;
    }
}
