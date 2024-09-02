package xyz.marsavic.gfxlab.graphics3d.scenes;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Light;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.gfxlab.graphics3d.textures.CheckersTexture;
import xyz.marsavic.gfxlab.graphics3d.textures.GridTexture;
import xyz.marsavic.gfxlab.graphics3d.textures.ImageTexture;

import java.util.Collections;

public class SceneTest extends Scene.Base {

	public SceneTest() {
		Texture t1 = CheckersTexture.create(8);
		Texture t2 = ImageTexture.create("/Stylized_Tiles_003_basecolor.png");
		ImageTexture n2 = ImageTexture.create("/Stylized_Tiles_003_normal.png");
		Texture t3 = GridTexture.create(2);

		Ball ball = Ball.cr(Vec3.xyz(0, 0, 2), 1,
				// v -> t2.getMaterialAt(v).specular(Color.WHITE).shininess(32)
				v -> Material.matte(t2).at(v).normalMap(n2).specular(Color.WHITE).shininess(32)
		);
		HalfSpace floor = HalfSpace.pn(Vec3.xyz(0, -1, 3), Vec3.xyz(0, 1, 0),
                v -> Material.matte(t1).at(v)
		);
		
		solid = Group.of(floor, ball);
		
		Collections.addAll(lights,
			Light.pc(Vec3.xyz(-1, 1, 1), Color.WHITE),
			Light.pc(Vec3.xyz( 2, 1, 2), Color.rgb(1.0, 0.5, 0.5)),
			Light.pc(Vec3.xyz(0, 0, -1), Color.gray(0.2))
		);
	}
	
}
