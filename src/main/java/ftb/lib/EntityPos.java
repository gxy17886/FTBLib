package ftb.lib;

import latmod.lib.MathHelperLM;
import latmod.lib.util.VecLM;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class EntityPos implements Cloneable
{
	public double x, y, z;
	public int dim;
	
	public EntityPos() { }
	
	public EntityPos(Entity e)
	{ set(e); }
	
	public EntityPos(BlockPos c, int dim)
	{ set(c, dim); }
	
	public EntityPos(double px, double py, double pz, int d)
	{ setPos(px, py, pz, d); }
	
	public boolean equalsPos(Entity e)
	{ return x == e.posX && y == e.posY && z == e.posZ && dim == e.dimension; }
	
	public void setPos(double px, double py, double pz, int d)
	{ x = px; y = py; z = pz; dim = d; }
	
	public void set(Entity e)
	{ setPos(e.posX, e.posY, e.posZ, e.dimension); }
	
	public void set(BlockPos c, int dim)
	{ setPos(c.getX() + 0.5D, c.getY() + 0.5D, c.getZ() + 0.5D, dim); }
	
	public void readFromNBT(NBTTagCompound tag)
	{
		x = tag.getDouble("X");
		y = tag.getDouble("Y");
		z = tag.getDouble("Z");
		dim = tag.getInteger("D");
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setDouble("X", x);
		tag.setDouble("Y", y);
		tag.setDouble("Z", z);
		tag.setInteger("D", dim);
	}
	
	public int intX()
	{ return MathHelperLM.floor(x); }
	
	public int intY()
	{ return MathHelperLM.floor(y); }
	
	public int intZ()
	{ return MathHelperLM.floor(z); }
	
	public BlockPos toBlockPos()
	{ return new BlockPos(intX(), intY(), intZ()); }
	
	public VecLM toVertex()
	{ return new VecLM(x, y, z); }
	
	public int[] toIntArray()
	{ return new int[] { intX(), intY(), intZ(), dim }; }
	
	public EntityPos clone()
	{ return new EntityPos(x, y, z, dim); }
	
	public static EntityPos fromIntArray(int[] pos)
	{
		if(pos == null || pos.length < 4) return null;
		return new EntityPos(pos[0] + 0.5D, pos[1] + 0.5D, pos[2] + 0.5D, pos[3]);
	}
	
	public static class Rot extends EntityPos
	{
		public float rotYaw, rotPitch;
		
		public Rot() { }
		
		public Rot(Entity e)
		{ super(e); }
		
		public boolean equalsPosRot(Entity e)
		{ return equalsPos(e) && rotYaw == e.rotationYaw && rotPitch == e.rotationPitch; }
		
		public Rot(double x, double y, double z, int dim, float yaw, float pitch)
		{ super(x, y, z, dim); rotYaw = yaw; rotPitch = pitch; }
		
		public void set(Entity e)
		{
			super.set(e);
			rotYaw = e.rotationYaw;
			rotPitch = e.rotationPitch;
		}
		
		public void readFromNBT(NBTTagCompound tag)
		{
			super.readFromNBT(tag);
			rotYaw = tag.getFloat("YR");
			rotPitch = tag.getFloat("PR");
		}
		
		public void writeToNBT(NBTTagCompound tag)
		{
			super.writeToNBT(tag);
			tag.setFloat("YR", rotYaw);
			tag.setFloat("PR", rotPitch);
		}
		
		public Rot clone()
		{ return new Rot(x, y, z, dim, rotYaw, rotPitch); }
	}
}