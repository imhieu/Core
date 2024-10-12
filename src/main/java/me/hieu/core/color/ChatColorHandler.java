package me.hieu.core.color;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * Author: Le Thanh Hieu
 * Date: 02/10/2024
 */

@Getter
public class ChatColorHandler {

    public HashMap<ChatColor, Integer> colorMap;
    public HashMap<ChatColor, Material> materialMap;

    public ChatColorHandler(){
        colorMap = new HashMap<>();
        materialMap = new HashMap<>();
        for (ChatColor color : ChatColor.values()){
            switch (color){
                case GOLD:
                    colorMap.put(ChatColor.GOLD, 1);
                    materialMap.put(ChatColor.GOLD, Material.YELLOW_STAINED_GLASS_PANE);
                    break;
                case YELLOW:
                    colorMap.put(ChatColor.YELLOW, 2);
                    materialMap.put(ChatColor.YELLOW, Material.YELLOW_STAINED_GLASS_PANE);
                    break;
                case RED:
                    colorMap.put(ChatColor.RED, 3);
                    materialMap.put(ChatColor.RED, Material.RED_STAINED_GLASS_PANE);
                    break;
                case LIGHT_PURPLE:
                    colorMap.put(ChatColor.LIGHT_PURPLE, 4);
                    materialMap.put(ChatColor.LIGHT_PURPLE, Material.PINK_STAINED_GLASS_PANE);
                    break;
                case GREEN:
                    colorMap.put(ChatColor.GREEN, 5);
                    materialMap.put(ChatColor.GREEN, Material.LIME_STAINED_GLASS_PANE);
                    break;
                case AQUA:
                    colorMap.put(ChatColor.AQUA, 6);
                    materialMap.put(ChatColor.AQUA, Material.CYAN_STAINED_GLASS_PANE);
                    break;
                case GRAY:
                    colorMap.put(ChatColor.GRAY, 7);
                    materialMap.put(ChatColor.GRAY, Material.LIGHT_GRAY_STAINED_GLASS);
                    break;
                case DARK_GRAY:
                    colorMap.put(ChatColor.DARK_GRAY, 8);
                    materialMap.put(ChatColor.DARK_GRAY, Material.GRAY_STAINED_GLASS_PANE);
                    break;
                case DARK_AQUA:
                    colorMap.put(ChatColor.DARK_AQUA, 9);
                    materialMap.put(ChatColor.DARK_AQUA, Material.CYAN_STAINED_GLASS_PANE);
                    break;
                case DARK_PURPLE:
                    colorMap.put(ChatColor.DARK_PURPLE, 10);
                    materialMap.put(ChatColor.DARK_PURPLE, Material.PURPLE_STAINED_GLASS_PANE);
                    break;
                case DARK_BLUE:
                    colorMap.put(ChatColor.DARK_BLUE, 11);
                    materialMap.put(ChatColor.DARK_BLUE, Material.BLUE_STAINED_GLASS_PANE);
                    break;
                case BLUE:
                    colorMap.put(ChatColor.BLUE, 12);
                    materialMap.put(ChatColor.BLUE, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                    break;
                case DARK_GREEN:
                    colorMap.put(ChatColor.DARK_GREEN, 13);
                    materialMap.put(ChatColor.DARK_GREEN, Material.GREEN_STAINED_GLASS_PANE);
                    break;
                case DARK_RED:
                    colorMap.put(ChatColor.DARK_RED, 14);
                    materialMap.put(ChatColor.DARK_RED, Material.RED_STAINED_GLASS_PANE);
                    break;
                case BLACK:
                    colorMap.put(ChatColor.BLACK, 15);
                    materialMap.put(ChatColor.BLACK, Material.BLACK_STAINED_GLASS_PANE);
                    break;
            }
        }
    }

}
