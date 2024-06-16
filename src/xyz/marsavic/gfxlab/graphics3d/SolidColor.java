package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;

// Izvor: https://raytracing.github.io/books/RayTracingTheNextWeek.html
public class SolidColor extends Texture {

    private Color diffuse;

    public SolidColor(Color diffuse) {
        this.diffuse = diffuse;
    }

    public SolidColor(double red, double green, double blue) {
        this(Color.rgb(red, green, blue));
    }

    @Override
    public Color value(Vector uv, Vec3 p) {
        return diffuse;
    }
}
