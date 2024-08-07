package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;

/** Point light. */
// Svetlo sa centrom i smerom
public record Light(Vec3 p, Color c) {

	// Obojeno svetlo
	public static Light pc(Vec3 p, Color c) {
		return new Light(p, c);
	}

	// Belo svetlo
	public static Light p(Vec3 p) {
		return pc(p, Color.WHITE);
	}
	
}
