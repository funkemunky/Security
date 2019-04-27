package com.teamsmc.security;

import com.teamsmc.commons.Commons;
import com.teamsmc.security.commands.CodeCommand;
import com.teamsmc.security.commands.ResetCodeCommand;
import com.teamsmc.security.commands.SetCodeCommand;
import com.teamsmc.security.handler.UserHandler;
import com.teamsmc.security.listeners.SecurityCheckEvents;
import com.teamsmc.security.listeners.UnsecuredPreventionEvents;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public class Security extends JavaPlugin {

    private UserHandler userHandler;
    @Getter
    private static Security instance;

    public void onEnable() {
        instance = this;
        userHandler = new UserHandler();
        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new SecurityCheckEvents(), this);
        getServer().getPluginManager().registerEvents(new UnsecuredPreventionEvents(), this);
    }

    private void registerCommands() {
        Commons.getInstance().getCommandFramework().registerCommands(new CodeCommand());
        Commons.getInstance().getCommandFramework().registerCommands(new SetCodeCommand());
        Commons.getInstance().getCommandFramework().registerCommands(new ResetCodeCommand());
    }

    public String getMD5EncryptedValue(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return password;
    }
}
