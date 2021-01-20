package com.popupmc.endbeautifier;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Utility {
    public static boolean isRelativeEvent(World world, Block block) {
        // Is this the end?
        boolean isEnd = world.getName().equalsIgnoreCase("main_the_end");

        if(!isEnd)
            return false;

        // What block?
        Material material = block.getType();

        // Stop here if the block is related to or found in the end normally
        if(material == Material.DRAGON_EGG ||
                material == Material.END_GATEWAY ||
                material == Material.END_PORTAL ||
                material == Material.END_ROD ||
                material == Material.END_STONE ||
                material == Material.END_STONE_BRICKS ||
                material == Material.PURPUR_BLOCK ||
                material == Material.BEDROCK ||
                material == Material.OBSIDIAN ||
                material == Material.CHORUS_PLANT ||
                material == Material.WALL_TORCH ||
                material == Material.FIRE ||
                material == Material.CHEST ||
                material == Material.ENDER_CHEST ||
                material == Material.PURPLE_STAINED_GLASS ||
                material == Material.BREWING_STAND ||
                material == Material.ITEM_FRAME ||
                material == Material.MAGENTA_WALL_BANNER ||
                material == Material.PURPUR_PILLAR ||
                material == Material.PURPUR_SLAB ||
                material == Material.PURPUR_STAIRS ||
                material == Material.DRAGON_HEAD)
            return false;

        return true;
    }
}
