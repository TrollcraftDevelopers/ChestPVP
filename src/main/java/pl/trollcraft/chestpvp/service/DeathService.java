package pl.trollcraft.chestpvp.service;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class DeathService implements Listener {

    private final Plugin plugin;

    public DeathService (Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler (ignoreCancelled = true)
    public void onKillAttempt(EntityDamageByEntityEvent event) {

        Entity damaging = event.getDamager();
        Entity victim = event.getEntity();

        if (victim.getType() != EntityType.PLAYER) return;

        Player victimPlayer = (Player) victim, damagingPlayer;

        if (damaging.getType() == EntityType.PLAYER) {
            damagingPlayer = (Player) damaging;

            if (death(victimPlayer, event.getFinalDamage())) {
                event.setCancelled(true);

                victimPlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cUmierasz"),
                        ChatColor.translateAlternateColorCodes('&', "&e" + damagingPlayer.getName() + " &czabija Cie"));

                PlayerDeathEvent deathEvent = new PlayerDeathEvent(victimPlayer,
                        Arrays.asList(victimPlayer.getInventory().getContents()), 0, "");
                Bukkit.getPluginManager().callEvent(deathEvent);

                //ScoreboardHandler.update(damagerPlayer);
                //ScoreboardHandler.update(victimPlayer);
            }

        }
        else if (damaging.getType() == EntityType.ARROW) {

            damagingPlayer = (Player) ((Arrow)damaging).getShooter();

            if (death(victimPlayer, event.getFinalDamage())) {
                event.setCancelled(true);

                victimPlayer.spigot().sendMessage(new TextComponent(""));

                victimPlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cUmierasz"),
                        ChatColor.translateAlternateColorCodes('&', "&e" + damagingPlayer.getName() + " &czabija Cie"));

                PlayerDeathEvent deathEvent = new PlayerDeathEvent(victimPlayer,
                        Arrays.asList(victimPlayer.getInventory().getContents()), 0, "");
                Bukkit.getPluginManager().callEvent(deathEvent);

                //ScoreboardHandler.update(damagerPlayer);
                //ScoreboardHandler.update(victimPlayer);
            }

        }

    }

    private boolean death(Player player, double finalDamage) {
        if (player.getHealth() - finalDamage <= 0) {

            //ScoreboardHandler.update(player);

            player.closeInventory();
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            //Drop.drop(player);
            player.setExp(0);
            player.setLevel(0);

            player.setFireTicks(0);
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.setFireTicks(0);
                }

            }.runTaskLater(plugin, 10);

            for(PotionEffect effect:player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());

            player.teleport(player.getLocation().getWorld().getSpawnLocation());
            //giveKit(player);
            return true;

        }
        else return false;
    }


}
