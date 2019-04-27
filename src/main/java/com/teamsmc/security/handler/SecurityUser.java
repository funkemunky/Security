package com.teamsmc.security.handler;

import com.teamsmc.commons.Commons;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SecurityUser {
    private UUID uuid;
    private String ip, code;
    private boolean secured;

    public SecurityUser(UUID uuid, String ip) {
        this.uuid = uuid;
        this.ip = ip;
        secured = false;
    }

    public boolean needsToBeAuthenticated() {
        return !ip.equals(Commons.getInstance().getUserManager().getUserById(uuid).getIpAddress());
    }
}
