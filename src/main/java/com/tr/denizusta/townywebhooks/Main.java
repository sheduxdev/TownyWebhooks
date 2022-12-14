package com.tr.denizusta.townywebhooks;

import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.event.PlayerEnterTownEvent;
import com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent;
import com.palmergames.bukkit.towny.event.town.TownAddAlliedTownEvent;
import com.palmergames.bukkit.towny.event.town.TownAddEnemiedTownEvent;
import com.palmergames.bukkit.towny.event.town.TownRemoveAlliedTownEvent;
import com.palmergames.bukkit.towny.event.town.TownRemoveEnemiedTownEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tr.denizusta.townywebhooks.Config.checkConfig;
import static com.tr.denizusta.townywebhooks.Webhook.webhookURL;

public final class Main extends JavaPlugin implements Listener {

    Plugin plugin;

    @Override
    public void onEnable() {
        plugin = Bukkit.getPluginManager().getPlugin("TownyWebhooks");
        getServer().getPluginManager().registerEvents(this, this);
        if(!new File(getDataFolder(),"config.yml").exists()){
            saveDefaultConfig();
        }
        checkConfig();
        if(Config.webhookUrl.equals("")) {
            getLogger().warning("Webhook URL'si girmediğiniz için eklenti devre dışı bırakılıyor.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void createTown(NewTownEvent event) {
        if(Config.createTown == true) {
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Yeni bir kasaba belirdi!").addField("Kasaba Adı",event.getTown().getName(), true).addField("Kasaba Başkanı", event.getTown().getMayor().getName(), true).addField("Kurulma Tarihi", format.format(now), true));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void deleteTown(DeleteTownEvent event) {
        if(Config.deleteTown == true) {
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Bir kasaba aramızdan ayrıldı...").addField("Kasaba Adı",event.getTownName(), true).addField("Kasaba Başkanı", event.getMayor().getName(), true).addField("Yıkılma Tarihi", format.format(now), true));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void joinTown(PlayerEnterTownEvent event) {
        if(Config.joinTown == true) {
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle(event.getPlayer().getName()+" bir kasabaya katıldı.").addField("Katıldığı Kasaba",event.getEnteredtown().getName(), true).addField("Kasaba Başkanı", event.getEnteredtown().getMayor().getName(), true).addField("Katıldığı Tarih", format.format(now), true));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void leaveTown(PlayerLeaveTownEvent event) {
        if(Config.leaveTown == true) {
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle(event.getPlayer().getName()+" bulunduğu kasabadan ayrıldı.").addField("Ayrıldığı Kasaba",event.getLefttown().getName(), true).addField("Kasaba Başkanı", event.getLefttown().getMayor().getName(), true).addField("Ayrıldığı Tarih", format.format(now), true));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void addTownEnemy(TownAddEnemiedTownEvent event) {
        if(Config.addTownEnemy == true) {
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Sanırım bir şeyler ters dönüyor...").setDescription("**"+event.getTown().getName()+"** kasabası **"+event.getNewEnemy().getName()+"** kasabasını düşmanı olarak belirledi."));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void removeTownEnemy(TownRemoveEnemiedTownEvent event) {
        if(Config.removeTownEnemy == true) {
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Eski dostları yeniden bir arada görmek güzel...").setDescription("**"+event.getTown().getName()+"** kasabası **"+event.getRemovedEnemy().getName()+"** kasabası ile barış yaptı."));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void addTownAlly(TownAddAlliedTownEvent event) {
        if(Config.addTownAlly == true) {
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Sanırsam ittifaklar toplanıyor...").setDescription("**"+event.getTown().getName()+"** kasabası **"+event.getNewAlly().getName()+"** kasabasını ittifak olarak belirledi."));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }
    @EventHandler
    public void removeTownAlly(TownRemoveAlliedTownEvent event) {
        if(Config.removeTownAlly == true) {
            Webhook discordWebhook = new Webhook(webhookURL);
            discordWebhook.addEmbed(new Webhook.EmbedObject().setColor(Color.orange).setTitle("Ters giden bir şey var...").setDescription("**"+event.getTown().getName()+"** kasabası **"+event.getRemovedAlly().getName()+"** kasabasını ittifaklıktan çıkardı."));
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                getLogger().severe(e.getStackTrace().toString());
            }
        }
    }

}
