package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.textures.ImageTexture;


/** Interaction of a ray with a solid.*/
public interface Hit {
	
	/** The time of the hit. */
	double t();
	
	/** The normal at the hit point. */
	Vec3 n();
	
	/** Surface material at the hit point. */
	// Materijal
	Material material();
	
	/** 2D coordinates in the internal coordinate system of the surface. */
	// UV koordinate za materijal
	Vector uv();

	Vec3 mapN();
	
	/** The normalized normal at the point of the hit */
	default Vec3 n_() {
		return n().normalized_();
	}

	default Hit withN(Vec3 n) {
		return new Hit() {
			@Override public double   t       () { return Hit.this.t(); }
			@Override public Vec3     n       () { return n; }
			@Override public Material material() { return Hit.this.material(); }
			@Override public Vector   uv      () { return Hit.this.uv(); }
			@Override public Vec3     mapN    () { return Hit.this.mapN(); }
		};
	}
	
	default Hit withMaterial(Material material) {
		return new Hit() {
			@Override public double   t       () { return Hit.this.t(); }
			@Override public Vec3     n       () { return Hit.this.n(); }
			@Override public Vec3     n_      () { return Hit.this.n_(); }
			@Override public Material material() { return material; }
			@Override public Vector   uv      () { return Hit.this.uv(); }
			@Override public Vec3     mapN    () { return Hit.this.mapN(); }
		};
	}
	
	default Hit inverted() {
		return new Hit() {
			@Override public double   t       () { return Hit.this.t (); }
			@Override public Vec3     n       () { return Hit.this.n ().inverse(); }
			@Override public Vec3     n_      () { return Hit.this.n_().inverse(); }
			@Override public Vector   uv      () { return Hit.this.uv(); }
			@Override public Material material() { return Hit.this.material(); }
			@Override public Vec3     mapN    () { return Hit.this.mapN().inverse(); }
		};
	}
	
	// =====================================================================================================
	
	// Staticka klasa
	abstract class RayT implements Hit {

		// Zrak
		private final Ray ray;
		// Vreme
		private final double t;
		
		protected RayT(Ray ray, double t) {
			this.ray = ray;
			this.t = t;
		}
		
		public Ray ray() {
			return ray;
		}
		
		@Override
		public double t() {
			return t;
		}

	}
	
	// Vracanje zraka van tela
	record AtInfinity(
			double t,
			Vec3 n
	) implements Hit {
		
		@Override public double   t       () { return t; }
		@Override public Vec3     n       () { return n; }
		@Override public Vector   uv      () { return Vector.ZERO; }
		@Override public Material material() { return Material.BLACK; }
		@Override public Vec3     mapN    () { return n; }
		
		
		public static AtInfinity inLine(Vec3 d, boolean future, boolean goingOut) {
			// We don't like calling this often, because it can create a new object. In frequently executed code, call one of the "axisAligned" methods.
			return new AtInfinity(
					future ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY,
					goingOut == future ? d : d.inverse()
			);
		}
		
		
		private static final AtInfinity hitAtInfinityXM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz(-1, 0, 0));
		private static final AtInfinity hitAtInfinityXP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 1, 0, 0));
		private static final AtInfinity hitAtInfinityYM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0,-1, 0));
		private static final AtInfinity hitAtInfinityYP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 1, 0));
		private static final AtInfinity hitAtInfinityZM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 0,-1));
		private static final AtInfinity hitAtInfinityZP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 0, 1));
		
		public static AtInfinity axisAligned(Vec3 d, boolean fromInside) {
			return fromInside ? axisAlignedGoingOut(d) : axisAlignedGoingIn(d);
		}
		
		public static AtInfinity axisAlignedGoingOut(Vec3 d) {
			if (d.x() < 0) return hitAtInfinityXM;
			if (d.x() > 0) return hitAtInfinityXP;
			if (d.y() < 0) return hitAtInfinityYM;
			if (d.y() > 0) return hitAtInfinityYP;
			if (d.z() < 0) return hitAtInfinityZM;
			return hitAtInfinityZP;
		}
		
		public static AtInfinity axisAlignedGoingIn(Vec3 d) {
			if (d.x() < 0) return hitAtInfinityXP;
			if (d.x() > 0) return hitAtInfinityXM;
			if (d.y() < 0) return hitAtInfinityYP;
			if (d.y() > 0) return hitAtInfinityYM;
			if (d.z() < 0) return hitAtInfinityZP;
			return hitAtInfinityZM;
		}
		
	}

	default Vec3 newNormal(Vec3 t_, Vec3 b_) {
		ImageTexture normalMap = material().normalMap();

		if (normalMap != null) {
			// Boja mape normala u uv tacki
			Color c = material().normalMap().colorAt(uv());
			c = c.mul(2).sub(Color.rgb(1, 1, 1));

			/* TBN matrica
			   [tx bx nx] * [cx]
			   [ty by ny]   [cy]
			   [tz bz nz]   [cz]
			 */
			double x = t_.x() * c.r() + b_.x() * c.g() + n_().x() * c.b();
			double y = t_.y() * c.r() + b_.y() * c.g() + n_().y() * c.b();
			double z = t_.z() * c.r() + b_.z() * c.g() + n_().z() * c.b();
			// [2]

			return Vec3.xyz(x, y, z).normalized_();
		}

		return n_();
	}
	
}
// [2] https://learnopengl.com/Advanced-Lighting/Normal-Mapping