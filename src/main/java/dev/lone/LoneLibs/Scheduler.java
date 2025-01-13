package dev.lone.LoneLibs;

import dev.lone.LoneLibs.annotations.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Wrapper for the Bukkit internal scheduler
 */
public class Scheduler
{
    private final Plugin plugin;
    //Class name, BukkitTask
    private final HashMap<String, List<BukkitTask>> scheduled = new HashMap<>();
    private final ExecutorService exe = Executors.newCachedThreadPool();

    public Scheduler(Plugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Returns the fully qualified name of the class which called
     * the function you call this from.
     *
     * @return the fully qualified name of the {@code Class}.
     */
    private String getCallerClassName()
    {
        // 0 = Thread
        // 1 = Scheduler(this class)
        // 2 = Scheduler(this class)
        // 3 = The actual caller class
        return Thread.currentThread().getStackTrace()[3].getClassName(); // Is it reliable?
    }

    /**
     * Unregisters all the scheduled tasks for a particular class.
     * WARNING: It will unregister all the tasks for every instance!
     * Use with caution if you have multiple instances of that class.
     *
     * @param clazz The class which registered the tasks.
     */
    @UnstableHack
    public void unregisterTasks(Class<?> clazz)
    {
        List<BukkitTask> bukkitTasks = scheduled.get(clazz.getName());
        if (bukkitTasks != null)
        {
            ListIterator<BukkitTask> iter = bukkitTasks.listIterator();
            BukkitTask entry;
            while (iter.hasNext())
            {
                entry = iter.next();
                if (entry != null)
                    entry.cancel();
                iter.remove();
            }
        }
    }

    public @NotNull BukkitScheduler get()
    {
        return Bukkit.getServer().getScheduler();
    }

    public void async(Runnable task)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    public void async(Runnable task, long delayTicks)
    {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }

    public BukkitTask asyncLoop(Consumer<BukkitRunnable> task, long delayTicks, long delay)
    {
        BukkitTask bukkitTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                task.accept(this);
            }
        }.runTaskTimerAsynchronously(plugin, delayTicks, delay);
        scheduled.computeIfAbsent(getCallerClassName(), k -> new ArrayList<>()).add(bukkitTask);
        return bukkitTask;
    }

    public void sync(Runnable task)
    {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public void sync(Runnable task, long delayTicks)
    {
        Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    public void delaySyncIf(Runnable task, long delayTicks, boolean b)
    {
        if(b)
            Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
        else
            task.run();
    }

    public BukkitTask syncLoop(Consumer<BukkitRunnable> task, long delayTicks, long delay)
    {
        BukkitTask bukkitTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                task.accept(this);
            }
        }.runTaskTimer(plugin, delayTicks, delay);
        scheduled.computeIfAbsent(getCallerClassName(), k -> new ArrayList<>()).add(bukkitTask);
        return bukkitTask;
    }

    public void syncLoop(Runnable task, long delay)
    {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, delay);
        scheduled.computeIfAbsent(getCallerClassName(), k -> new ArrayList<>()).add(bukkitTask);
    }

    /**
     * Runs an async task without any delay and without using the bukkit internal threads utility.
     *
     * WARNING: doesn't intercept exceptions! They are silently thrown
     * @param task the task to execute.
     */
    public void asyncNonBukkitIf(Runnable task, boolean b)
    {
        if(b)
            exe.submit(task);
        else
            task.run();
    }

    /**
     * Runs an async task without any delay and without using the bukkit internal threads utility.
     * WARNING: doesn't intercept exceptions! They are silently thrown
     * @param task the task to execute.
     */
    public void asyncNonBukkit(Runnable task)
    {
        exe.submit(task);
    }

    /**
     * Runs an async task with a delay and without using the bukkit internal threads utility.
     * WARNING: doesn't intercept exceptions! They are silently thrown
     *
     * @param task the task to execute.
     * @param delayMs delay.
     */
    public void asyncNonBukkit(Runnable task, int delayMs)
    {
        exe.submit(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ignored) {}
            task.run();
        });
    }

    public void syncDelayedIf(Runnable runnable, boolean b)
    {
        syncDelayedIf(runnable, b, 0L);
    }

    public void syncDelayedIf(Runnable runnable, boolean b, long delay)
    {
        if (b)
            sync(runnable, delay);
        else
            runnable.run();
    }
}
