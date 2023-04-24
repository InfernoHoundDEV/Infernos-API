package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.data.BlockDimPos;
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
import java.util.Random;

public class CmdRandomTP extends CommandBase {

    @Override
    public String getCommandName() {
        return "rtp";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/rtp <Player>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayerMP player = getCommandSenderAsPlayer(ics);
        if (args.length == 0) {
            args = new String[]{player.getDisplayName()};
        }
        if (args.length <= 1) {
            EntityPlayerMP other = getPlayer(ics, args[0]);

            if (!WorldData.getPlayerList().containsKey(other.getDisplayName())) {
                ics.addChatMessage(new ChatComponentText("Player is not online!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            PlayerData o = WorldData.getPlayerList().get(other.getDisplayName());
            BlockDimPos pos = randomPos(other.dimension);

            o.setLastPos(o.getCurrentPos());
            InfernoTeleporter.teleport(other,pos);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return ics.canCommandSenderUseCommand(3, "rtp");
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }

    private BlockDimPos randomPos(int dim) {
        Random random = new Random();
        int x = random.nextInt(Math.min(Math.max(InfernosCommands.rPosX, 100), 100000));
        int y = random.nextInt(80) + 40;
        int z = random.nextInt(Math.min(Math.max(InfernosCommands.rPosZ, 100), 100000));
        return new BlockDimPos( x, y, z, dim );
    }
}
