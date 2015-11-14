package ftb.lib.mod;

import net.minecraft.command.*;

public class CommandFTBReload extends CommandBase
{
	public String getName()
	{ return "ftb_reload"; }
	
	public String getCommandUsage(ICommandSender ics)
	{ return "/ftb_reload"; }
	
	public int getRequiredPermissionLevel()
	{ return 4; }
	
	public void execute(ICommandSender ics, String[] args)
	{ FTBLibMod.reload(ics, true); }
}