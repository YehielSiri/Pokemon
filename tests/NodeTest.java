package Tests;

import Main.Geo_Location;
import Main.Node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Node_dataTest {

	private final Geo_Location l = new Geo_Location("5.0,2.0,0.0");
	private final Node n = new Node_data(0, l);

	private final Geo_Location l1 = new Geo_Location(9, 9, 0);
	private final Node_data n1 = new Node_data(99, l1);

	private final Geo_Location l2 = new Geo_Location(6, 6, 0);
	private final Node_data n2 = new Node_data(66, l2);

	private final Geo_Location l3 = new Geo_Location("4.0,9.0,0.0");


	@Test
	void getKey() {
		assertEquals(0, n.getKey());
		assertEquals(n1.getKey(), 99);
		assertEquals(n2.getKey(), 66);
	}

	@Test
	void getLocation() {
		assertEquals(l, n.getLocation());
		assert (l1.is_equal(new Geo_Location(9.0, 9.0, 0.0)));
		assertFalse(l1.is_equal(new Geo_Location(6.0, 9.0, 0.0)));
	}

	@Test
	void setLocation() {
		assertNotEquals(n.getLocation(), l2);
		n.setLocation(l2);
		assertEquals(l2, n.getLocation());
	}

	@Test
	void getInfo() {
		assertEquals(n1.getInfo(), "Renana");
		assertEquals(n2.getInfo(), "GRAPH");
	}

	@Test
	void setInfo() {
		n1.setInfo("Elroi");
		assertEquals(n1.getInfo(), "Elroi");
	}

	@Test
	void getTag() {
		assertEquals(n1.getTag(), 1);
		assertEquals(n2.getTag(), -1);
	}

	@Test
	void setTag() {
		n1.setTag(0);
		assertEquals(n1.getTag(), 0);
	}

	@Test
	void testToString() {
		assertEquals(n1.toString(), "(" + 99 + ")");

	}

	@Test
	void is_equals() {

	}

}