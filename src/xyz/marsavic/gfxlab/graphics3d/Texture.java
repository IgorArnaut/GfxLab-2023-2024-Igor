package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;

public interface Texture {

    Color colorAt(Vector uv);

    public default Material at(Vector uv)
    {
        return Material.matte(colorAt(uv));
    }

}
