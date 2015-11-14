package ftb.lib.mod;

import ftb.lib.*;
import ftb.lib.api.*;
import ftb.lib.api.config.ConfigListRegistry;
import ftb.lib.item.ODItems;
import ftb.lib.mod.net.*;
import latmod.lib.OS;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = FTBLibFinals.MOD_ID, name = FTBLibFinals.MOD_NAME, version = FTBLibFinals.VERSION, dependencies = FTBLibFinals.DEPS)
public class FTBLibMod
{
	@Mod.Instance(FTBLibFinals.MOD_ID)
	public static FTBLibMod inst;
	
	@SidedProxy(serverSide = "ftb.lib.mod.FTBLibModCommon", clientSide = "ftb.lib.mod.FTBLibModClient")
	public static FTBLibModCommon proxy;
	
	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent e)
	{
		if(FTBLibFinals.DEV)
			FTBLib.logger.info("Loading FTBLib, DevEnv");
		else
			FTBLib.logger.info("Loading FTBLib, v" + FTBLibFinals.VERSION);
		
		FTBLib.logger.info("OS: " + OS.current + ", 64bit: " + OS.is64);
		
		FTBLib.init(e.getModConfigurationDirectory());
		JsonHelper.init();
		FTBLibNetHandler.init();
		FTBWorld.init();
		ODItems.preInit();
		
		EventBusHelper.register(new FTBLibEventHandler());
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent e)
	{
		ODItems.postInit();
	}
	
	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new CommandFTBMode());
		e.registerServerCommand(new CommandFTBReload());
		e.registerServerCommand(new CommandFTBWorldID());
	}
	
	/*
	@NetworkCheckHandler
	public boolean checkNetwork(Map<String, String> m, Side side)
	{
		String s = m.get(FTBLibFinals.MOD_ID);
		return s == null || FTBLibFinals.DEV || s.equals(FTBLibFinals.VERSION);
	}*/
	
	public static void reload(ICommandSender sender, boolean printMessage)
	{
		ConfigListRegistry.reloadInstance();
		FTBWorld.reloadGameModes();
		
		if(FTBWorld.server.setMode(Side.SERVER, FTBWorld.server.getMode(), true) == 0)
		{
			new MessageSyncConfig(null).sendTo(null);
			
			new EventFTBReloadPre(Side.SERVER, sender).post();
			new EventFTBReload(Side.SERVER, sender).post();
			new MessageReload().sendTo(null);
			if(printMessage) FTBLib.printChat(BroadcastSender.inst, new ChatComponentTranslation("ftbl:reloadedServer"));
		}
	}
}