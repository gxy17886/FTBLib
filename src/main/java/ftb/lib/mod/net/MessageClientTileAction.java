package ftb.lib.mod.net;
import ftb.lib.api.*;
import ftb.lib.api.gui.IClientActionTile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessageClientTileAction extends MessageLM
{
	public MessageClientTileAction() { super(DATA_LONG); }
	
	public MessageClientTileAction(TileEntity t, String s, NBTTagCompound tag)
	{
		this();
		io.writeInt(t.getPos().getX());
		io.writeInt(t.getPos().getY());
		io.writeInt(t.getPos().getZ());
		io.writeString(s);
		writeTag(tag);
	}
	
	public LMNetworkWrapper getWrapper()
	{ return FTBLibNetHandler.NET_GUI; }
	
	public IMessage onMessage(MessageContext ctx)
	{
		int x = io.readInt();
		int y = io.readInt();
		int z = io.readInt();
		String action = io.readString();
		NBTTagCompound data = readTag();
		
		EntityPlayerMP ep = ctx.getServerHandler().playerEntity;
		TileEntity te = ep.worldObj.getTileEntity(new BlockPos(x, y, z));
		
		if(te instanceof IClientActionTile)
			((IClientActionTile)te).onClientAction(ep, action, data);
		
		return null;
	}
}