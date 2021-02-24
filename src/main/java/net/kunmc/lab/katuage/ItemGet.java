package net.kunmc.lab.katuage;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemGet implements Listener {
    //ドロップアイテム入手時の処理
    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event){
        Item i = event.getItem();
        Player p = event.getPlayer();
        if(i.getCustomName()==null){
            if(i.getItemStack().getItemMeta().getDisplayName().equals("MoneyCraft")){
                i.getItemStack().setLore(null);
            }
        } else if(i.getCustomName().equals("KatuagePlugin"+p.getName())){
            event.setCancelled(true);
        }else{
            if(i.getItemStack().getItemMeta().getDisplayName().equals("MoneyCraft")){
                i.getItemStack().setLore(null);
            }
        }
    }
    @EventHandler
    public  void onDropper(InventoryPickupItemEvent event){
        Item i = event.getItem();
        if(i.getItemStack().getItemMeta().hasLore()){
            if(i.getItemStack().getItemMeta().getLore().get(0).equals("threw")){
                i.getItemStack().setLore(null);
            }
        }
    }
}
