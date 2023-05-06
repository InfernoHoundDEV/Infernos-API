package dev.infernohound.infernoscommands.data;

public class BlockDimPos {
    private int dim;
    private int x;
    private int y;
    private int z;


    @Override
    public String toString() {
        return "BlockDimPos{" +
                "dim=" + dim +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public BlockDimPos(int x, int y, int z, int dim) {
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockDimPos(int[] ai) {
        if(ai == null || ai.length < 4)
        {
            x = 0;
            y = 256;
            z = 0;
            dim = 0;
        }
        else
        {
            x = ai[0];
            y = ai[1];
            z = ai[2];
            dim = ai[3];
        }
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void addX(int x) {this.x = this.x + x;}

    public void addY(int y) {this.y = this.y + y;}

    public void addZ(int z) {this.z = this.z + z;}

    public int[] toIntArray() {
        return new int[]{(int) x, (int) y, (int) z, dim};
    }

    public BlockDimPos copy() {
        return new BlockDimPos(x,y,z,dim);
    }
}
