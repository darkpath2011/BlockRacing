package top.darkpath2011.blockRacing.manager;

import lombok.Getter;
import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/31 19:01
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
@Getter
public class ChestManager {
    private Map<UUID, Menu> menus = new HashMap<>();

    public void addMenu(Menu menu) {
        menus.put(menu.getMenuHolder().getMenuId(), menu);
        BlockRacing.plugin.getLogger().info("成功加载: "+menu.getMenuHolder().getMenuId());
    }
}
