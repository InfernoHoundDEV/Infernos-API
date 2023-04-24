package dev.infernohound.infernoscommands.data;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Warp {

    private final HashMap<String, BlockDimPos> warps;

    public Warp() {
        this.warps = new HashMap<>();
    }


    public void readFromNBT(NBTTagCompound tag, String str) {
        warps.clear();

        NBTTagCompound properties = (NBTTagCompound) tag.getTag(str);

        if(properties != null && !properties.hasNoTags())
        {
            for(String str1 : getMapKeys(properties))
            {
                warps.put(str1, new BlockDimPos(properties.getIntArray(str1)));
            }
        }
    }

    private String[] getMapKeys(NBTTagCompound tag)
    {
        if(tag == null || tag.hasNoTags()) return new String[0];

        Set<String> strSet = tag.func_150296_c();

        String[] strArray = new String[strSet.size()];

        int index = 0;
        for (String str : strSet) {
            strArray[index++] = str;
        }
        return strArray;
    }

    public void writeToNBT(NBTTagCompound tag, String s)
    {
        NBTTagCompound properties = new NBTTagCompound();
        for(Map.Entry<String, BlockDimPos> e : warps.entrySet())
            properties.setIntArray(e.getKey(), e.getValue().toIntArray());
        tag.setTag(s, properties);
    }

    public Set<String> keySet()
    { return warps.keySet(); }

    public String[] keyArray()
    {
        int size = warps.keySet().size()+1;
        String[] strArray = new String[size];

        int index = 0;
        for (String str : warps.keySet()) {
            strArray[index++] = str;
        }
        strArray[size-1] = "list";
        return strArray;
    }

    public BlockDimPos get(String s)
    { return warps.get(s); }

    public boolean set(String s, BlockDimPos pos)
    {
        if(pos == null) return warps.remove(s) != null;
        return warps.put(s, pos.copy()) == null;
    }
    public int size()
    { return warps.size(); }
}
