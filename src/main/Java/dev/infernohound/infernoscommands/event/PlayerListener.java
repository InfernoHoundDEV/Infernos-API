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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerListener extends Event {

    private static final Logger logger = LogManager.getLogger(InfernosCommands.MODID);

    @SubscribeEvent
    public void onPlayerCreation(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && PlayerData.get((EntityPlayer) event.entity) == null) {
            EntityPlayer player = (EntityPlayer) event.entity;
            PlayerData.register(player);
        }
    }


    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.entity;
            NBTTagCompound playerDataNBT = WorldData.getProxyPlayerData(entityPlayer.getUniqueID());
            if (playerDataNBT != null) {
                PlayerData.get(entityPlayer).loadNBTData(playerDataNBT);
            }
        }
    }


    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.entity;
            PlayerData playerData = PlayerData.get(entityPlayer);
            playerData.setLastPos(playerData.getCurrentPos());
            NBTTagCompound playerDataNBT = new NBTTagCompound();
            playerData.saveNBTData(playerDataNBT);
            WorldData.setProxyPlayerData(entityPlayer.getUniqueID(),playerDataNBT);
        }
    }
}
