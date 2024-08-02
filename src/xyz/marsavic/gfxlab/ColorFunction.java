package xyz.marsavic.gfxlab;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;

// F1 - funkcija od jednog parametra
public interface ColorFunction extends F1<Color, Vec3> {

	// Vraca boju sa vektora V u vremenu T
	default Color at(double t, Vector p) {
		return at(Vec3.xp(t, p));
	}
	
}
