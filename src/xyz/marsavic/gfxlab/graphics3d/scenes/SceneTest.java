package xyz.marsavic.gfxlab.graphics3d.scenes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Light;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.Texture;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.gfxlab.graphics3d.textures.ImageTexture;

import java.util.Collections;
import java.util.Objects;

import static java.lang.Math.*;

public class SceneTest extends Scene.Base {

	public SceneTest() {
		Texture it = ImageTexture.create("/earthmap.jpg");

		Ball ball = Ball.cr(Vec3.xyz(0, 0, 2), 1,
				v -> it.getMaterialAt(v).specular(Color.WHITE).shininess(32)
		);
		HalfSpace floor = HalfSpace.pn(Vec3.xyz(0, -1, 3), Vec3.xyz(0, 1, 0),
				v -> Material.matte(v.add(Vector.xy(0.05)).mod().min() < 0.1 ? 0.5 : 1)
		);
		
		solid = Group.of(floor, ball);
		
		Collections.addAll(lights,
			Light.pc(Vec3.xyz(-1, 1, 1), Color.WHITE),
			Light.pc(Vec3.xyz( 2, 1, 2), Color.rgb(1.0, 0.5, 0.5)),
			Light.pc(Vec3.xyz(0, 0, -1), Color.gray(0.2))
		);
	}
	
}
