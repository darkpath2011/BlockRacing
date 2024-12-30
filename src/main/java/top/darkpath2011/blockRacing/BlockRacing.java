package top.darkpath2011.blockRacing;

import top.darkpath2011.blockRacing.command.GameCommand;
import top.darkpath2011.blockRacing.command.TeamCheatCommand;
import top.darkpath2011.blockRacing.command.TeleportCommand;
import top.darkpath2011.blockRacing.listener.GameListener;
import top.darkpath2011.blockRacing.listener.PlayerListener;
import top.darkpath2011.blockRacing.room.GameRoom;
import top.darkpath2011.blockRacing.room.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class BlockRacing extends JavaPlugin {
    public static BlockRacing plugin;
    public static GameRoom room;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        plugin = this;
        getLogger().info("BlockRacing plugin has been loaded.");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        room = new GameRoom(GameStatus.WAITING);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommandMap().register("", new GameCommand("blockracing"));
        getCommandMap().register("", new TeleportCommand());
        getCommandMap().register("", new TeamCheatCommand());
        getLogger().info("BlockRacing plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("BlockRacing plugin has been disabled.");
    }

    public static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commandMap;
    }
}
