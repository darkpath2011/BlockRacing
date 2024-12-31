package top.darkpath2011.blockRacing.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 21:04
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class Tools {
    public static void sendActionBar(Player player, String message) {
        ComponentBuilder builder = new ComponentBuilder(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, builder.create());
    }

    public static Material getRandomBlock() {
        List<Material> availableBlocks = new ArrayList<>();
        for (Material material : Material.values()) {
            if (!material.isBlock() && isUnobtainableBlock(material)) {
                continue;
            }
            availableBlocks.add(material);
        }
        Random random = new Random();
        int randomIndex = random.nextInt(availableBlocks.size());
        return availableBlocks.get(randomIndex);
    }

    /**
     * 判断是否为末地维度的方块
     * @param material
     * @return
     */
    private static boolean isEndDimensionBlock(Material material) {
        switch (material) {
            case END_STONE:
            case PURPUR_BLOCK:
            case NETHER_STAR:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case END_GATEWAY:
            case END_PORTAL:
            case END_PORTAL_FRAME:
            case DRAGON_EGG:
                return true;
            default:
                return false;
        }
    }

    private static boolean isUnobtainableBlock(Material material) {
        switch (material) {
            case BEDROCK:
            case END_PORTAL:
            case END_PORTAL_FRAME:
            case COMMAND_BLOCK:
            case STRUCTURE_BLOCK:
            case BARRIER:
            case STRUCTURE_VOID:
                return true;
            case OAK_SAPLING:
            case BIRCH_SAPLING:
            case SPRUCE_SAPLING:
            case JUNGLE_SAPLING:
            case ACACIA_SAPLING:
            case DARK_OAK_SAPLING:
            case WHEAT:
            case MELON:
            case PUMPKIN:
            case SWEET_BERRIES:
                return true;
            default:
                return false;
        }
    }
}
