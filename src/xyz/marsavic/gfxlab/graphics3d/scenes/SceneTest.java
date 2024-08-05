package xyz.marsavic.gfxlab.graphics3d.scenes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Light;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;

import java.util.Collections;
import java.util.Objects;

import static java.lang.Math.*;

public class SceneTest extends Scene.Base {

	private Color getColorNN(Vector uv, Image image, PixelReader pr) {
		// Dimenzije slike
		double width = image.getWidth();
		double height = image.getHeight();

		// Koordinate u i v u novom opsegu
		Vector uv2 = Vector.xy((uv.x() + 1) / 2, (uv.y() + 1) / 2);

		// Koordinate i i j
		Vector ij = uv2.mul(Vector.xy(width, height));
		ij = ij.round();
		// Pomeranje i i j
		ij = ij.add(Vector.xy(0 * ij.x(), 0 * ij.y()));
		ij = ij.mod(Vector.xy(width, height));
		// Flipovanje j
		ij = ij.withY(height - ij.y() - 1);

		return Color.rgb(
				pr.getColor(ij.xInt(), ij.yInt()).getRed(),
				pr.getColor(ij.xInt(), ij.yInt()).getGreen(),
				pr.getColor(ij.xInt(), ij.yInt()).getBlue()
		);
	}

	public SceneTest() {
		Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("earthmap.jpg")));
		PixelReader pr = image.getPixelReader();

		Ball ball = Ball.cr(Vec3.xyz(0, 0, 2), 1,
				v -> (/*Numeric.mod(v.dot(Vector.xy(5, 4))) < 0.2 ?
											Material.matte(getColorNN(v, image)) :
											Material.matte(0.1) */
											Material.matte(getColorNN(v, image, pr))
				).specular(Color.WHITE).shininess(32)
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
