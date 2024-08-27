package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.utils.Numeric;

public class MarsavicTexture implements Texture {

    private MarsavicTexture()
    {}

    public static MarsavicTexture create()
    {
        return new MarsavicTexture();
    }

    @Override
    public Color colorAt(Vector uv) {
        return Numeric.mod(uv.dot(Vector.xy(5, 4))) < 0.2 ? Color.okhcl(uv.y(), 0.125, 0.75) : Color.gray(0.1);
    }

}
