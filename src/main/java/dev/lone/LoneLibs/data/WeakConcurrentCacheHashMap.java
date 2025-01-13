package dev.lone.LoneLibs.data;

import dev.lone.LoneLibs.Main;
import dev.lone.LoneLibs.Scheduler;
import org.jetbrains.annotations.Nullable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

// Do not use this shit anymore! It's not working correctly if the plugin isn't initialized yet,
// for example in onLoad event.
@Deprecated
public class WeakConcurrentCacheHashMap<K, V> extends ConcurrentHashMap<K, V>
{
    private final Map<K, Long> times = new ConcurrentHashMap<>();
    private long expirationTicks = 20 * 60 * 5;
    @Nullable private BukkitTask bukkitTask;

    public WeakConcurrentCacheHashMap()
    {
        init();
    }

    public WeakConcurrentCacheHashMap(long expirationTicks)
    {
        this.expirationTicks = expirationTicks;
        init();
    }

    public void init()
    {
        Scheduler scheduler = new Scheduler(Main.inst);
        bukkitTask = scheduler.asyncLoop((task) -> {
            cleanup();
        }, expirationTicks, expirationTicks);
    }

    @Override
    public V put(@NotNull K key, @NotNull V value)
    {
        times.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for (Entry<? extends K, ? extends V> entry : m.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public V putIfAbsent(K key, V value)
    {
        V prevValue = super.putIfAbsent(key, value);
        times.put(key, System.currentTimeMillis());
        return prevValue;
    }

    @Override
    public V remove(@NotNull Object key)
    {
        V remove = super.remove(key);
        if(remove != null)
        {
            times.remove(key);
            if(size() == 0)
                cancelTask();
        }

        return remove;
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        boolean remove = super.remove(key, value);
        if(remove)
        {
            times.remove(key, value);
            if(size() == 0)
                cancelTask();
        }

        return remove;
    }

    public void deleteCompletely()
    {
        clear();
        times.clear();
        cancelTask();
    }

    private void cleanup()
    {
        long currentMs = System.currentTimeMillis();
        for (Iterator<Entry<K, Long>> iterator = times.entrySet().iterator(); iterator.hasNext(); )
        {
            Entry<K, Long> entry = iterator.next();
            if (currentMs >= (times.get(entry.getKey()) + expirationTicks))
            {
                iterator.remove();
                times.remove(entry.getKey());
            }
        }

        if(size() == 0)
            cancelTask();
    }

    private void cancelTask()
    {
        if(bukkitTask == null)
            return;
        bukkitTask.cancel();
        bukkitTask = null;
    }
}