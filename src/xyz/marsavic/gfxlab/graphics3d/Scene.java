package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.gfxlab.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public interface Scene {

	// Solidi
	Solid solid();
	
	Collection<Light> lights();

	// Pozadinska boja
	default Color colorBackground() {
		return Color.BLACK;
	}
	
	class Base implements Scene {
		
		protected Solid solid;
		protected final List<Light> lights = new ArrayList<>();
		protected Color colorBackground = Color.BLACK;
		
		@Override
		public Solid solid() {
			return solid;
		}
		
		@Override
		public Collection<Light> lights() {
			return lights;
		}
		
		@Override
		public Color colorBackground() {
			return colorBackground;
		}

	}
	
}
