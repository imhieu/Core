package me.hieu.core.procedure;

import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.grant.packet.GrantPardonPacket;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.packet.PunishmentPardonPacket;
import me.hieu.core.rank.Rank;
import me.hieu.libraries.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 14/10/2024
 */

public class ProcedureListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        Core plugin = Core.getInstance();
        ProcedureHandler procedureHandler = plugin.getProcedureHandler();
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        String message = event.getMessage();
        Procedure procedure = procedureHandler.getProcedureByUniqueId(uniqueId);
        if (procedure == null) return;
        event.setCancelled(true);
        if (message.equalsIgnoreCase("cancel")){
            player.sendMessage(CC.translate("&cGrant pardon procedure cancelled."));
            procedureHandler.getProcedureMap().remove(player.getUniqueId());
            return;
        }
        if (procedure.getProcedureType() == ProcedureType.GRANT_PARDON){
            Grant grant = (Grant) procedure.getObject();
            Profile target = plugin.getProfileHandler().getProfileByUniqueId(procedure.getTarget());
            Rank rank = plugin.getRankHandler().getRankByUniqueId(grant.getRankUniqueId());
            player.sendMessage(CC.translate("&aPardoned the grant of &r" + target.getFormattedName() + " &afor &r" + rank.getFormattedRank() + " &arank."));
            GrantPardonPacket packet = new GrantPardonPacket(procedure.getTarget(), procedure.getUniqueId(), grant.getRankUniqueId(), uniqueId, System.currentTimeMillis(), message);
            plugin.getRedisHandler().sendPacket(packet);
            procedureHandler.getProcedureMap().remove(player.getUniqueId());
        }
        if (procedure.getProcedureType() == ProcedureType.HISTORY_PARDON){
            Punishment punishment = (Punishment) procedure.getObject();
            Profile target = plugin.getProfileHandler().getProfileByUniqueId(procedure.getTarget());
            PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUniqueId(), punishment.getUniqueId(), false, uniqueId, System.currentTimeMillis(), message);
            plugin.getRedisHandler().sendPacket(packet);
            procedureHandler.getProcedureMap().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Core.getInstance().getProcedureHandler().getProcedureMap().remove(uuid);
    }

}
