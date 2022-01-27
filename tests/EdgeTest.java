package Tests;

import Main.Edge_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Edge_dataTest {

	private final Edge_data e1 = new Edge_data(5,9,40.5);
	private final Edge_data e2 = new Edge_data(9,14,35.5);


	@Test
	void getSrc() {
		assertEquals(e1.getSrc(),5);
		assertEquals(e2.getSrc(),9);
	}

	@Test
	void getDest() {
		assertEquals(e1.getDest(),9);
		assertEquals(e2.getDest(),14);
	}

	@Test
	void getWeight() {
		assertEquals(e1.getWeight(),40.5);
		assertEquals(e2.getWeight(),35.5);
	}

	@Test
	void getInfo() {
		assertEquals(e1.getInfo(),"Renana");
		assertEquals(e2.getInfo(),"GRAPH");
	}

	@Test
	void setInfo() {
		e1.setInfo("Elroi");
		assertEquals(e1.getInfo(),"Elroi");
	}

	@Test
	void getTag() {
		assertEquals(e1.getTag(),0);
		assertEquals(e2.getTag(),-1);
	}

}