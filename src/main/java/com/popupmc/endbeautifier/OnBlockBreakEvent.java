package com.popupmc.endbeautifier;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.Objective;

public class OnBlockBreakEvent implements Listener {

    // Punish player for removing non-end blocks in the end
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // Get block
        Block block = e.getBlock();

        // Get world
        World world = block.getWorld();

        // Stop here if this event isn't relative to what we're looking for
        if(!Utility.isRelativeEvent(world, block))
            return;

        // Get Player
        Player p = e.getPlayer();

        // Get end objective
        Objective endBuildObj = p.getScoreboard().getObjective("endBuild");
        if(endBuildObj == null) {
            EndBeautifier.plugin.getLogger().warning("Objective endBuild is null");
            return;
        }

        int endBuild = endBuildObj.getScore(p.getName()).getScore();

        // Update it
        endBuild--;
        endBuildObj.getScore(p.getName()).setScore(endBuild);
    }
}
