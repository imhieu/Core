package me.hieu.core.profile;

import com.google.common.reflect.TypeToken;
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
import me.hieu.libraries.util.CC;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
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
        try {
            load();
        } catch (Exception e){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.kickPlayer(CC.translate("&cThere was an error loading your profile. Contact an administrator."));
        }
    }

    public Profile(String name){
        uniqueId = Bukkit.getOfflinePlayer(name).getUniqueId();
        this.name = name;
        tag = null;
        grants = new ArrayList<>();
        punishments = new ArrayList<>();
        permissions = new ArrayList<>();
        staffOption = new ProfileStaffOption();
        try {
            load();
        } catch (Exception e){
            Player player = Bukkit.getPlayer(name);
            if (player == null) return;
            player.kickPlayer(CC.translate("&cThere was an error loading your profile. Contact an administrator."));
        }
    }

    @Override
    public int compareTo(@NotNull Profile o) {
        return o.getActiveGrant().getRank().getWeight() - getActiveGrant().getRank().getWeight();
    }

    public void calibratePermissions(){
        List<String> permissions = new ArrayList<>(this.permissions);
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return;
        PermissionAttachment attachment = player.addAttachment(Core.getInstance());
        Rank rank = getActiveGrant().getRank();
        permissions.addAll(rank.getAllPermissions());
        for (String permission : permissions){
            attachment.setPermission(permission, true);
        }
        player.recalculatePermissions();
    }

    public String getFormattedName(){
        return getActiveGrant().getRank().getColor() + name;
    }

    public String getChatFormattedName(){
        return getActiveGrant().getRank().getPrefix() + name + getActiveGrant().getRank().getSuffix() + (tag == null ? "" : tag.getDisplay());
    }

    public Grant getActiveGrant(){
        Collections.sort(grants);
        for (Grant grant : grants){
            if (!grant.isExpired() && !grant.isPardoned()) return grant;
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
            save();
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
            Type type = new TypeToken<List<String>>(){}.getType();
            permissions = new Gson().fromJson(document.getString("permissions"), type);
        }
        Document staffOptionsDocument = (Document) document.get("staffOptions");
        if (staffOptionsDocument != null){
            staffOption.setStaffChatEnabled(staffOptionsDocument.getBoolean("staffChat"));
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
            document.append("permissions", gson.toJson(permissions));
        }
        if (!staffOption.isStaffChatEnabled()){
            Document staffOptionsDocument = new Document();
            staffOptionsDocument.append("staffChat", false);
            document.append("staffOptions", staffOptionsDocument);
        }
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getProfileHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

}
