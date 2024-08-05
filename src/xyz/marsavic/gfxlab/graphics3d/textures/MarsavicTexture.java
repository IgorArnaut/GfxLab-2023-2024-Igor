package xyz.marsavic.gfxlab.graphics3d.textures;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.utils.Numeric;

public class MarsavicTexture implements Texture {

    @Override
    public Material getMaterialAt(Vector uv) {
        return Numeric.mod(uv.dot(Vector.xy(5, 4))) < 0.2 ?
                Material.matte(Color.okhcl(uv.y(), 0.125, 0.75)) :
                Material.matte(0.1);
    }

}
