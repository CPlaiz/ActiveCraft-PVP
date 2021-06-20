package de.cplaiz.activecraft.listener.inventory;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.Objects;

public class Navigator implements Listener {

    private final Inventory navigatorInventory;

    public Navigator(int rows) {
        this.navigatorInventory = Bukkit.createInventory(null, rows * 9, "§5 Navigator §8 | §7 Choose your server!");


        this.navigatorInventory.setItem(4, new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .displayname("SuckaMasters")
                .lore("pepega", "sucka")
                .flag(ItemFlag.HIDE_ATTRIBUTES)

        .build());
    }

    String lore = "pepega compass";

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if(event.getItem() == null) return;
        if(event.getItem().getType() == Material.COMPASS) {
                event.getPlayer().openInventory(this.navigatorInventory);
                event.setCancelled(true);

        }
    }
    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack() == null) return;
        if(event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
            event.getPlayer().openInventory(this.navigatorInventory);
            event.setCancelled(true);

        } else event.setCancelled(true);
    }


    @EventHandler
    private void onItemClick(InventoryClickEvent event) {
        if (Objects.equals(event.getClickedInventory(), this.navigatorInventory)) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE) {
                event.getWhoClicked().sendMessage(Main.PREFIX + "have fun sucking");
                Player clicker = (Player) event.getWhoClicked();
                clicker.performCommand("spawn");
            }
        }
    }





}
