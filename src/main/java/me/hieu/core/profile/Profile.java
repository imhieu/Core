package me.hieu.core.profile;

import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.grant.GrantDeserializer;
import me.hieu.core.profile.option.ProfileStaffOption;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.PunishmentDeserializer;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.rank.Rank;
import me.hieu.core.tag.Tag;
import me.hieu.core.util.ConsoleUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

@Getter @Setter
public class Profile implements Comparable<Profile> {

    private UUID uniqueId;
    private String name;
    private Tag tag;
    private List<Grant> grants;
    private List<Punishment> punishments;
    private List<String> permissions;

    private ProfileStaffOption staffOption;

    public Profile(UUID uuid){
        this.uniqueId = uuid;
        name = Bukkit.getOfflinePlayer(uuid).getName();
        tag = null;
        grants = new ArrayList<>();
        punishments = new ArrayList<>();
        permissions = new ArrayList<>();
        staffOption = new ProfileStaffOption();
        load();
    }

    public Profile(String name){
        uniqueId = Bukkit.getOfflinePlayer(name).getUniqueId();
        this.name = name;
        tag = null;
        grants = new ArrayList<>();
        punishments = new ArrayList<>();
        permissions = new ArrayList<>();
        staffOption = new ProfileStaffOption();
        load();
    }

    @Override
    public int compareTo(@NotNull Profile o) {
        return o.getActiveRank().getWeight() - getActiveRank().getWeight();
    }

    public void calibratePermissions(){
        List<String> permissions = new ArrayList<>(this.permissions);
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return;
        PermissionAttachment attachment = player.addAttachment(Core.getInstance());
        Rank rank = getActiveRank();
        permissions.addAll(rank.getAllPermissions());
        for (String permission : permissions){
            attachment.setPermission(permission, true);
        }
        player.recalculatePermissions();
    }

    public String getFormattedName(){
        return getActiveRank().getColor() + name;
    }

    public String getChatFormattedName(){
        return getActiveRank().getPrefix() + name + getActiveRank().getSuffix() + (tag == null ? "" : tag.getDisplay());
    }

    public Rank getActiveRank(){
        Collections.sort(grants);
        for (Grant grant : grants){
            if (!grant.isExpired() && !grant.isPardoned()) return grant.getGrantRank();
        }
        return null;
    }

    public Punishment getActivePunishment(PunishmentType type){
        for (Punishment punishment : punishments){
            if (!punishment.isExpired() && !punishment.isPardoned() && punishment.getType() == type) return punishment;
        }
        return null;
    }

    private void load(){
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Document document = Core.getInstance().getProfileHandler().getCollection().find(filter).first();
        if (document == null){
            Rank rank = Core.getInstance().getRankHandler().getRankByName("Default");
            grants.add(new Grant(UUID.randomUUID(), rank.getUniqueId(), ConsoleUtil.CONSOLE_UUID, System.currentTimeMillis(), Integer.MAX_VALUE, "Default Grant"));
            return;
        }
        name = document.getString("name");
        if (document.getString("tagUniqueId") != null){
            tag = Core.getInstance().getTagHandler().getTagByUniqueId(UUID.fromString(document.getString("tagUniqueId")));
        }
        if (document.getString("grants") != null){
            grants = GrantDeserializer.convert(document.getString("grants"));
        }
        if (document.getString("punishments") != null){
            punishments = PunishmentDeserializer.convert(document.getString("punishments"));
        }
        if (document.getString("permissions") != null){
            String[] splitPermissions = document.getString("permissions").split(":");
            permissions.addAll(Arrays.asList(splitPermissions));
        }
    }

    public void save(){
        Document document = new Document();
        document.append("uniqueId", uniqueId.toString());
        document.append("name", name);
        if (tag != null){
            document.append("tagUniqueId", tag.getUniqueId().toString());
        }
        Gson gson = new Gson();
        if (!grants.isEmpty()){
            document.append("grants", gson.toJson(grants));
        }
        if (!punishments.isEmpty()){
            document.append("punishments", gson.toJson(punishments));
        }
        if (!permissions.isEmpty()){
            document.append("permissions", convert(permissions));
        }
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getProfileHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    private String convert(List<String> permissions){
        StringBuilder builder = new StringBuilder();
        for (String permission : permissions){
            builder.append(permission).append(":");
        }
        return builder.toString();
    }

}
