package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;

// Izvor: https://raytracing.github.io/books/RayTracingTheNextWeek.html
public abstract class Texture {
    public Texture() {}

    abstract public Color value(Vector uv, Vec3 p);
}
