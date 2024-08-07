package xyz.marsavic.gfxlab.graphics3d.solids;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Hit;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Solid;
import xyz.marsavic.utils.Numeric;


public class Cylinder implements Solid {

	// Centar
	private final Vec3 c;
	// Poluprecnik
	private final double r;

	private final boolean inverted;
	private final F1<Material, Vector> mapMaterial;

	// Kvadriran poluprecnik
	private final double rSqr;


	/** Negative r will make the ball inverted (the resulting solid is a complement of a ball). */
	// Konstruktor
	private Cylinder(Vec3 c, double r, F1<Material, Vector> mapMaterial) {
		this.c = c;
		this.r = r;
		this.mapMaterial = mapMaterial;
		rSqr = r * r;
		inverted = r < 0;
	}
	
	// Vraca loptu centra C i poluprecnika R sa materijalom
	public static Cylinder cr(Vec3 c, double r, F1<Material, Vector> mapMaterial) {
		return new Cylinder(c, r, mapMaterial);
	}

	// Vraca loptu centra C i poluprecnika R
	public static Cylinder cr(Vec3 c, double r) {
		return cr(c, r, Material.DEFAULT);
	}
	
	// Vraca centar
	public Vec3 c() {
		return c;
	}
	
	// Vraca poluprecnik
	public double r() {
		return r;
	}
	
	// Prvi susret zraka sa loptom
	@Override
	public Hit firstHit(Ray ray, double afterTime) {
		// Vektor E od centra lopte ka izvoru zraka
		Vec3 e = c().sub(ray.p());                                // Vector from the ray origin to the ball center

		// Smer zraka
		double dSqr = ray.d().lengthSquared();
		// Projekcija E na smer zraka
		double l = e.dot(ray.d()) / dSqr;
		// Odsecak na projekciji E unutar lopte
		double mSqr = l * l - (e.lengthSquared() - rSqr) / dSqr;
		
		if (mSqr > 0) {
			double m = Math.sqrt(mSqr);
			// Vreme prvog preseka
			if (l - m > afterTime) return new HitCylinder(ray, l - m);
			// Vreme drugog preseka
			if (l + m > afterTime) return new HitCylinder(ray, l + m);
		}

		return Hit.AtInfinity.axisAligned(ray.d(), inverted);
	}

	class HitCylinder extends Hit.RayT {

		protected HitCylinder(Ray ray, double t) {
			super(ray, t);
		}

		// Normala (poluprecnik) od tacke udarca
		@Override
		public Vec3 n() {
			return ray().at(t()).sub(c());
		}

		@Override
		public Material material() {
			return Cylinder.this.mapMaterial.at(uv());
		}
		
		@Override
		public Vector uv() {
			Vec3 n = n();
			return Vector.xy(
					Numeric.atan2T(n.z(), n.x()),
					4 * Numeric.asinT(n.y() / r)
			);
		}

		// Normalizovana normala (podeljena duzinom poluprecnika)
		@Override
		public Vec3 n_() {
			return n().div(r);
		}
		
	}
	
}
