package com.tr.denizusta.townywebhooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Config {
    public static String webhookUrl;
    public static boolean createTown;
    public static boolean deleteTown;
    public static boolean joinTown;
    public static boolean leaveTown;
    public static boolean addTownEnemy;
    public static boolean removeTownEnemy;
    public static boolean addTownAlly;
    public static boolean removeTownAlly;

    public static void checkConfig(){
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("TownyWebhooks");
        webhookUrl = plugin.getConfig().getString("webhook-url");
        createTown = plugin.getConfig().getBoolean("towny.create-town.enabled");
        deleteTown = plugin.getConfig().getBoolean("towny.delete-town-town.enabled");
        joinTown = plugin.getConfig().getBoolean("towny.join-town.enabled");
        leaveTown = plugin.getConfig().getBoolean("towny.leave-town.enabled");
        addTownEnemy = plugin.getConfig().getBoolean("towny.add-enemy.enabled");
        removeTownEnemy = plugin.getConfig().getBoolean("towny.remove-enemy.enabled");
        addTownAlly = plugin.getConfig().getBoolean("towny.remove-ally.enabled");
        removeTownAlly = plugin.getConfig().getBoolean("towny.remove-ally.enabled");
    }
}
