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

import static java.lang.Math.*;

public class SceneTest extends Scene.Base {

	private Color getColorNN(Vector uv, Image image, PixelReader pr) {
		// Dimenzije slike
		System.out.println("Image width: " + image.getWidth());
		System.out.println("Image height: " + image.getHeight());

		// x i y koordinata uv vektora
		System.out.println("uv.x(): " + uv.x());
		System.out.println("uv.y(): " + uv.y());

		// u i v koordinate
		double u = abs(uv.x());
		double v = abs(uv.y());

		System.out.println("u: " + u);
		System.out.println("v: " + v);

		// Tacka (i, j) na slici
		double i = round(image.getWidth() * u);
		double j = round(image.getHeight() * v);

		System.out.println("i: " + i);
		System.out.println("j: " + j);

//		System.out.println("r: " + pr.getColor(i, j).getRed());
//		System.out.println("g: " + pr.getColor(i, j).getGreen());
//		System.out.println("b: " + pr.getColor(i, j).getBlue());

		return Color.rgb(
				pr.getColor((int) i, (int) j).getRed(),
				pr.getColor((int) i, (int) j).getGreen(),
				pr.getColor((int) i, (int) j).getBlue()
		);
	}

	public SceneTest() {
		Image image = new Image(getClass().getResourceAsStream("earthmap.jpg"));
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
