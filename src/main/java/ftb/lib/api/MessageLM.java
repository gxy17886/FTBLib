package ftb.lib.api;

import java.io.*;

import ftb.lib.LMNBTUtils;
import io.netty.buffer.ByteBuf;
import latmod.lib.ByteIOStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public abstract class MessageLM implements IMessage, IMessageHandler<MessageLM, IMessage>
{
	public final NBTTagCompound readTag()
	{
		try
		{
			short s = io.readShort();
			if(s >= 0)
			{
				byte[] abyte = new byte[s];
				io.readRawBytes(abyte);
				return LMNBTUtils.readMap(new ByteArrayInputStream(abyte));
			}
		}
		catch(Exception ex)
		{ ex.printStackTrace(); }
		
		return null;
	}
	
	public final void writeTag(NBTTagCompound tag)
	{
		try
		{
			if (tag == null)
				io.writeShort((short)-1);
			else
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				LMNBTUtils.writeMap(baos, tag);
				byte[] abyte = baos.toByteArray();
				io.writeShort((short)abyte.length);
				io.writeRawBytes(abyte);
			}
		}
		catch(Exception ex)
		{ ex.printStackTrace(); }
	}
	
	// End of static //
	
	/** No bytes */
	public static final int DATA_NONE = 0;
	
	/** Max 65K bytes */
	public static final int DATA_SHORT = 2;
	
	/** Max 4B bytes */
	public static final int DATA_LONG = 4;
	
	public final int dataType;
	public ByteIOStream io;
	
	public MessageLM(int t)
	{
		dataType = t;
		if(t != DATA_NONE) io = new ByteIOStream();
	}
	
	public abstract LMNetworkWrapper getWrapper();
	
	public IMessage onMessage(MessageContext ctx)
	{ return null; }
	
	public final void fromBytes(ByteBuf bb)
	{
		if(dataType == DATA_NONE) return;
		int len = 0;
		
		if(dataType == DATA_SHORT)
			len = bb.readShort() & 0xFFFF;
		else if(dataType == DATA_LONG)
			len = bb.readInt();
		
		byte[] b = new byte[len];
		bb.readBytes(b, 0, len);
		io.setCompressedData(b);
	}
	
	public final void toBytes(ByteBuf bb)
	{
		if(dataType == DATA_NONE) return;
		
		byte[] b = io.toCompressedByteArray();
		
		if(dataType == DATA_SHORT)
			bb.writeShort((short)b.length);
		else if(dataType == DATA_LONG)
			bb.writeInt(b.length);
		
		bb.writeBytes(b, 0, b.length);
	}
	
	public final IMessage onMessage(MessageLM m, MessageContext ctx)
	{ io = m.io; return onMessage(ctx); }
	
	public final void sendTo(EntityPlayerMP ep)
	{
		//if(FTBLibFinals.DEV) FTBLib.logger.info("[S] Message sent: " + getClass().getName());
		if(ep != null) getWrapper().sendTo(this, ep);
		else getWrapper().sendToAll(this);
	}
	
	public final void sendToServer()
	{
		//if(FTBLibFinals.DEV) FTBLib.logger.info("[C] Message sent: " + getClass().getName());
		getWrapper().sendToServer(this);
	}
}