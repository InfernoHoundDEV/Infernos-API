package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CmdSetHome extends CommandBase {
    @Override
    public String getCommandName() {
        return "sethome";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/sethome <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);
        if (args.length == 0) {
            args = new String[]{"home"};
        }
        if (args.length <= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                entityPlayer.addChatMessage(new ChatComponentText("Can not have a home named list").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }
            int maxHomes = InfernosCommands.maxHomes;
            if(maxHomes <= 0 || (playerData.getHomes().size() >= maxHomes && !playerData.getHomes().keySet().contains(args[0].toLowerCase()))) {
                entityPlayer.addChatMessage(new ChatComponentText("Already have the maximum number of homes!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            playerData.getHomes().set(args[0].toLowerCase(), playerData.getCurrentPos());
            entityPlayer.addChatMessage(new ChatComponentText("Created home " + args[0].toLowerCase()));
            NBTTagCompound tag = new NBTTagCompound();
            playerData.saveNBTData(tag);
            WorldData.setProxyPlayerData(entityPlayer.getUniqueID(), tag);
        }
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
