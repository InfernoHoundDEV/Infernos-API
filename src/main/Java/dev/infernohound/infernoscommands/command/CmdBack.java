package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class CmdBack extends CommandBase {
    @Override
    public String getCommandName() {
        return "back";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/back";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayerMP entityPlayer = (EntityPlayerMP) getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);

        if (playerData.getLastPos() == null) {
            ics.addChatMessage(new ChatComponentText("You can only back once!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        InfernoTeleporter.teleport(entityPlayer, playerData.getLastPos());
        playerData.setLastPos(null);
        NBTTagCompound tag = new NBTTagCompound();
        playerData.saveNBTData(tag);
        WorldData.setProxyPlayerData(entityPlayer.getUniqueID(), tag);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return true;
    }

}
