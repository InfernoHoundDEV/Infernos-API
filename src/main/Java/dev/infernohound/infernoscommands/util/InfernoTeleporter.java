package dev.infernohound.infernoscommands.util;

import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InfernoTeleporter extends Teleporter {

    private static final Logger logger = LogManager.getLogger(InfernosCommands.MODID);

    public static void teleport(EntityPlayer player, BlockDimPos pos) {
        int dim = player.worldObj.provider.dimensionId;
        if (pos.getDim() != dim) {
            ((EntityPlayerMP) player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, pos.getDim(), new InfernoTeleporter(((EntityPlayerMP) player).mcServer.worldServerForDimension(pos.getDim())));
            ((EntityPlayerMP) player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, pos.getDim(), new InfernoTeleporter(((EntityPlayerMP) player).mcServer.worldServerForDimension(pos.getDim())));
        }
        player.setPositionAndUpdate(pos.getX(), pos.getY() + 1.0D, pos.getZ());
        player.playSound("random.fizz", 1F, 1F);

        logger.log(Level.INFO, "Teleported to {}", pos);
    }

    final WorldServer world;

    public InfernoTeleporter(WorldServer world) {
        super(world);
        this.world = world;
    }

    @Override
    public void placeInPortal(Entity player, double x, double y, double z, float rot) {
        ((EntityPlayer) player).setPositionAndUpdate(x, y, z);
    }

    @Override
    public void removeStalePortalLocations(long l) {}

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

}
