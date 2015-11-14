package ftb.lib.api;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;

public class EventFTBReload extends EventLM
{
	public final Side side;
	public final ICommandSender sender;
	
	public EventFTBReload(Side s, ICommandSender ics)
	{ side = s; sender = ics; }
}