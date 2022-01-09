package space;


import graph.GeoLocation;

/**
 * This class represents a simple world to frame conversion and vice versa.
 */
public class Range2Range {
	    private GeoSpace2D world, frame;

	    public Range2Range(GeoSpace2D w, GeoSpace2D f) {
	        world = new GeoSpace2D(w);
	        frame = new GeoSpace2D(f);
	    }
	    
	    public GeoLocation world2frame(GeoLocation p) {
	    	GeoLocation d = world.getPortion(p);
	    	GeoLocation ans = frame.fromPortion(d);
	        return ans;
	    }
	    
	    public GeoLocation frame2world(GeoLocation p) {
	    	GeoLocation d = frame.getPortion(p);
	    	GeoLocation ans = world.fromPortion(d);
	        return ans;
	    }
	    
	    public GeoSpace2D getWorld() {
	        return world;
	    }
	    
	    public GeoSpace2D getFrame() {
	        return frame;
	    }
}