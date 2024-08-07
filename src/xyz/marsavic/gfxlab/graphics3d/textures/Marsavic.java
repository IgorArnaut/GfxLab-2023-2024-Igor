package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.utils.Numeric;

public class Marsavic implements Texture {

    private Marsavic()
    {}

    public static Marsavic create()
    {
        return new Marsavic();
    }

    @Override
    public Material getMaterialAt(Vector uv) {
        Vector uv2 = uv.mul(2);

        return Numeric.mod(uv2.x() + uv2.y()) == 0 ?
                Material.matte(1) :
                Material.matte(0);
    }

}
