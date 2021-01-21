package com.popupmc.endbeautifier;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

public class Utility {

    // I use or operators where possible because of "short-circuit programming pattern"
    // a popular programming strategy.
    // Or operators are the only operators to end early and not finish evaluating if a single condition is
    // not met, great for speed in a gaming server

    // IntelliJ wants me to convert these to and operators meaning the entire code block must be evaluated for
    // every block place or break even if a condition is false which is unesesary and saves 2 lines of code making it
    // pointless and a waste of cpu & tick time

    public static boolean isRelativeEndEvent(World world, Block block) {
        // Is this the end?
        boolean isEnd = world.getName().equalsIgnoreCase(EndBeautifier.endWorldName);

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
                material == Material.DRAGON_HEAD ||
                material == Material.IRON_BARS)
            return false;

        return true;
    }

    public static boolean isRelativeNetherEvent(World world, Block block) {
        // Is this the end?
        boolean isNether = world.getName().equalsIgnoreCase(EndBeautifier.netherWorldName);

        if(!isNether)
            return false;

        // What block?
        Material material = block.getType();

        // Stop here if the block is related to or found in the nether normally
        if(material == Material.BEDROCK ||
                material == Material.LAVA ||
                material == Material.GRAVEL ||
                material == Material.BROWN_MUSHROOM ||
                material == Material.RED_MUSHROOM ||
                material == Material.FIRE ||
                material == Material.NETHERRACK ||
                material == Material.SOUL_SAND ||
                material == Material.GLOWSTONE ||
                material == Material.NETHER_QUARTZ_ORE ||
                material == Material.MAGMA_BLOCK ||
                material == Material.BONE_BLOCK ||
                material == Material.NETHER_WART_BLOCK ||
                material == Material.OBSIDIAN ||
                material == Material.NETHER_PORTAL ||
                material == Material.SPAWNER ||
                material == Material.CHEST ||
                material == Material.NETHER_BRICKS ||
                material == Material.NETHER_BRICK_FENCE ||
                material == Material.NETHER_BRICK_STAIRS ||
                material == Material.NETHER_WART ||
                material == Material.GOLD_BLOCK ||
                material == Material.LANTERN ||
                material == Material.QUARTZ_BLOCK ||
                material == Material.SMOOTH_QUARTZ ||
                material == Material.SMOOTH_QUARTZ_SLAB ||
                material == Material.NETHER_BRICK_SLAB ||
                material == Material.NETHER_BRICK_WALL ||
                material == Material.RED_NETHER_BRICK_WALL ||
                material == Material.RED_NETHER_BRICKS ||
                material == Material.RED_NETHER_BRICK_STAIRS ||
                material == Material.RED_NETHER_BRICK_SLAB ||
                material == Material.NETHER_STAR ||
                material == Material.NETHER_BRICK ||
                material == Material.BROWN_MUSHROOM_BLOCK ||
                material == Material.RED_MUSHROOM_BLOCK ||
                material == Material.MUSHROOM_STEM ||
                material == Material.QUARTZ_SLAB ||
                material == Material.CHISELED_QUARTZ_BLOCK ||
                material == Material.QUARTZ_PILLAR ||
                material == Material.QUARTZ_STAIRS ||
                material == Material.SMOOTH_QUARTZ_STAIRS ||
                material == Material.QUARTZ)
            return false;

        return true;
    }

    // Gets objective for a partial build stack depending on world
    public static Objective getBuildPartialObj(World world, Player p) {
        if(world.getName().equalsIgnoreCase(EndBeautifier.endWorldName)) {
            Objective obj = p.getScoreboard().getObjective("endBuildPartial");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective endBuildPartial is null");
                return null;
            }

            return obj;
        }
        else if(world.getName().equalsIgnoreCase(EndBeautifier.netherWorldName)) {
            Objective obj = p.getScoreboard().getObjective("nethBuildPartial");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective nethBuildPartial is null");
                return null;
            }

            return obj;
        }

        return null;
    }

    // Gets objective for a full build stack depending on world
    public static Objective getBuildObj(World world, Player p) {
        if(world.getName().equalsIgnoreCase(EndBeautifier.endWorldName)) {
            Objective obj = p.getScoreboard().getObjective("endBuild");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective endBuild is null");
                return null;
            }

            return obj;
        }
        else if(world.getName().equalsIgnoreCase(EndBeautifier.netherWorldName)) {
            Objective obj = p.getScoreboard().getObjective("nethBuild");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective nethBuild is null");
                return null;
            }

            return obj;
        }

        return null;
    }

    // Gets objective for a paid-up full build stack depending on world
    public static Objective getPaidBuildObj(World world, Player p) {
        if(world.getName().equalsIgnoreCase(EndBeautifier.endWorldName)) {
            Objective obj = p.getScoreboard().getObjective("paidEndBuild");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective paidEndBuild is null");
                return null;
            }

            return obj;
        }
        else if(world.getName().equalsIgnoreCase(EndBeautifier.netherWorldName)) {
            Objective obj = p.getScoreboard().getObjective("paidNethBuild");
            if(obj == null) {
                EndBeautifier.plugin.getLogger().warning("Objective paidNethBuild is null");
                return null;
            }

            return obj;
        }

        return null;
    }
}
