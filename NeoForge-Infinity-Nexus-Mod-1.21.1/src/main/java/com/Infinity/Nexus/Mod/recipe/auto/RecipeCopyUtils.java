package com.Infinity.Nexus.Mod.recipe.auto;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RecipeCopyUtils {
    static Item wand = Items.STICK;
    static Item customWand = Items.STICK;

    public static boolean getWand(Item handItem) {
        return handItem == wand;
    }

    public static String getItemNBT(ItemStack item) {
        //TODO TAGS
        //CompoundTag offhandItemTag = item.getTag();
        CompoundTag offhandItemTag = null;
        String nbtString = "";
        if (offhandItemTag != null) {
            nbtString = offhandItemTag.toString().replace("\"", "\\\"");
            //Gson gson = new Gson();
            //nbtString = gson.toJson(nbtString);
            //System.out.println(nbtString);
        }
       return ",\n    \"nbt\":"+ (nbtString.isEmpty() ? "null" : "\"" + nbtString + "\"");
    }

    public static String getOutputItem(ItemStack item) {
        String offhandItemId = item.getItem().builtInRegistryHolder().key().location().toString();
        int offhandItemCount = item.getCount();
        String offhandItemNBT = RecipeCopyUtils.getItemNBT(item);

        return "  \"output\": {\n"+
               "    \"count\": " + offhandItemCount + ",\n"+
               "    \"item\": \"" + offhandItemId + "\""+
               (offhandItemNBT.contains("null") ? "" :offhandItemNBT) +
               "\n  }\n";
    }
}
