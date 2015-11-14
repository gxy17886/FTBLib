package ftb.lib;

import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BroadcastSender implements ICommandSender
{
	public static final BroadcastSender inst = new BroadcastSender();
	
	public static final BroadcastSender mute = new BroadcastSender()
	{ public void addChatMessage(IChatComponent ics) { } };
	
	public String getName()
	{ return "[Server]"; }
	
	public IChatComponent getDisplayName()
	{ return new ChatComponentText(getName()); }
	
	public void addChatMessage(IChatComponent message)
	{ FTBLib.getServer().getConfigurationManager().sendChatMsgImpl(message, true); }
	
	public boolean canUseCommand(int permLevel, String commandName)
	{ return true; }
	
	public BlockPos getPosition()
	{ return new BlockPos(0, 66, 0); }
	
	public Vec3 getPositionVector()
	{ return new Vec3(0D, 66D, 0D); }
	
	public World getEntityWorld()
	{ return FTBLib.getServer().getEntityWorld(); }
	
	public Entity getCommandSenderEntity()
	{ return null; }
	
	public boolean sendCommandFeedback()
	{ return false; }
	
	public void setCommandStat(Type type, int amount)
	{  }
}