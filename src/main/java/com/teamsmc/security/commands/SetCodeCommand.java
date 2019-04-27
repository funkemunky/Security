package com.teamsmc.security.commands;

import com.teamsmc.commons.rank.Rank;
import com.teamsmc.commons.utility.command.Command;
import com.teamsmc.commons.utility.command.CommandArgs;
import com.teamsmc.security.Security;
import com.teamsmc.security.handler.SecurityUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class SetCodeCommand {
    @Command(name = "setcode", rankOnly = Rank.PLAT_ADMIN)
    public void onCommand(CommandArgs args) {
        if(args.length() == 4) {
            String password = args.getArgs(0),
                    playerName = args.getArgs(1),
                    code = args.getArgs(2),
                    confirmCode = args.getArgs(3);

            OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

            if(password.equals("jerryateacherry123*") || args.getSender() instanceof ConsoleCommandSender) {
                Security.getInstance().getUserHandler().loadUser(target.getUniqueId());

                SecurityUser user = Security.getInstance().getUserHandler().getUser(target.getUniqueId());
                if(code.equals(confirmCode)) {
                    user.setCode(Security.getInstance().getMD5EncryptedValue(code));
                    Security.getInstance().getUserHandler().saveUser(user);
                    args.getSender().sendMessage(ChatColor.GREEN + target.getName() + "'s code has been set.");
                } else {
                    args.getSender().sendMessage(ChatColor.RED + "The codes provided did not match.");
                }
            } else if(password.equalsIgnoreCase("console")) {
                args.getSender().sendMessage(ChatColor.RED + "You can only do this in console.");
            } else {
                args.getSender().sendMessage(ChatColor.RED + "You entered the admin-password incorrectly.");
            }
        } else {
            args.getSender().sendMessage(ChatColor.RED + "/setcode <admin-password/console> <player> <code> <confirm-code>");
        }
    }
}
