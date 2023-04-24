package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CmdTPAHere extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpahere";
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return "/tpahere <Player>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if(args.length != 1) {
            ics.addChatMessage(new ChatComponentText("Please input a valid player name!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        EntityPlayer entityPlayer = getCommandSenderAsPlayer(ics);
        EntityPlayer entityOther = getPlayer(ics, args[0]);

        if (entityOther != null) {
            ics.addChatMessage(new ChatComponentText("Player is not online!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        PlayerData playerData = PlayerData.get(entityPlayer);

        if (playerData.containsTpaRequest(entityOther.getUniqueID())) {
            ics.addChatMessage(new ChatComponentText("You have already sent a request to " + entityOther.getDisplayName() + "!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }


        playerData.addTpaRequest(entityOther.getUniqueID());

        ChatComponentText request = new ChatComponentText(entityPlayer.getDisplayName() + " requests you to teleport to them");
        request.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
        entityOther.addChatMessage(request);

        ChatComponentText accept = new ChatComponentText("Accept");
        accept.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN));
        accept.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + entityPlayer.getDisplayName()));

        ChatComponentText tab = new ChatComponentText("   ");

        ChatComponentText deny = new ChatComponentText("Deny");
        deny.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
        deny.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + entityPlayer.getDisplayName()));

        entityOther.addChatMessage(accept.appendSibling(tab).appendSibling(deny));

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
