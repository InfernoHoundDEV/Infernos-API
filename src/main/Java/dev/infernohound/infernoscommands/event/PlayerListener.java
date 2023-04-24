package dev.infernohound.infernoscommands.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerListener extends Event {

    private static final Logger logger = LogManager.getLogger(InfernosCommands.MODID);

    @SubscribeEvent
    public void onPlayerCreation(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && PlayerData.get((EntityPlayer) event.entity) == null) {
            PlayerData.register((EntityPlayer) event.entity);
        }
    }


    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = (EntityPlayer) event.player;
        String playerName = player.getDisplayName();
        PlayerData p = PlayerData.get(player);
        WorldData.getPlayerList().put(playerName, p);
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = (EntityPlayer) event.player;
        String playerName = player.getDisplayName();
        PlayerData p = PlayerData.get(player);
        WorldData.getPlayerList().remove(playerName);
    }

    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent event) {
        EntityPlayer player = (EntityPlayer) event.entityPlayer;
        String playerName = event.entityPlayer.getDisplayName();

        if (WorldData.getPlayerList().containsKey(playerName)) {
            PlayerData data = WorldData.getPlayerList().get(playerName);
            BlockDimPos loc = new BlockDimPos(player.posX, player.posY, player.posZ, player.worldObj.provider.dimensionId);
            data.setLastPos(loc);
            logger.log(Level.INFO, "Set player's last loc to {}", loc);
        }
    }

}
