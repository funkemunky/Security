package com.teamsmc.security.listeners;

import com.teamsmc.security.Security;
import com.teamsmc.security.handler.SecurityUser;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class UnsecuredPreventionEvents implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEvent(PlayerMoveEvent event) {
        SecurityUser user = Security.getInstance().getUserHandler().getUser(event.getPlayer().getUniqueId());

        if(user != null && !user.isSecured()) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must enter your code."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        SecurityUser user = Security.getInstance().getUserHandler().getUser(event.getPlayer().getUniqueId());

        if(user != null && !user.isSecured()) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must enter your code."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent event) {
        if(!event.getMessage().split(" ")[0].toLowerCase().startsWith("/code")) {
            SecurityUser user = Security.getInstance().getUserHandler().getUser(event.getPlayer().getUniqueId());

            if(user != null && !user.isSecured()) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must enter your code."));
                event.setCancelled(true);
            }
        }
    }
}
