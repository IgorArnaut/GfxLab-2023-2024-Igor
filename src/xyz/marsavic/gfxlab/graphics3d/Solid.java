package xyz.marsavic.gfxlab.graphics3d;


public interface Solid {
	
	/**
	 * Returns the first hit of the ray into the surface of the solid, occurring strictly after the given time.
	 * If the ray misses the Solid, the hit at infinity is returned.
	 * All hits along the same line should alternate between entering and exiting hits, meaning that the sign of the
	 * dot product between the normal at the hit and the line direction should alternate. This should also hold for
	 * the hit at infinity.
	 */
	Hit firstHit(Ray ray, double afterTime);
	
	
	default Hit firstHit(Ray ray) {
		return firstHit(ray, 0);
	}
	
	
	default boolean hitBetween(Ray ray, double afterTime, double beforeTime) {
		double t = firstHit(ray).t();
		return (afterTime < t) && (t < beforeTime);
	}

}
