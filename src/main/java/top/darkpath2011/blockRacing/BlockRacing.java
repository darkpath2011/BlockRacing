package top.darkpath2011.blockRacing;

import lombok.Getter;
import top.darkpath2011.blockRacing.command.GameCommand;
import top.darkpath2011.blockRacing.command.TeamUserManagerCommand;
import top.darkpath2011.blockRacing.command.TeleportCommand;
import top.darkpath2011.blockRacing.listener.ChestListener;
import top.darkpath2011.blockRacing.listener.GameListener;
import top.darkpath2011.blockRacing.listener.PlayerListener;
import top.darkpath2011.blockRacing.manager.ChestManager;
import top.darkpath2011.blockRacing.object.MenuManager; // 确保导入 MenuManager 类
import top.darkpath2011.blockRacing.room.GameRoom;
import top.darkpath2011.blockRacing.room.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class BlockRacing extends JavaPlugin {
    @Getter
    public static BlockRacing plugin;
    @Getter
    public static GameRoom room;
    @Getter
    public static ChestManager chestManager;
    @Getter
    private MenuManager menuManager;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        plugin = this;
        getLogger().info("BlockRacing plugin has been loaded.");
    }

    @Override
    public void onEnable() {
        room = new GameRoom(GameStatus.WAITING);
        chestManager = new ChestManager();
        menuManager = new MenuManager();
        getServer().getPluginManager().registerEvents(menuManager, this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        getCommandMap().register("", new GameCommand("blockracing"));
        getCommandMap().register("", new TeleportCommand());
        getCommandMap().register("", new TeamUserManagerCommand());
        getLogger().info("BlockRacing plugin has been enabled.");
    }

    @Override
    public void onDisable() {
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
