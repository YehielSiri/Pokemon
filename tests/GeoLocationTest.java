package Tests;

import Main.Geo_Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Geo_LocationTest {

	private final Geo_Location l = new Geo_Location("5.0,2.0,0.0");
	private final Geo_Location l1 = new Geo_Location(9, 9, 0);
	private final Geo_Location l2 = new Geo_Location(6, 6, 0);
	private final Geo_Location l3 = new Geo_Location("3.0,2.0,0.0");

	@Test
	void x() {
		assertEquals(5.0, l.x());
		assertEquals(l1.x(), 9);
		assertEquals(l2.x(), 6);
	}

	@Test
	void y() {
		assertEquals(2.0, l.y());
		assertEquals(l1.y(), 9);
		assertEquals(l2.y(), 6);
	}

	@Test
	void z() {
		assertEquals(0.0, l.z());
		assertEquals(l1.z(), 0);
		assertEquals(l2.z(), 0);
	}

	@Test
	void testToString() {
		assertEquals(l1.toString(), "{" +
				"x=" + 9.0 +
				", y=" + 9.0 +
				", z=" + 0.0 +
				'}');
	}


	@Test
	void distance() {
		assertEquals(2, l.distance(l3));
		assertEquals(l1.distance(l1), 0);
		assertEquals(l2.distance(l2), 0);
	}
}