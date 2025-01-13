package dev.lone.LoneLibs.data;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentArrayList<K> extends ConcurrentHashMap<K, Object>
{
    private static final Object DUMMY = (byte) 0;

    public void add(K element)
    {
        put(element, DUMMY);
    }
}
