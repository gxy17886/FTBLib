package ftb.lib.mod;

import java.util.UUID;

import ftb.lib.FTBWorld;
import ftb.lib.api.EventFTBWorldClient;
import ftb.lib.client.FTBLibClient;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class FTBLibClientEventHandler
{
	@SubscribeEvent
	public void preTexturesLoaded(TextureStitchEvent.Pre e)
	{
		/*
		if(e.map.getTextureType() == 0)
		{
			FTBLibClient.blockNullIcon = e.map.registerIcon("ftbl:empty_block");
			FTBLibClient.clearCachedData();
		}
		else if(e.map.getTextureType() == 1)
			FTBLibClient.unknownItemIcon = e.map.registerIcon("ftbl:unknown");
			*/
	}
	
	@SubscribeEvent
	public void onConnected(FMLNetworkEvent.ClientConnectedToServerEvent e)
	{
		ServerData sd = FTBLibClient.mc.getCurrentServerData();
		String s = (sd == null || sd.serverIP.isEmpty()) ? "localhost" : sd.serverIP.replace('.', '_');
		FTBWorld.client = new FTBWorld(new UUID(0L, 0L), s);
		new EventFTBWorldClient(FTBWorld.client, true).post();
	}
	
	@SubscribeEvent
	public void onDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent e)
	{
		new EventFTBWorldClient(null, false).post();
		FTBWorld.client = null;
	}
}