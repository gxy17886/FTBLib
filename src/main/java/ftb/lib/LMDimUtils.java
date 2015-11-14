package ftb.lib;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public class LMDimUtils
{
	public static boolean teleportPlayer(EntityPlayerMP ep, EntityPos pos)
	{ return teleportPlayer(ep, pos.x, pos.y, pos.z, pos.dim); }
	
	public static boolean teleportPlayer(EntityPlayerMP ep, double x, double y, double z, int dim)
	{
		if(ep == null) return false;
		ep.fallDistance = 0F;
		
		if(ep.dimension == dim)
		{
			ep.playerNetServerHandler.setPlayerLocation(x, y, z, ep.rotationYaw, ep.rotationPitch);
			return true;
		}
		
		if(!DimensionManager.isDimensionRegistered(dim)) return false;
		MinecraftServer mcs = MinecraftServer.getServer();
		if (mcs == null || (dim != 0 && !mcs.getAllowNether()))
			return false;
		
		WorldServer w1 = mcs.worldServerForDimension(dim);
		if (w1 == null)
		{
			System.err.println("Cannot teleport " + ep.getName() + " to Dimension " + dim + ": Missing WorldServer");
			return false;
		}
		
		WorldServer w0 = (WorldServer)ep.worldObj;
		
		if(ep.ridingEntity != null)
		{
			ep.mountEntity(null);
		}
		
		boolean chw = w0 != w1;
		
		w0.updateEntityWithOptionalForce(ep, false);
		
		ep.closeScreen();
		
		if(chw)
		{
			ep.dimension = dim;
			ep.playerNetServerHandler.sendPacket(new S07PacketRespawn(ep.dimension, ep.worldObj.getDifficulty(), w1.getWorldInfo().getTerrainType(), ep.theItemInWorldManager.getGameType()));
			w0.getPlayerManager().removePlayer(ep);
			
			ep.closeScreen();
			w0.playerEntities.remove(ep);
			w0.updateAllPlayersSleepingFlag();
			int i = ep.chunkCoordX;
			int j = ep.chunkCoordZ;
			
			if(ep.addedToChunk && w0.getChunkProvider().chunkExists(i, j))
			{
				w0.getChunkFromChunkCoords(i, j).removeEntity(ep);
				w0.getChunkFromChunkCoords(i, j).setModified(true);
			}
			
			w0.loadedEntityList.remove(ep);
			w0.onEntityRemoved(ep);
		}
		
		ep.setLocationAndAngles(x, y, z, ep.rotationYaw, ep.rotationPitch);
		w1.theChunkProviderServer.loadChunk(MathHelper.floor_double(x) >> 4, MathHelper.floor_double(z) >> 4);
		
		if(chw)
		{
			w1.spawnEntityInWorld(ep);
			ep.setWorld(w1);
		}
		
		ep.setLocationAndAngles(x, y, z, ep.rotationYaw, ep.rotationPitch);
		w1.updateEntityWithOptionalForce(ep, false);
		ep.setLocationAndAngles(x, y, z, ep.rotationYaw, ep.rotationPitch);
		
		if(chw) ep.mcServer.getConfigurationManager().func_72375_a(ep, w1);
		ep.playerNetServerHandler.setPlayerLocation(x, y, z, ep.rotationYaw, ep.rotationPitch);
		
		w1.updateEntityWithOptionalForce(ep, false);
		
		ep.theItemInWorldManager.setWorld(w1);
		ep.mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(ep, w1);
		ep.mcServer.getConfigurationManager().syncPlayerInventory(ep);
		for(Object o : ep.getActivePotionEffects())
			ep.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(ep.getEntityId(), (PotionEffect)o));
		ep.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(ep.experience, ep.experienceTotal, ep.experienceLevel));
		
		ep.setLocationAndAngles(x, y, z, ep.rotationYaw, ep.rotationPitch);
		return true;
	}
	
	public static World getWorld(int dim)
	{
		if(dim == 0) return FTBLib.getServerWorld();
		return DimensionManager.getWorld(dim);
	}
	
	public static String getDimName(int dim)
	{
		if(dim == 0) return "Overworld";
		else if(dim == 1) return "The End";
		else if(dim == -1) return "Nether";
		
		World w = getWorld(dim);
		return w == null ? ("DIM" + dim) : w.provider.getDimensionName();
	}
	
	public static double getMovementFactor(int dim)
	{
		if(dim == 0) return 1D;
		else if(dim == 1) return 1D;
		else if(dim == -1) return 8D;
		else
		{
			World w = getWorld(dim);
			return (w == null) ? 1D : w.provider.getMovementFactor();
		}
	}
	
	public static double getWorldScale(int dim)
	{ return 1D / getMovementFactor(dim); }
	
	public static BlockPos getSpawnPoint(int dim)
	{
		World w = getWorld(dim);
		return (w == null) ? null : w.getSpawnPoint();
	}
	
	public static EntityPos getEntitySpawnPoint(int dim)
	{
		BlockPos c = getSpawnPoint(dim);
		if(c == null) return null;
		return new EntityPos(c, dim);
	}
	
	public static BlockPos getPlayerSpawnPoint(EntityPlayerMP ep, int dim)
	{
		BlockPos c = ep.getBedLocation(dim);
		return (c == null) ? getSpawnPoint(dim) : c;
	}
	
	public static EntityPos getPlayerEntitySpawnPoint(EntityPlayerMP ep, int dim)
	{
		BlockPos c = getPlayerSpawnPoint(ep, dim);
		EntityPos p = new EntityPos();
		p.x = c.getX() + 0.5D;
		p.y = c.getY() + 0.5D;
		p.z = c.getZ() + 0.5D;
		p.dim = dim;
		return p;
	}
}