package me.ryans.moneyplugin;

import me.ryans.moneyplugin.commands.ConvertTabCompletion;
import me.ryans.moneyplugin.commands.TutorialCommands;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;


public class MoneyPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        TutorialCommands commands = new TutorialCommands();
        getCommand("exchange").setExecutor(commands);
        getCommand("totalbalance").setExecutor(commands);
        getCommand("exchangerate").setExecutor(commands);
        getCommand("convert").setExecutor(commands);
        getCommand("convert").setTabCompleter(new ConvertTabCompletion());
        getCommand("exchange").setTabCompleter(new ConvertTabCompletion());
        getServer().getPluginManager().registerEvents(this,this);
    }
}
