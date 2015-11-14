package ftb.lib.mod.net;

import ftb.lib.*;
import ftb.lib.api.*;
import ftb.lib.mod.FTBLibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;

public class MessageReload extends MessageLM
{
	public MessageReload() { super(DATA_NONE); }
	
	public LMNetworkWrapper getWrapper()
	{ return FTBLibNetHandler.NET; }
	
	public IMessage onMessage(MessageContext ctx)
	{
		FTBWorld.reloadGameModes();
		EntityPlayer ep = FTBLibMod.proxy.getClientPlayer();
		new EventFTBReloadPre(Side.CLIENT, ep).post();
		new EventFTBReload(Side.CLIENT, ep).post();
		FTBLib.printChat(ep, new ChatComponentTranslation("ftbl:reloadedClient"));
		return null;
	}
}