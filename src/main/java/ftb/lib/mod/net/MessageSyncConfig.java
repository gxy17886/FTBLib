package ftb.lib.mod.net;

import ftb.lib.api.*;
import ftb.lib.api.config.ConfigSyncRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessageSyncConfig extends MessageLM
{
	public MessageSyncConfig() { super(DATA_LONG); }
	
	public MessageSyncConfig(EntityPlayerMP ep)
	{ this(); ConfigSyncRegistry.writeToIO(io); }
	
	public LMNetworkWrapper getWrapper()
	{ return FTBLibNetHandler.NET; }
	
	public IMessage onMessage(MessageContext ctx)
	{
		ConfigSyncRegistry.readFromIO(io);
		return null;
	}
}