package com.jutils.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU cache.
 * <p>
 * {@code pseudoHead.next} is the most recently used node, while {@code pseudoTail.prev} is the least recently used node.
 */
public class LruCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> pseudoHead;
    private final Node<K, V> pseudoTail;

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        pseudoHead = new Node<>();
        pseudoTail = new Node<>();
        pseudoHead.next = pseudoTail;
        pseudoTail.prev = pseudoHead;
    }

    public synchronized V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        if (node.prev != pseudoHead) {
            removeNode(node);
            setAsHeadNode(node);
        }
        return node.value;
    }

    public synchronized void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            if (map.size() < capacity) {
                node = new Node<>();
            } else {
                node = pseudoTail.prev;
                removeNode(node);
                map.remove(node.key);
            }
            node.key = key;
            node.value = value;
            map.put(key, node);
        } else {
            removeNode(node);
            node.value = value;
        }
        setAsHeadNode(node);
    }

    public synchronized void clear() {
        map.clear();
        pseudoHead.next = pseudoTail;
        pseudoTail.prev = pseudoHead;
    }

    public int size() {
        return map.size();
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void setAsHeadNode(Node<K, V> node) {
        node.prev = pseudoHead;
        node.next = pseudoHead.next;
        pseudoHead.next.prev = node;
        pseudoHead.next = node;
    }

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> prev;
        private Node<K, V> next;
    }
}
