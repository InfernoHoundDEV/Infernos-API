package dev.infernohound.infernoscommands.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

import java.util.HashMap;

public class WorldData extends WorldSavedData {

    public final static String IDENTIFIER = "InfernoWorldData";
    private static HashMap<String, PlayerData> playerList = new HashMap<>();
    private static Warp warps = new Warp();

    public WorldData(String identifier) {
        super(identifier);
    }

    public static HashMap<String, PlayerData> getPlayerList() {
        return playerList;
    }
    public Warp getWarps() { return warps; }

    public static WorldData getSave(World world) {
        MapStorage storage = world.perWorldStorage;
        WorldData result = (WorldData) storage.loadData(WorldData.class, IDENTIFIER);
        if (result == null) {
            result = new WorldData(IDENTIFIER);
            storage.setData(IDENTIFIER, result);
        }
        return result;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        warps.readFromNBT(tag,"Warps");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        warps.writeToNBT(tag, "Warps");
    }
}
