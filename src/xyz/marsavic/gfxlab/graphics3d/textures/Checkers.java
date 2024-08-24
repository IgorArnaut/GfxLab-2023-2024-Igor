package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.utils.Numeric;

import static java.lang.Math.*;

public class Checkers implements Texture {

    private final double w, h;
    private final Color c1;
    private final Color c2;

    private Checkers(double w, double h, Color c1, Color c2)
    {
        this.w = w;
        this.h = h;
        this.c1 = c1;
        this.c2 = c2;
    }

    public static Checkers create(double w, double h, Color c1, Color c2)
    {
        return new Checkers(w, h, c1, c2);
    }

    public static Checkers create(double k, Color c1, Color c2) { return create(k, k, c1, c2); }

    public static Checkers create(double w, double h) { return create(w, h, Color.BLACK, Color.WHITE); }

    public static Checkers create(double k) { return create(k, k, Color.BLACK, Color.WHITE); }

    @Override
    public Material at(Vector uv) {
        Vector uv2 = uv.mul(Vector.xy(w, h)).floor();
        return floor(uv2.x() + uv2.y()) % 2.0 == 0 ?
                Material.matte(c1) :
                Material.matte(c2); // [1]
    }

}
// [1] http://www.raytracerchallenge.com/bonus/texture-mapping.html