package space;

import graph.GeoLocation;

/**
 * This class represents a two dimensions range.
 */
public class GeoSpace2D {
	private GeoSpace1D yRange;
	private GeoSpace1D xRange;

	public GeoSpace2D(GeoSpace1D x, GeoSpace1D y) {
		xRange = new GeoSpace1D(x);
		yRange = new GeoSpace1D(y);
	}
	
	public GeoSpace2D(GeoSpace2D w) {
		xRange = new GeoSpace1D(w.xRange);
		yRange = new GeoSpace1D(w.yRange);
	}
	
	public GeoLocation getPortion(GeoLocation p) {
		double x = xRange.getPortion(p.x());
		double y = yRange.getPortion(p.y());
		return new GeoLocation(x,y,0);
	}
	
	public GeoLocation fromPortion(GeoLocation p) {
		double x = xRange.fromPortion(p.x());
		double y = yRange.fromPortion(p.y());
		return new GeoLocation(x,y,0);
	}
}
