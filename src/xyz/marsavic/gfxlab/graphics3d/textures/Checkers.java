package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.utils.Numeric;

import static java.lang.Math.*;

public class Checkers implements Texture {

    private final double width, height;
    private final Color color1;
    private final Color color2;

    private Checkers(double width, double height, Color color1, Color color2)
    {
        this.width = width;
        this.height = height;
        this.color1 = color1;
        this.color2 = color2;
    }

    public static Checkers create(double width, double height, Color color1, Color color2)
    {
        return new Checkers(width, height, color1, color2);
    }

    public static Checkers create(double k, Color color1, Color color2) { return create(k, k, color1, color2); }

    public static Checkers create(double width, double height) { return create(width, height, Color.BLACK, Color.WHITE); }

    public static Checkers create(double k) { return create(k, k, Color.BLACK, Color.WHITE); }

    @Override
    public Material getMaterialAt(Vector uv) {
        return round(uv.x() * 4) + uv.y() * 4 % 2.0 == 0 ?
                Material.matte(color1) :
                Material.matte(color2);
    }

}
