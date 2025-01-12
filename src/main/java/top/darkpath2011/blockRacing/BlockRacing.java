package top.darkpath2011.blockRacing;

import lombok.Getter;
import top.darkpath2011.blockRacing.command.GameCommand;
import top.darkpath2011.blockRacing.command.TeamUserManagerCommand;
import top.darkpath2011.blockRacing.command.TeleportCommand;
import top.darkpath2011.blockRacing.command.HelpCommand;
import top.darkpath2011.blockRacing.listener.ChestListener;
import top.darkpath2011.blockRacing.listener.GameListener;
import top.darkpath2011.blockRacing.listener.PlayerListener;
import top.darkpath2011.blockRacing.manager.ChestManager;
import top.darkpath2011.blockRacing.room.GameRoom;
import top.darkpath2011.blockRacing.room.GameStatus;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockRacing extends JavaPlugin {
    @Getter
    public static BlockRacing plugin;
    @Getter
    public static GameRoom room;
    @Getter
    public static ChestManager chestManager;

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
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);

        // 注册命令
        getCommand("blockracing").setExecutor(new GameCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("teamusermanager").setExecutor(new TeamUserManagerCommand());
        getCommand("blockracinghelp").setExecutor(new HelpCommand());

        // 插件加载成功时渲染颜文字
        getLogger().info("§l§a(๑˃̵ᴗ˂̵)و BlockRacing plugin has been enabled. §l§bAuthor: darkpath2011.shazi_awa");
    }

    @Override
    public void onDisable() {
        // 插件卸载时渲染颜文字
        getLogger().info("§l§a(๑˃̵ᴗ˂̵)و BlockRacing plugin has been disabled. §l§bAuthor: darkpath2011.shazi_awa");
    }
}
