package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.Arrays;
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
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        if (args.length == 0) {
            args = new String[]{entityPlayer.getDisplayName()};
        }
        if (args.length <= 1) {
            EntityPlayer entityOther = getPlayer(ics, args[0]);

            if (entityOther == null) {
                ics.addChatMessage(new ChatComponentText("Player is not online!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            PlayerData otherData = PlayerData.get(entityOther);
            BlockDimPos pos = getTopBlock(entityPlayer.worldObj, randomPos(entityOther.dimension));

            otherData.setLastPosToCurrentPos();

            InfernoTeleporter.teleport(entityOther, pos);
            NBTTagCompound tag = new NBTTagCompound();
            otherData.saveNBTData(tag);
            WorldData.setProxyPlayerData(entityOther.getUniqueID(), tag);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return ics.canCommandSenderUseCommand(3, "rtp") ? InfernosConfig.rtp : false;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }

    private BlockDimPos randomPos(int dim) {
        Random random = new Random();
        int x = random.nextInt(Math.min(Math.max(InfernosConfig.randomX, 100), 100000));
        int y = 258;
        int z = random.nextInt(Math.min(Math.max(InfernosConfig.randomZ, 100), 100000));
        return new BlockDimPos( x, y, z, dim );
    }

    private BlockDimPos getTopBlock(World world, BlockDimPos pos) {
        int y = 256;
        while(world.isAirBlock((int) pos.getX(), y, (int) pos.getZ())) {
            --y;
        }
        return new BlockDimPos(pos.getX(), y+1, (int) pos.getZ(), pos.getDim());
    }
}
