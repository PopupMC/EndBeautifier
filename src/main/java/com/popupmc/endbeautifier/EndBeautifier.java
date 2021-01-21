package com.popupmc.endbeautifier;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EndBeautifier extends JavaPlugin {

    @Override
    public void onEnable() {
        plugin = this;

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Add listener
        Bukkit.getPluginManager().registerEvents(new OnBlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockBreakEvent(), this);

        getLogger().info("EndBeautifier is enabled.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("EndBeautifier is disabled");
    }

    static JavaPlugin plugin;
    public static Economy econ = null;

    public static final String endWorldName = "main_the_end";
    public static final String netherWorldName = "main_nether";
}
