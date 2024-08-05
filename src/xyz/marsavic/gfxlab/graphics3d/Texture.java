package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;

public interface Texture {

    Material getMaterialAt(Vector uv);

}
