import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	// Check empty board.
	public void testEmptyBoard ( ) {
		Board b = new Board ( );
		assertTrue (b.isOK ( ));
		checkCollection (b, 0, 0); // applies more tests
		assertTrue (!b.formsTriangle (new Connector (1, 2), Color.RED));
	}
	
	// Check one-connector board.
	public void test1Connector ( ) {
		Board b = new Board ( );
		b.add (new Connector (1, 2), Color.RED);
		assertTrue (b.isOK ( ));
		checkCollection (b, 1, 0);
		
		Iterator<Connector> iter = b.connectors (Color.RED);
		assertTrue (iter.hasNext ( ));
		Connector cnctr = iter.next ( );
		assertEquals (b.colorOf (cnctr), Color.RED);
		assertEquals (new Connector (1, 2), cnctr);
		assertTrue (!iter.hasNext ( ));
		
		assertTrue (!b.formsTriangle (new Connector(1,3), Color.RED));
		assertTrue (!b.formsTriangle (new Connector(5,6), Color.RED));
		assertTrue (!b.choice ( ).equals (new Connector (1, 2)));
		assertEquals (b.colorOf (b.choice ( )), Color.WHITE);
	}
	
	// More tests go here.
	
	public void test2Connector() {
		Board b = new Board ( );
		b.add(new Connector(1,2), Color.RED);
		b.add(new Connector(2,3), Color.RED);
		b.add(new Connector(1,3), Color.RED);
		assertFalse(b.isOK());
		
		Board c = new Board ( );
		c.myHashMap.put(new Connector (1, 2), Color.RED);
		assertFalse(c.isOK());
		
		Board d = new Board();
		Iterator<Connector> iter = d.connectors ();
		int i = 0;
		while(iter.hasNext()) {
			iter.next();
			i++;
		}
		assertTrue(i == 15);
		
		d.add(new Connector(1,2), Color.RED);
		d.add(new Connector(3,4), Color.RED);
		d.add(new Connector(2,5), Color.RED);
		Iterator<Connector> iter2 = d.connectors (Color.RED);
		int j = 0;
		while(iter2.hasNext()) {
			iter2.next();
			j++;
		}
		assertTrue(j == 3);
		
	}
	// (a useful helper method)
	// Make the following checks on a board that should be legal:
	//	Check connector counts (# reds + # blues + # uncolored should be 16.
	//	Check red vs. blue counts.
	//	Check for duplicate connectors.
	//	Check for a blue triangle, which shouldn't exist.
	private void checkCollection (Board b, int redCount, int blueCount) {
		// Fill this in if you'd like to use this method.
		
		assertTrue(b.isOK());
		assertTrue((redCount == blueCount) || (redCount == blueCount + 1) );
	}
}
