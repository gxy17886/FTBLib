package ftb.lib.cmd;

import ftb.lib.FTBLib;
import latmod.lib.LMListUtils;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.*;

import java.util.*;

public abstract class CommandLM extends CommandBase // CommandFTBU CommandSubLM
{
	public final String commandName;
	public final CommandLevel level;
	public static boolean extendedUsageInfo = false;
	
	public CommandLM(String s, CommandLevel l)
	{
		commandName = ((s == null) ? "" : s).trim();
		level = (l == null || commandName.isEmpty()) ? CommandLevel.NONE : l;
	}
	
	public int getRequiredPermissionLevel()
	{ return level.requiredPermsLevel(); }
	
	public boolean canCommandSenderUseCommand(ICommandSender ics)
	{
		if(FTBLib.getEffectiveSide().isClient()) return true;
		return level != CommandLevel.NONE && (level == CommandLevel.ALL || !FTBLib.isDedicatedServer() || super.canCommandSenderUseCommand(ics));
	}
	
	public final String getCommandName()
	{ return commandName; }
	
	public String getCommandUsage(ICommandSender ics)
	{ return '/' + commandName; }
	
	public final void processCommand(ICommandSender ics, String[] args) throws CommandException
	{
		if(!level.isEnabled()) throw new FeatureDisabledException();
		if(args == null) args = new String[0];
		IChatComponent s = onCommand(ics, args);
		if(s != null) FTBLib.printChat(ics, s);
		onPostCommand(ics, args);
	}
	
	public abstract IChatComponent onCommand(ICommandSender ics, String[] args) throws CommandException;
	
	public static IChatComponent error(IChatComponent c)
	{
		c.getChatStyle().setColor(EnumChatFormatting.RED);
		return c;
	}
	
	public final void printHelpLine(ICommandSender ics, String args)
	{ FTBLib.printChat(ics, "/" + commandName + (args != null && args.length() > 0 ? (" " + args) : "")); }
	
	public void onPostCommand(ICommandSender ics, String[] args) throws CommandException
	{
	}
	
	@SuppressWarnings("all")
	public final List<String> addTabCompletionOptions(ICommandSender ics, String[] args, BlockPos pos)
	{
		if(!level.isEnabled()) return null;
		try
		{
			String[] s = getTabStrings(ics, args, args.length - 1);
			if(s != null && s.length > 0)
			{
				if(sortStrings(ics, args, args.length - 1)) Arrays.sort(s);
				return getListOfStringsMatchingLastWord(args, s);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public Boolean getUsername(String[] args, int i)
	{ return null; }
	
	public final boolean isUsernameIndex(String[] args, int i)
	{ return false; }
	
	public static boolean isArg(String[] args, int i, String... s)
	{
		if(args != null && i >= 0 && i < args.length)
		{
			for(int j = 0; j < s.length; j++)
				if(args[i].equals(s[j])) return true;
		}
		
		return false;
	}
	
	public String[] getTabStrings(ICommandSender ics, String args[], int i) throws CommandException
	{
		Boolean b = getUsername(args, i);
		if(b == null) return new String[0];
		if(FTBLib.ftbu != null) return FTBLib.ftbu.getPlayerNames(b);
		ArrayList<String> l = new ArrayList<>();
		for(EntityPlayerMP ep : FTBLib.getAllOnlinePlayers(null))
			l.add(ep.getName());
		return LMListUtils.toStringArray(l);
	}
	
	public boolean sortStrings(ICommandSender ics, String args[], int i)
	{ return getUsername(args, i) == null; }
	
	public static void checkArgs(String[] args, int i) throws CommandException
	{ if(args == null || args.length < i) throw new MissingArgsException(); }
	
	public static void checkArgsStrong(String[] args, int i) throws CommandException
	{ if(args == null || args.length != i) throw new MissingArgsException(); }
}