package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.event.PlayerListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class CmdTPDeny extends CommandBase {
    @Override
    public String getCommandName() {
        return "tpdeny";
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return "/tpdeny <Player>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if(args.length != 1) {
            ics.addChatMessage(new ChatComponentText("Please input a valid player name!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        EntityPlayer entityPlayer = getCommandSenderAsPlayer(ics);
        EntityPlayer entityOther = getPlayer(ics,args[0]);
        PlayerData playerData = PlayerData.get(entityPlayer);
        PlayerData otherData = PlayerData.get(entityOther);

        if(!playerData.containsTpaRequest(entityOther.getUniqueID()) && !otherData.containsTpaRequest(entityPlayer.getUniqueID())) {
            ics.addChatMessage(new ChatComponentText("Found no request from " + entityOther.getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        playerData.removeTpaRequest(entityOther.getUniqueID());
        otherData.removeTpaRequest(entityPlayer.getUniqueID());

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }
}
