package ftb.lib.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class BasicInventory implements IInventory
{
	public final ItemStack[] items;
	
	public BasicInventory(int i)
	{ items = new ItemStack[i]; }
	
	public int getSizeInventory()
	{ return items.length; }
	
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }
	
	public ItemStack decrStackSize(int slot, int amt)
	{ return LMInvUtils.decrStackSize(this, slot, amt); }
	
	public ItemStack getStackInSlotOnClosing(int i)
	{ return LMInvUtils.getStackInSlotOnClosing(this, i); }
	
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; markDirty(); }
	
	public String getName()
	{ return ""; }
	
	public IChatComponent getDisplayName()
	{ return new ChatComponentText(getName()); }
	
	public boolean hasCustomName()
	{ return false; }
	
	public int getInventoryStackLimit()
	{ return 64; }
	
	public void markDirty() { }
	
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return true; }
	
	public void openInventory(EntityPlayer ep) { }
	
	public void closeInventory(EntityPlayer ep) { }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	public int getField(int id)
	{ return 0; }
	
	public void setField(int id, int value)
	{
	}
	
	public int getFieldCount()
	{ return 0; }
	
	public void clear()
	{
		for(int i = 0; i < items.length; i++)
			items[i] = null;
	}
}