package ftb.lib;

import ftb.lib.item.ItemStackTypeAdapter;
import latmod.lib.LMJsonUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class JsonHelper
{
	public static void init()
	{
		LMJsonUtils.register(IChatComponent.class, new IChatComponent.Serializer());
		LMJsonUtils.register(ChatStyle.class, new ChatStyle.Serializer());
		LMJsonUtils.register(ItemStack.class, new ItemStackTypeAdapter());
	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient()
	{
	}
}