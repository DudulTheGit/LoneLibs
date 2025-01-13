package dev.lone.LoneLibs;

import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    Metrics metrics;
    public static Main inst;

    @Override
    public void onEnable()
    {
        inst = this;

        try
        {
            FastInvManager.register(this);
        }
        catch(IllegalStateException ignored){}

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            metrics = new Metrics(this, 8217);
        });
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
}
