package ftb.lib.api.item;

import ftb.lib.LMMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

public abstract class ItemLM extends Item implements IItemLM
{
	public final String itemName;
	public final List<ItemStack> itemsAdded;
	
	public boolean requiresMultipleRenderPasses = false;
	
	public ItemLM(String s)
	{
		super();
		itemName = s;
		setUnlocalizedName(getMod().getItemName(s));
		itemsAdded = new ArrayList<>();
	}
	
	public abstract LMMod getMod();
	
	@SuppressWarnings("unchecked")
	public final <E> E register()
	{
		getMod().addItem(this);
		return (E) this;
	}
	
	public final Item getItem()
	{ return this; }
	
	public final String getItemID()
	{ return itemName; }
	
	@SideOnly(Side.CLIENT)
	public abstract CreativeTabs getCreativeTab();
	
	public void onPostLoaded()
	{ addAllDamages(1); }
	
	public void loadRecipes()
	{
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item j, CreativeTabs c, List l)
	{
		for(ItemStack is : itemsAdded)
			if(isVisible(is)) l.add(is);
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return getMod().getItemName(itemName); }
	
	public void addAllDamages(int until)
	{
		for(int i = 0; i < until; i++)
			itemsAdded.add(new ItemStack(this, 1, i));
	}
	
	public void addAllDamages(int[] dmg)
	{
		for(int i = 0; i < dmg.length; i++)
			itemsAdded.add(new ItemStack(this, 1, dmg[i]));
	}
	
	public final boolean requiresMultipleRenderPasses()
	{ return requiresMultipleRenderPasses; }
	
	public int getRenderPasses(int m)
	{ return 1; }
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
	{
	}
	
	public boolean isVisible(ItemStack is)
	{ return true; }
}