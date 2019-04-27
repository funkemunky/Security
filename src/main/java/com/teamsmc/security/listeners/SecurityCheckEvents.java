package com.teamsmc.security.listeners;

import com.teamsmc.commons.Commons;
import com.teamsmc.commons.rank.Rank;
import com.teamsmc.commons.user.User;
import com.teamsmc.commons.utility.StringUtil;
import com.teamsmc.security.Security;
import com.teamsmc.security.handler.SecurityUser;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SecurityCheckEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerJoinEvent event) {
        User user = Commons.getInstance().getUserManager().getUserById(event.getPlayer().getUniqueId());

        if(user.hasRank(Rank.MOD_PLUS)) {
            SecurityUser securityUser = Security.getInstance().getUserHandler().loadUser(user.getUuid());

            if(securityUser.getCode() != null) {
                if(!securityUser.needsToBeAuthenticated()) {
                    user.getPlayer().sendMessage(ChatColor.GREEN + "You have bypassed the authentication process since your IP has not changed.");
                    securityUser.setSecured(true);
                } else {
                    securityUser.setSecured(false);
                }
            } else {
                user.getPlayer().kickPlayer(StringUtil.translate("&6&lTeamsMC Security\n&fYou have been disconnected for security purposes.\n&7Contact a &c&oPlatformAdmin &7or above."));
            }
        }
    }
}
