package top.darkpath2011.blockRacing.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.darkpath2011.blockRacing.utils.Tools;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 19:46
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
@Getter
@Setter
public class Team {
    private String name;
    private int score;
    private Set<Player> players;
    private String color;
    private List<Task> tasks;
    private Map<Integer, Inventory> teamChests;
    private Map<Integer, Location> teamLocations;

    public Team(String name,String color) {
        this.name = name;
        this.color = color;
        this.score = 0;
        this.tasks = new ArrayList<>();
        this.players = new HashSet<>();
        this.teamChests = new HashMap<>();
        this.teamLocations = new HashMap<>();
        //初始4个任务
        for (int i=1;i<=4;i++){
            this.tasks.add(addTask());
        }
        for (int i=1;i<=3;i++){
            teamChests.put(i, Bukkit.createInventory(null, 54, getColor()+getName()+" §r[§a"+i+"§r]"));
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
        String newName = getColor()+getName();
        player.setDisplayName(newName);
        player.setPlayerListName(newName);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        String newName = "§r"+getName();
        player.setDisplayName(newName);
        player.setPlayerListName(newName);
    }

    public void removeTask(Material material) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if (t.getMaterial() == material) {
                iterator.remove();
                tasks.add(addTask());
                break;
            }
        }
    }

    public Task addTask() {
        Task material = Tools.getRandomBlock();
        if (!players.isEmpty()){
            for (Player player : players){
                for (ItemStack playerItem : player.getInventory().getContents()) {
                    if (playerItem != null && playerItem.isSimilar(playerItem)) {
                        addScore(player.getName(),playerItem.getType());
                        break;
                    }
                }
            }
        }
        return material;
    }

    public void createTeamLocation(Location location) {
        teamLocations.put(teamLocations.size()+1,location);
    }

    public void removeTeamLocation(Integer locationId) {
        teamLocations.remove(locationId);
    }

    public void openTeamChest(Player player,Integer chestId) {
        Inventory teamChest = teamChests.get(chestId);
        player.openInventory(teamChest);
    }

    public void clearTeamChests() {
        for (Inventory inventory : teamChests.values()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, null);
            }
        }
        teamChests.clear();
    }

    public void addScore(String player,Material item){
        Bukkit.getServer().broadcastMessage(
                "§f[§6✔§f] "+getColor()+getName()+"§r队员 ["+getColor()+player
                        +"§r] §a完成了§r目标 [§f"+ item.name()+"§r]§r!"
        );
        setScore(getScore() + 1);
        Player p =Bukkit.getPlayer(player);
        if (p!= null){
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
        }
    }

    public void roll() {
        List<Task> taskList = new ArrayList<>(tasks);
        Collections.shuffle(taskList);
        for (Task task : taskList) {
            addScore("系统",task.getMaterial());
            removeTask(task.getMaterial());
        }
    }

    public List<Task> getTasksByMaterial(Material material) {
        return tasks.stream()
                .filter(t -> t.getMaterial() == material)
                .collect(Collectors.toList());
    }

}
