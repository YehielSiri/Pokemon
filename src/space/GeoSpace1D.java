package space;

/**
 * This class represents a one dimension range.
 */
public class GeoSpace1D {
	private double min, max;

	public GeoSpace1D(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public GeoSpace1D(GeoSpace1D x) {
		this(x.min, x.max);
	}

	public boolean isInside(double d) {
		return ( (d >= this.getMin()) && (d <= this.getMax()) );
	}

	public String toString() {
		String ans = "["+this.getMin()+","+this.getMax()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}

	public boolean isEmpty() {
		return this.getMin() > this.getMax();
	}

	public double getMax() {
		return max;
	}

	public double getLength() {
		return max - min;
	}

	private void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	private void setMin(double min) {
		this.min = min;
	}

	public double getPortion(double d) {
		double d1 = d-min;
		double ans = d1/getLength();
		return ans;
	}

	public double fromPortion(double p) {
		return min+p* getLength();
	}
}
