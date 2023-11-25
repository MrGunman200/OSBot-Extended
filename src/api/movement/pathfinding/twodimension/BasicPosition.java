package api.movement.pathfinding.twodimension;

public class BasicPosition
{
    public final int x;
    public final int y;
    public final int z;
    private final int hashCode;
    private String toString = null;

    public BasicPosition(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        final int prime = 13;
        long result = 1;

        result = prime * result + Double.doubleToLongBits(x);
        result = prime * result + Double.doubleToLongBits(y);
        result = prime * result + Double.doubleToLongBits(z);

        this.hashCode = (int) (result ^ result >> 32);
    }

    @Override
    public String toString()
    {
        if (this.toString == null)
        {
            this.toString = "BasicPosition(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
        }

        return this.toString;
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

}
