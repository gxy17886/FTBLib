package ftb.lib.mod.net;

import ftb.lib.FTBWorld;
import ftb.lib.api.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSendGameMode extends MessageLM
{
	public MessageSendGameMode() { super(DATA_SHORT); }
	
	public MessageSendGameMode(String s)
	{ this(); io.writeString(s); }
	
	public LMNetworkWrapper getWrapper()
	{ return FTBLibNetHandler.NET; }
	
	public IMessage onMessage(MessageContext ctx)
	{ FTBWorld.client.setMode(Side.CLIENT, io.readString(), true); return null; }
}