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
        if(!Utility.isRelativeEndEvent(world, block) &&
            !Utility.isRelativeNetherEvent(world, block))
            return;

        // Get Player
        Player p = e.getPlayer();

        // Get partial objective
        // A partial is a partial stack of 64, not a full stack
        Objective buildPartialObj = Utility.getBuildPartialObj(world, p);
        if(buildPartialObj == null) {
            EndBeautifier.plugin.getLogger().warning("buildPartialObj is null");
            return;
        }

        int buildPartial = buildPartialObj.getScore(p.getName()).getScore();

        // Update it
        buildPartial--;

        // If it's not below 0, then update and stop here
        if(buildPartial >= 0) {
            buildPartialObj.getScore(p.getName()).setScore(buildPartial);
            return;
        }
        // Otherwise reset to 1 less than a full stack and keep going
        else {
            buildPartial = OnBlockPlace.stack - 1;
            buildPartialObj.getScore(p.getName()).setScore(buildPartial);
        }

        // Get build objective
        Objective buildObj = Utility.getBuildObj(world, p);
        if(buildObj == null) {
            EndBeautifier.plugin.getLogger().warning("buildObj is null");
            return;
        }

        int build = buildObj.getScore(p.getName()).getScore();

        // Update it
        build--;
        buildObj.getScore(p.getName()).setScore(build);
    }
}
