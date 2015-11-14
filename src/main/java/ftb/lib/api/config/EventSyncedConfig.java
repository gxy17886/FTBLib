package ftb.lib.api.config;

import ftb.lib.api.EventLM;
import latmod.lib.config.ConfigList;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class EventSyncedConfig extends EventLM
{
	public final ConfigList list;
	
	public EventSyncedConfig(ConfigList l)
	{ list = l; }
}