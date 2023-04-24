package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.event.PlayerListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
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

        EntityPlayerMP player = getCommandSenderAsPlayer(ics);
        EntityPlayerMP other = getPlayer(ics,args[0]);
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        PlayerData o = WorldData.getPlayerList().get(other.getDisplayName());

        if(!p.getTpaRequests().contains(other.getDisplayName()) && !o.getTpaRequests().contains(player.getDisplayName())) {
            ics.addChatMessage(new ChatComponentText("Found no request from " + other.getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        p.getTpaRequests().remove(other.getDisplayName());
        o.getTpaRequests().remove(player.getDisplayName());

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
