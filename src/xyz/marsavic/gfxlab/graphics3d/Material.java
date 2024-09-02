package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.textures.ImageTexture;


public record Material (
		Color diffuse,
		Color specular,
		double shininess,
		Color reflective,
		Color refractive,
		double refractiveIndex,
		Color emittance,
		Texture texture,
		ImageTexture normalMap,
		BSDF bsdf
) implements F1<Material, Vector> {

	// Osnovna boja
	public Material diffuse        (Color  diffuse        ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	// Odbijena boja
	public Material specular       (Color  specular       ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material shininess      (double shininess      ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material reflective     (Color  reflective     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material refractive     (Color  refractive     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material refractiveIndex(double refractiveIndex) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material emittance      (Color  emittance      ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material texture        (Texture texture       ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }
	public Material normalMap      (ImageTexture normalMap       ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap); }

	public Material specularCopyDiffuse() { return this.specular(diffuse()); }

	public Material(Color diffuse, Color specular, double shininess, Color reflective, Color refractive, double refractiveIndex, Color emittance, Texture texture, ImageTexture normalMap) {
		this(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance, texture, normalMap,
				BSDF.avg(
						new BSDF[] {
								BSDF.diffuse   (diffuse),
								BSDF.reflective(reflective),
								BSDF.refractive(refractive, refractiveIndex)
						},
						new double[] {
								diffuse.luminance(),
								reflective.luminance(),
								refractive.luminance()
						}
				)
		);
	}
	
	
	public Material(BSDF bsdf) {
		this(Color.BLACK, Color.BLACK, 32.0, Color.BLACK, Color.BLACK, 1.4, Color.BLACK, null, null, bsdf);
	}
	
	
	@Override
	public Material at(Vector uv) {
		if (normalMap != null)
			return matte(texture.colorAt(uv).mul(normalMap.colorAt(uv)));

		return texture.at(uv);
	}

	// --- Utility constants and factory methods ---
	// Crna boja
	public static final Material BLACK   = new Material(Color.BLACK, Color.BLACK, 32, Color.BLACK, Color.BLACK, 1.5, Color.BLACK, null, null);

	// Uniforman raspored osvetljenja u svim pravcima
	public static Material matte (Texture t) { return BLACK.texture(t); }
	public static Material matte (Color  c) { return BLACK.diffuse(c); }
	public static Material matte (double k) { return matte(Color.gray(k)); }
	public static Material matte (        ) { return matte(1.0); }
	public static final Material MATTE = matte();

	public static Material normal (ImageTexture nm) { return BLACK.normalMap(nm); }

	public static Material mirror(Color  c) { return BLACK.reflective(c); }
	public static Material mirror(double k) { return mirror(Color.gray(k)); }
	public static Material mirror(        ) { return mirror(1.0); }
	public static final Material MIRROR = mirror();

	public static Material glass (Color  c) { return BLACK.refractive(c).refractiveIndex(1.5); }
	public static Material glass (double k) { return glass(Color.gray(k)); }
	public static Material glass (        ) { return glass(1.0); }
	public static final Material GLASS = glass();

	public static Material light (Color  c) { return BLACK.emittance(c); }
	public static Material light (double k) { return light(Color.gray(k)); }
	public static Material light (        ) { return light(1.0); }
	public static final Material LIGHT = light();


	// Podrazumevani Matte materijal
	public static final Material DEFAULT = MATTE;

	public Material mul(double k) {
		return new Material(
				diffuse        .mul(k),
				specular       .mul(k),
				shininess         * k ,
				reflective     .mul(k),
				refractive     .mul(k),
				refractiveIndex   * k ,
				emittance      .mul(k),
				null,
				null,
				bsdf           .mul(k)
		);
	}
	
	public Material add(Material o) {
		return new Material(
				diffuse        .add(o.diffuse        ),
				specular       .add(o.specular       ),
				shininess         + o.shininess       ,
				reflective     .add(o.reflective     ),
				refractive     .add(o.refractive     ),
				refractiveIndex   + o.refractiveIndex ,
				emittance      .add(o.emittance      ),
				null,
				null,
				BSDF.avg(new BSDF[] {this.bsdf, o.bsdf}, new double[] {1, 1})
		);
	}
	
	public static Material lerp(Material a, Material b, double k) {
		return a.mul(1-k).add(b.mul(k));
	}
	
	
}