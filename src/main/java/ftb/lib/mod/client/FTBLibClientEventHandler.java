package ftb.lib.mod.client;

import ftb.lib.*;
import ftb.lib.api.EventFTBWorldClient;
import ftb.lib.client.FTBLibClient;
import ftb.lib.item.*;
import ftb.lib.mod.FTBLibFinals;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

@SideOnly(Side.CLIENT)
public class FTBLibClientEventHandler
{
	public static final FTBLibClientEventHandler instance = new FTBLibClientEventHandler();
	
	@SubscribeEvent
	public void onConnected(FMLNetworkEvent.ClientConnectedToServerEvent e)
	{
		ServerData sd = FTBLibClient.mc.getCurrentServerData();
		String s = (sd == null || sd.serverIP.isEmpty()) ? "localhost" : sd.serverIP.replace('.', '_');
		FTBWorld.client = new FTBWorld(Side.CLIENT, new UUID(0L, 0L), s);
		
		EventFTBWorldClient event = new EventFTBWorldClient(FTBWorld.client, true);
		if(FTBLib.ftbu != null) FTBLib.ftbu.onFTBWorldClient(event);
		event.post();
	}
	
	@SubscribeEvent
	public void onDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent e)
	{
		EventFTBWorldClient event = new EventFTBWorldClient(null, false);
		if(FTBLib.ftbu != null) FTBLib.ftbu.onFTBWorldClient(event);
		event.post();
		FTBWorld.client = null;
	}
	
	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent e)
	{
		if(e.itemStack == null || e.itemStack.getItem() == null) return;
		
		if(FTBLibModClient.item_reg_names.get())
		{
			e.toolTip.add(LMInvUtils.getRegName(e.itemStack).toString());
		}
		
		if(FTBLibModClient.item_ore_names.get())
		{
			List<String> ores = ODItems.getOreNames(e.itemStack);
			
			if(ores != null && !ores.isEmpty())
			{
				e.toolTip.add("Ore Dictionary names:");
				for(String or : ores)
					e.toolTip.add("> " + or);
			}
		}
	}
	
	@SubscribeEvent
	public void onDrawDebugText(RenderGameOverlayEvent.Text e)
	{
		if(!FTBLibClient.mc.gameSettings.showDebugInfo)
		{
			if(FTBLibFinals.DEV)
			{
				e.left.add("[MC " + EnumChatFormatting.GOLD + Loader.MC_VERSION + EnumChatFormatting.WHITE + " DevEnv]");
				DevConsole.text.set("MC", Loader.MC_VERSION + ", " + FTBLibClient.mc.debug);
			}
		}
		else
		{
			if(DevConsole.enabled()) e.left.add("r: " + MathHelperMC.get2DRotation(FTBLibClient.mc.thePlayer));
		}
	}
}