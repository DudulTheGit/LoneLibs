package dev.lone.LoneLibs.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class Msg
{
    String PREFIX = "[LoneLibs] ";
    String PREFIX_CONSOLE = "[LoneLibs] ";
    final ColorLog LOG = new ColorLog(PREFIX);

    public Msg(String prefix)
    {
        setPrefix(prefix, prefix);
    }

    public void setPrefix(String prefix)
    {
        PREFIX = prefix;
    }

    public void setPrefixConsole(String prefix)
    {
        PREFIX_CONSOLE = prefix;
        LOG.setPrefix(prefix);
    }

    public void setPrefix(String chat, String console)
    {
        PREFIX = chat;
        setPrefixConsole(console);
    }

    public void messageConsoleAndSender(CommandSender sender, String message)
    {
        log(message);
        if (sender != null && sender != Bukkit.getConsoleSender())
            sender.sendMessage(PREFIX + message);
    }

    public void log(String message)
    {
        LOG.log(Level.INFO, message);
    }

    public void log(String error, Level level)
    {
        LOG.log(level, error);
    }

    public void error(String error)
    {
        if(ChatColor.stripColor(error).equals(error))
            LOG.log(Level.SEVERE, ChatColor.RED + error);
        else
            LOG.log(Level.SEVERE, error);
    }

    public void error(String error, @Nullable Throwable e)
    {
        if(ChatColor.stripColor(error).equals(error))
            LOG.log(Level.SEVERE, ChatColor.RED + error);
        else
            LOG.log(Level.SEVERE, error);

        if(e != null)
            e.printStackTrace();
    }

    public void warn(String error)
    {
        if(ChatColor.stripColor(error).equals(error))
            LOG.log(Level.WARNING, ChatColor.YELLOW + error);
        else
            LOG.log(Level.WARNING, error);
    }

    public void warn(String error, @Nullable Throwable e)
    {
        warn(error);
        if(e != null)
            e.printStackTrace();
    }

    public void sendNoPrefix(CommandSender commandSender, String message)
    {
        commandSender.sendMessage(message);
    }

    public void send(Player player, String message)
    {
        player.sendMessage(PREFIX + message);
    }

    public void send(CommandSender sender, String message)
    {
        sender.sendMessage(PREFIX + message);
    }

    public void sendAllOp(String message)
    {
        log(message);
        for (Player sender : Bukkit.getOnlinePlayers())
        {
            if (sender.isOp())
                sender.sendMessage(PREFIX + message);
        }
    }
}
