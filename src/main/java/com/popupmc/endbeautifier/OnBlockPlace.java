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
        if(!Utility.isRelativeEvent(world, block))
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

        // Get end objective
        Objective endBuildObj = p.getScoreboard().getObjective("endBuild");
        if(endBuildObj == null) {
            EndBeautifier.plugin.getLogger().warning("Objective endBuild is null");
            return;
        }

        int endBuild = endBuildObj.getScore(p.getName()).getScore();

        // Update it
        endBuild++;
        endBuildObj.getScore(p.getName()).setScore(endBuild);

        // Get paid end objective
        Objective paidEndBuildObj = p.getScoreboard().getObjective("paidEndBuild");
        if(paidEndBuildObj == null) {
            EndBeautifier.plugin.getLogger().warning("Objective paidEndBuild is null");
            return;
        }

        int paidEndBuild = paidEndBuildObj.getScore(p.getName()).getScore();

        // Paid should always be less than or equal to unpaid
        // If greater than then stop here
        if(paidEndBuild > endBuild)
            return;

        // Get difference between paid and unpaid
        int difference = endBuild - paidEndBuild;

        // If not enough difference then stop here
        if(difference < 0 || (difference % stack) != 0)
            return;

        // Get payout
        float payout = (float)(difference / stack) * 0.01f;

        // Pay the player
        EconomyResponse r = EndBeautifier.econ.depositPlayer(p, payout);

        // Stop here if vault error
        if(!r.transactionSuccess()) {
            p.sendMessage(ChatColor.RED + "Vault Error: Unable to pay you ❇" + payout);
            return;
        }

        // Update score
        paidEndBuildObj.getScore(p.getName()).setScore(endBuild);

        // Announce paid
        p.sendMessage(ChatColor.GREEN + "You've been paid ❇" + payout + " for building and growing the end with non-end related blocks.");
    }

    // For code cleanliness and readability
    public final static int stack = 64;
    public static Random random = new Random();
}
