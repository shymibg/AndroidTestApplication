package com.com.dataTypes;

/**
 * Created by Shymi on 28.2.2016 г..
 */
public class KeyValuePair<K,V> {
    private K key;
    private V value;

    public KeyValuePair()
    {
    }

    public KeyValuePair(K key, V value)
    {
        this.key=key;
        this.value=value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}