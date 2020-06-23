package me.kylesimmonds.serveressentials.gui;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


//TODO BROKEN BROKEN BROKEN BROKEN BROKEN BROKEN BROKEN BROKEN
public class GUI {

    //TODO add support for multiple GUI's
    private int slots;
    private String title;
    private InventoryHolder ih;
    private int slotLocation;
    private int getNewItemSlot;
    private Inventory GUInv = Bukkit.getServer().createInventory(null, slots, title);

    public GUI(InventoryHolder inventoryHolder, int slots, String title) {
        this.slots = slots;
        this.title = title;
        this.ih = inventoryHolder;
    }

    public Inventory getGUInv() {
        return GUInv;
    }

    public int getNewItemSlot() {
        return getNewItemSlot;
    }

    public void setNewItemSlot(int getNewItemSlot) {
        this.getNewItemSlot = getNewItemSlot;
    }

    public void setGUInv(Inventory GUInv) {
        this.GUInv = GUInv;
    }

    //add option for wool color..etc
    public ItemStack addItem(Material material, int amount, String displayName, List<String> lore, int location) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GOLD + "Created GUI successfully!");
        getNewItemSlot = location;
        return itemStack;
    }


    public int getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(int slotLocation) {
        this.slotLocation = slotLocation;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InventoryHolder getIh() {
        return ih;
    }

    public void setIh(InventoryHolder ih) {
        this.ih = ih;
    }
}
