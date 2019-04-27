package com.teamsmc.security.commands;

import com.teamsmc.commons.rank.Rank;
import com.teamsmc.commons.utility.command.Command;
import com.teamsmc.commons.utility.command.CommandArgs;
import com.teamsmc.security.Security;
import com.teamsmc.security.handler.SecurityUser;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class CodeCommand {

    @Command(name = "code", usage = "/code <code>", rankOnly = Rank.MOD_PLUS, playerOnly = true)
    public void onCommand(CommandArgs args) {
        new BukkitRunnable() {
            public void run() {
                SecurityUser user = Security.getInstance().getUserHandler().getUser(args.getPlayer().getUniqueId());

                if(!user.isSecured()) {
                    if(args.length() == 1) {
                        String code = args.getArgs(0);

                        if(user.getCode().equals(Security.getInstance().getMD5EncryptedValue(code))) {
                            user.setSecured(true);
                            user.setIp(args.getSenderUser().getIpAddress());
                            Security.getInstance().getUserHandler().saveUser(user);
                            args.getSender().sendMessage(ChatColor.GREEN + "You have been sucecssfully authenticated.");
                        } else {
                            args.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&6&lTeamsMC Security\n&fYou have been disconnected for security purposes.\n&7Incorrect code."));
                        }
                    } else {
                        args.getSender().sendMessage(ChatColor.RED + "Usage: /code <code>");
                    }
                } else {
                    args.getSender().sendMessage(ChatColor.RED + "You are already secured. No need to type in your code again.");
                }
            }
        }.runTaskAsynchronously(Security.getInstance());

    }
}
