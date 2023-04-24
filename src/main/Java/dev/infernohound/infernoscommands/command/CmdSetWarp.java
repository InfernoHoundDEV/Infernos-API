package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class CmdSetWarp extends CommandBase {
    @Override
    public String getCommandName() {
        return "setwarp";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/setwarp <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);
        WorldData data = WorldData.getSave(entityPlayer.worldObj);

        if (args.length == 0) {
            entityPlayer.addChatMessage(new ChatComponentText("Please give a name for the warp").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }
        if (args.length <= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                entityPlayer.addChatMessage(new ChatComponentText("Can not have a warp named list").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            data.getWarps().set(args[0].toLowerCase(), playerData.getCurrentPos());
            entityPlayer.addChatMessage(new ChatComponentText("Created warp " + args[0].toLowerCase()));
            data.markDirty();
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return ics.canCommandSenderUseCommand(3, "setwarp");
    }
}
