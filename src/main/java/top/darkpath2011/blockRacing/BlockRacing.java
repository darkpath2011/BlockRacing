package top.darkpath2011.blockRacing;

import lombok.Getter;
import top.darkpath2011.blockRacing.command.GameCommand;
import top.darkpath2011.blockRacing.command.TeamUserManagerCommand;
import top.darkpath2011.blockRacing.command.TeleportCommand;
import top.darkpath2011.blockRacing.listener.ChestListener;
import top.darkpath2011.blockRacing.listener.GameListener;
import top.darkpath2011.blockRacing.listener.PlayerListener;
import top.darkpath2011.blockRacing.manager.ChestManager;
import top.darkpath2011.blockRacing.room.GameRoom;
import top.darkpath2011.blockRacing.room.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class BlockRacing extends JavaPlugin {
    public static BlockRacing plugin;
    public static GameRoom room;
    public static ChestManager chestManager;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        plugin = this;
        getLogger().info("BlockRacing plugin has been loaded.");
    }

    @Override
    public void onEnable() {
        if (!validateConfig()) {
            getLogger().severe("配置检查失败，插件将被禁用。请修复 config.yml 后重试。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        room = new GameRoom(GameStatus.WAITING);
        chestManager = new ChestManager();
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        CommandMap commandMap = getCommandMap();
        if (commandMap == null) {
            getLogger().severe("无法获取命令注册器 (CommandMap)，插件将被禁用。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        commandMap.register("", new GameCommand("blockracing"));
        commandMap.register("", new TeleportCommand());
        commandMap.register("", new TeamUserManagerCommand());
        getLogger().info("BlockRacing plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        if (room != null) {
            room.shutdown();
            room = null;
        }
        chestManager = null;
        getLogger().info("BlockRacing plugin has been disabled.");
    }

    private boolean validateConfig() {
        boolean valid = true;
        if (getConfig().getConfigurationSection("teams") == null) {
            getLogger().severe("缺少 teams 配置，请在 config.yml 中配置队伍信息。");
            valid = false;
        }
        if (getConfig().getStringList("blocks").isEmpty()) {
            getLogger().severe("缺少 blocks 配置，请在 config.yml 中配置方块目标列表。");
            valid = false;
        }
        if (getConfig().getInt("winner_socer", 0) <= 0) {
            getLogger().warning("winner_socer 配置无效，建议设置为大于 0 的整数。");
        }
        return valid;
    }

    public static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            if (plugin != null) {
                plugin.getLogger().severe("无法获取 CommandMap: " + e.getMessage());
            }
        }
        return commandMap;
    }
}
