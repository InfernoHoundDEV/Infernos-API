package dev.infernohound.infernoscommands.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent event) {
        if (!InfernosConfig.timestamp) {return;}
        IChatComponent message = event.message.createCopy();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        ChatComponentText time = new ChatComponentText(now.format(formatter));
        time.setChatStyle(new ChatStyle().setColor(InfernosConfig.timestampColor));
        ChatComponentText leftSide = new ChatComponentText("[");
        ChatComponentText rightSide = new ChatComponentText("]: ");
        IChatComponent timestamp = leftSide.appendSibling(time).appendSibling(rightSide);
        IChatComponent messageWithTimestamp = timestamp.appendSibling(message);
        event.message = messageWithTimestamp;
    }
}
