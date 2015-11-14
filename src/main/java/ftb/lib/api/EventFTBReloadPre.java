package ftb.lib.api;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;

/** Should only be used by FTBUtilities! */
public class EventFTBReloadPre extends EventLM
{
	public final Side side;
	public final ICommandSender sender;
	
	public EventFTBReloadPre(Side s, ICommandSender ics)
	{ side = s; sender = ics; }
}