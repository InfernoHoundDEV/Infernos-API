package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CmdTPAccept extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpaccept";
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return "/tpaccept <Player>";
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

        if(playerData != null) {
            playerData.removeTpaRequest(entityOther.getUniqueID());

            otherData.setLastPos(otherData.getCurrentPos());
            InfernoTeleporter.teleport(entityOther, playerData.getCurrentPos());
            return;
        }

        if(otherData != null) {
            otherData.removeTpaRequest(entityPlayer.getUniqueID());

            playerData.setLastPos(playerData.getCurrentPos());
            InfernoTeleporter.teleport(entityPlayer, otherData.getCurrentPos());
            return;
        }

        ics.addChatMessage(new ChatComponentText("Found no request from " + entityOther.getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
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
