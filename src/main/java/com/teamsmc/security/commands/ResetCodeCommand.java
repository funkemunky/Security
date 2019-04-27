package com.teamsmc.security.commands;

import com.teamsmc.commons.rank.*;
import com.teamsmc.commons.utility.command.Command;
import com.teamsmc.commons.utility.command.*;
import com.teamsmc.security.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class ResetCodeCommand {

    @Command(name = "resetcode", rankOnly = Rank.DEVELOPER)
    public void onCommand(CommandArgs args) {
        if(args.getSender() instanceof ConsoleCommandSender) {
            if(args.length() > 0) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args.getArgs(0));

                Security.getInstance().getUserHandler().resetUser(player.getUniqueId());

                args.getSender().sendMessage(ChatColor.GREEN + "If " + player.getName() + " has a profile, it has been removed.");
            } else {
                args.getSender().sendMessage(ChatColor.RED + "Usage: /resetcode <player>");
            }
        } else {
            args.getSender().sendMessage(ChatColor.RED + "Can only be done via console.");
        }
    }

}
