package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Texture;

public class GridTexture implements Texture {

    private final double w, h;
    private final Color c1, c2;

    private GridTexture(double w, double h, Color c1, Color c2)
    {
        this.w = w;
        this.h = h;
        this.c1 = c1;
        this.c2 = c2;
    }

    public static GridTexture create(double w, double h, Color c1, Color c2)
    {
        return new GridTexture(w, h, c1, c2);
    }

    public static GridTexture create(double k, Color c1, Color c2) { return create(k, k, c1, c2); }

    public static GridTexture create(double w, double h) { return create(w, h, Color.BLACK, Color.WHITE); }

    public static GridTexture create(double k) { return create(k, k, Color.BLACK, Color.WHITE); }

    @Override
    public Color colorAt(Vector uv) {
        return uv.mul(Vector.xy(w, h)).mod().min() < 0.1 ? c1 : c2;
    }

}
