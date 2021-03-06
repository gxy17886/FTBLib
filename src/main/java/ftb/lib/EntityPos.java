package ftb.lib;

import latmod.lib.MathHelperLM;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

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
	
	public boolean equalsIntPos(EntityPos p)
	{ return (p == this) || (p != null && dim == p.dim && intX() == p.intX() && intY() == p.intY() && intZ() == p.intZ()); }
	
	public void setPos(double px, double py, double pz, int d)
	{
		x = px;
		y = py;
		z = pz;
		dim = d;
	}
	
	public void set(Entity e)
	{ setPos(e.posX, e.posY, e.posZ, e.dimension); }
	
	public void set(BlockPos c, int dim)
	{ setPos(c.getX() + 0.5D, c.getY() + 0.5D, c.getZ() + 0.5D, dim); }
	
	public EntityPos center()
	{ return new EntityPos(intX() + 0.5D, intY() + 0.5D, intZ() + 0.5D, dim); }
	
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
	
	public BlockPos toChunkCoordinates()
	{ return new BlockPos(intX(), intY(), intZ()); }
	
	public Vec3 toVec3()
	{ return new Vec3(x, y, z); }
	
	public int[] toIntArray()
	{ return new int[] {intX(), intY(), intZ(), dim}; }
	
	public EntityPos clone()
	{ return new EntityPos(x, y, z, dim); }
	
	public static EntityPos fromIntArray(int[] pos)
	{
		if(pos == null || pos.length < 4) return null;
		return new EntityPos(pos[0] + 0.5D, pos[1] + 0.5D, pos[2] + 0.5D, pos[3]);
	}
}