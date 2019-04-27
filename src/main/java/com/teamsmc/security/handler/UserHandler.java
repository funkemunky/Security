package com.teamsmc.security.handler;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.teamsmc.commons.Commons;
import com.teamsmc.commons.rank.Rank;
import org.bson.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserHandler {
    private List<SecurityUser> securityUsers;
    private MongoCollection<Document> userCollection;

    public UserHandler() {
        userCollection = Commons.getInstance().getMongo().getMongoDatabase().getCollection("SecurityUsers");
        securityUsers = Lists.newArrayList();

    }

    public SecurityUser loadUser(UUID uuid) {
        SecurityUser user;

        Document doc = userCollection.find(Filters.eq("uuid", uuid.toString())).first();

        if(doc != null) {
            user = new SecurityUser(UUID.fromString(doc.getString("uuid")), doc.getString("ip"));

            user.setCode(doc.getString("code"));
        } else {
            user = new SecurityUser(uuid, "none");
        }
        securityUsers.add(user);

        return user;
    }

    public SecurityUser getUser(UUID uuid) {
        Optional<SecurityUser> optionalUser = securityUsers.stream().filter(user -> user.getUuid().toString().equals(uuid.toString())).findFirst();

        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else if(Commons.getInstance().getUserManager().getUserById(uuid).hasRank(Rank.MOD_PLUS)) {
            SecurityUser user = new SecurityUser(uuid, "none");
            securityUsers.add(user);
            return user;
        }
        return null;
    }

    public void resetUser(UUID uuid) {
        SecurityUser user = loadUser(uuid);

        if(user != null) {
            securityUsers.remove(user);
            userCollection.deleteOne(Filters.eq("uuid", user.getUuid().toString()));
        }
    }

    public void saveUser(SecurityUser user) {
        Document document = new Document("uuid", user.getUuid().toString());

        document.put("ip", user.getIp());
        document.put("code", user.getCode());

        userCollection.deleteOne(Filters.eq("uuid", user.getUuid().toString()));
        userCollection.insertOne(document);
    }
}
