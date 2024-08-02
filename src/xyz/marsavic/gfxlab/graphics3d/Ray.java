package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.gfxlab.Vec3;

// Zrak sa izvorom i smerom
public record Ray(Vec3 p, Vec3 d) {
	
	// Vraca zrak definisan sa P i D
	public static Ray pd(Vec3 p, Vec3 d) {
		return new Ray(p, d);
	}
	
	// Vraca zrak definisan izmedju 0 i 1
	public static Ray pq(Vec3 p, Vec3 q) {
		return pd(p, q.sub(p));
	}
	
	// Vraca vektor na vremenu T
	public Vec3 at(double t) {
		return p.add(d.mul(t));
	}
	
	//
	public Ray moveTo(double t) {
		return Ray.pd(at(t), d());
	}
	
	// Normalizovan zrak
	public Ray normalized_() {
		return Ray.pd(p(), d().normalized_());
	}
	
}