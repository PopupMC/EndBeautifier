package com.popupmc.endbeautifier;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scoreboard.Objective;

import java.util.Random;

public class OnBlockPlace implements Listener {

    // Reward player for adding non-end blocks in the end
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

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

        // Turn dirt blocks to grass, grass paths, mycelium, or podzyl
        if(e.getBlock().getType() == Material.DIRT) {

            // Get random percent between 0-100
            int randomNumber = random.nextInt(100 + 1);

            // 50% chance for it to turn to grass block
            // 25% chance for it to turn into a grass path
            // 15% chance for it to turn into mycelium
            // 10% chance for Podzol
            // 9% chance for it to stay as dirt
            if(randomNumber >= 50)
                e.getBlock().setType(Material.GRASS_BLOCK);
            else if(randomNumber >= 25)
                e.getBlock().setType(Material.GRASS_PATH);
            else if(randomNumber >= 15)
                e.getBlock().setType(Material.MYCELIUM);
            else if(randomNumber >= 10)
                e.getBlock().setType(Material.PODZOL);
            //else
            // Keep it as dirt
        }

        // Get partial objective
        // A partial is a partial stack of 64, not a full stack
        Objective buildPartialObj = Utility.getBuildPartialObj(world, p);
        if(buildPartialObj == null) {
            EndBeautifier.plugin.getLogger().warning("buildPartialObj is null");
            return;
        }

        int buildPartial = buildPartialObj.getScore(p.getName()).getScore();

        // Update it
        buildPartial++;

        // If it's less than a full stack then increment and stop here
        if(buildPartial < stack) {
            buildPartialObj.getScore(p.getName()).setScore(buildPartial);
            return;
        }
        // Otherwise reset value and keep going
        else {
            buildPartial = 0;
            buildPartialObj.getScore(p.getName()).setScore(buildPartial);
        }

        // Get objective
        Objective buildObj = Utility.getBuildObj(world, p);
        if(buildObj == null) {
            EndBeautifier.plugin.getLogger().warning("buildObj is null");
            return;
        }

        int build = buildObj.getScore(p.getName()).getScore();

        // Update it
        build++;
        buildObj.getScore(p.getName()).setScore(build);

        // Get paid objective
        Objective paidBuildObj = Utility.getPaidBuildObj(world, p);
        if(paidBuildObj == null) {
            EndBeautifier.plugin.getLogger().warning("paidBuildObj is null");
            return;
        }

        int paidBuild = paidBuildObj.getScore(p.getName()).getScore();

        // Paid should always be less than or equal to unpaid
        // If greater than then stop here
        if(paidBuild > build)
            return;

        // Get difference between paid and unpaid
        int difference = build - paidBuild;

        // If not enough difference then stop here
        if(difference <= 0)
            return;

        // Get payout
        float payout = (float)(difference) * 0.01f;

        // Pay the player
        EconomyResponse r = EndBeautifier.econ.depositPlayer(p, payout);

        // Stop here if vault error
        if(!r.transactionSuccess()) {
            p.sendMessage(ChatColor.RED + "Vault Error: Unable to pay you ❇" + payout);
            return;
        }

        // Update score
        paidBuildObj.getScore(p.getName()).setScore(build);

        // Announce paid
        if(world.getName().equalsIgnoreCase(EndBeautifier.endWorldName))
            p.sendMessage(ChatColor.GREEN + "You've been paid ❇" + payout + " for building and growing the end with non-end related blocks.");
        else
            p.sendMessage(ChatColor.GREEN + "You've been paid ❇" + payout + " for building and growing the nether with non-nether related blocks.");
    }

    // For code cleanliness and readability
    public final static int stack = 64;
    public static Random random = new Random();
}
