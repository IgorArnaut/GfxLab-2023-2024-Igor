package xyz.marsavic.gfxlab.graphics3d.raytracing;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.graphics3d.Camera;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Scene;


public abstract class Raytracer implements ColorFunctionT {

	// Scena
	protected final Scene scene;
	// Kamera
	private final Camera camera;
	
	// Konstruktor
	public Raytracer(Scene scene, Camera camera) {
		this.scene = scene;
		this.camera = camera;
	}
	
	// Daje boju za zrak
	protected abstract Color sample(Ray ray);
	
	// Daje boju u trenutku vremena
	@Override
	public Color at(double t, Vector p) {
		return sample(camera.exitingRay(p));
	}
	
}
