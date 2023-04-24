package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.event.PlayerListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class CmdTPA extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return "/tpa <Player>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if(args.length != 1) {
            ics.addChatMessage(new ChatComponentText("Please input a valid player name!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        EntityPlayer entityPlayer = getCommandSenderAsPlayer(ics);
        EntityPlayer entityOther = getPlayer(ics, args[0]);

        if (entityOther == null) {
            ics.addChatMessage(new ChatComponentText("Player is not online!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        PlayerData otherData = PlayerData.get(entityOther);

        if (otherData.containsTpaRequest(entityPlayer.getUniqueID())) {
            ics.addChatMessage(new ChatComponentText("You have already sent a request to " + entityOther.getDisplayName() + "!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }


        otherData.addTpaRequest(entityPlayer.getUniqueID());

        ChatComponentText confirmation =  new ChatComponentText("Sent a request to " + entityOther.getDisplayName());
        confirmation.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
        entityPlayer.addChatComponentMessage(confirmation);


        ChatComponentText request = new ChatComponentText(entityPlayer.getDisplayName() + " requests to teleport to you");
        request.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
        entityOther.addChatComponentMessage(request);

        ChatComponentText accept = new ChatComponentText("Accept");
        accept.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN));
        accept.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + entityPlayer.getDisplayName()));

        ChatComponentText tab = new ChatComponentText("   ");

        ChatComponentText deny = new ChatComponentText("Deny");
        deny.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
        deny.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + entityPlayer.getDisplayName()));

        entityOther.addChatComponentMessage(accept.appendSibling(tab).appendSibling(deny));

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