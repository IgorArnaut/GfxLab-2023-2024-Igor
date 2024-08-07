package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;

import static java.lang.Math.round;

public class Grid implements Texture {

    private final double width, height;
    private final Color color1, color2;

    private Grid(double width, double height, Color color1, Color color2)
    {
        this.width = width;
        this.height = height;
        this.color1 = color1;
        this.color2 = color2;
    }

    public static Grid create(double width, double height, Color color1, Color color2)
    {
        return new Grid(width, height, color1, color2);
    }

    public static Grid create(double k, Color color1, Color color2) { return create(k, k, color1, color2); }

    public static Grid create(double width, double height) { return create(width, height, Color.BLACK, Color.WHITE); }

    public static Grid create(double k) { return create(k, k, Color.BLACK, Color.WHITE); }

    @Override
    public Material getMaterialAt(Vector uv) {
        return Material.matte(uv.add(Vector.xy(width, height)).mod().min() < 0.01 ? color1 : color2);
    }

}
