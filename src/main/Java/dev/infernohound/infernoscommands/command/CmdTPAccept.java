package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CmdTPAccept extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

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

        EntityPlayerMP player = getCommandSenderAsPlayer(ics);
        EntityPlayerMP other = getPlayer(ics,args[0]);
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        PlayerData o = WorldData.getPlayerList().get(other.getDisplayName());

        if(p != null && p.getTpaRequests().contains(other.getDisplayName())) {
            p.getTpaRequests().remove(other.getDisplayName());

            o.setLastPos(o.getCurrentPos());
            InfernoTeleporter.teleport(other, p.getCurrentPos());
            return;
        }

        if(o != null && o.getTpaRequests().contains(player.getDisplayName())) {
            o.getTpaRequests().remove(player.getDisplayName());

            p.setLastPos(o.getCurrentPos());
            InfernoTeleporter.teleport(player, o.getCurrentPos());
            return;
        }

        ics.addChatMessage(new ChatComponentText("Found no request from " + other.getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }
}
