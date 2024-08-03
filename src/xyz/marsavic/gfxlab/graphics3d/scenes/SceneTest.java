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
import xyz.marsavic.utils.Numeric;

import java.util.Collections;
import java.util.Objects;


public class SceneTest extends Scene.Base {

	Color getColor(javafx.scene.paint.Color c)
	{
		return Color.rgb(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public SceneTest() {
		Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("earthmap.jpg")));
		PixelReader pr = image.getPixelReader();

		for (int i = 0; i < image.getHeight(); i++)
		{
			for (int j = 0; j < image.getWidth(); j++)
			{
				double r = pr.getColor(j, i).getRed();
				double g = pr.getColor(j, i).getGreen();
				double b = pr.getColor(j, i).getBlue();
				System.out.println(r + " " + g + " " + b);
			}
		}

		Ball ball = Ball.cr(Vec3.xyz(0, 0, 2), 1,
				v -> (Material.matte(getColor(pr.getColor(v.xInt(), v.yInt())))
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
