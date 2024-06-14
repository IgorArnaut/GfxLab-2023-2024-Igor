package xyz.marsavic.gfxlab.graphics3d.solids;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Hit;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Solid;
import xyz.marsavic.utils.Numeric;


public class Ball implements Solid, Runnable {
	
	private Vec3 c;
	private final double r;
	private final boolean inverted;
	private final F1<Material, Vector> mapMaterial;

	private double x1;
	private double x2;
	
	// transient
	private final double rSqr;
	
	
	/** Negative r will make the ball inverted (the resulting solid is a complement of a ball). */
	private Ball(Vec3 c, double r, F1<Material, Vector> mapMaterial) {
		this.c = c;
		this.r = r;
		this.mapMaterial = mapMaterial;
		rSqr = r * r;
		inverted = r < 0;
	}
	
	
	public static Ball cr(Vec3 c, double r, F1<Material, Vector> mapMaterial) {
		return new Ball(c, r, mapMaterial);
	}
	
	public static Ball cr(Vec3 c, double r) {
		return cr(c, r, Material.DEFAULT);
	}
	
	
	public Vec3 c() {
		return c;
	}
	
	
	public double r() {
		return r;
	}
	
	
	
	@Override
	public Hit firstHit(Ray ray, double afterTime) {
		Vec3 e = c().sub(ray.p());                                // Vector from the ray origin to the ball center
		
		double dSqr = ray.d().lengthSquared();
		double l = e.dot(ray.d()) / dSqr;
		double mSqr = l * l - (e.lengthSquared() - rSqr) / dSqr;
		
		if (mSqr > 0) {
			double m = Math.sqrt(mSqr);
			if (l - m > afterTime) return new HitBall(ray, l - m);
			if (l + m > afterTime) return new HitBall(ray, l + m);
		}
		return Hit.AtInfinity.axisAligned(ray.d(), inverted);
	}

	private void update() {
		double time = pingPong(System.nanoTime() / 1000000.0 * 5, 1);
		x1 = c.x() - 1;
		x2 = c.x() + 1;
		c = Vec3.xyz(lerp(x1, x2, time), c.y(), c.z());
	}

	// Izvor: https://github.com/Unity-Technologies/UnityCsReference/blob/master/Runtime/Export/Math/Mathf.cs#L362
	private double repeat(double t, double length) {
		return Math.clamp(t - Math.floor(t / length) * length, 0.0f, length);
	}

	private double pingPong(double t, double length) {
		t = repeat(t, length * 2);
		return length - Math.abs(t - length);
	}

	private double lerp(double a, double b, double t) {
		return a + (b - a) * t;
	}

	@Override
	public void run() {
		update();
	}


	class HitBall extends Hit.RayT {
		
		protected HitBall(Ray ray, double t) {
			super(ray, t);
		}
		
		@Override
		public Vec3 n() {
			return ray().at(t()).sub(c());
		}
		
		@Override
		public Material material() {
			return Ball.this.mapMaterial.at(uv());
		}
		
		@Override
		public Vector uv() {
			Vec3 n = n();
			return Vector.xy(
					Numeric.atan2T(n.z(), n.x()),
					4 * Numeric.asinT(n.y() / r)
			);
		}
		
		@Override
		public Vec3 n_() {
			return n().div(r);
		}
		
	}
	
}
