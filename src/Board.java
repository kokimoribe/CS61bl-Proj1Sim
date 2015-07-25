import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	
	// Initialize an empty board with no colored edges.
	
	//myHashMap: keys will be assigned connectors, values will be assigned colors
	//every key(connector) has its own value(color)
	HashMap<Connector, Color> myHashMap = new HashMap<Connector, Color>();
	
	public Board ( ) {
		// You fill this in.
		
		//make hashmap with all 15 possible connectors as the keys
		//assign every connector's color as white
		for (int i = 1; i < 7; i++) {
			for (int j = i + 1; j < 7; j++) {
				//double for loop to get digits i,j that fills all possible 15 connector endpoints
				//12,13,14,15,16,23,24,25,26,34, etc...
				this.myHashMap.put(new Connector(i, j), Color.WHITE);
			}
		}
		
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
		// You fill this in.
		
		//can't simply do myHashMap.put(cnctr, c) 
		//b/c cnctr is a newly created Connector that points
		//to a different Connector object than the one in myHashMap.keySet()
		
		//Instead, iterate through the Connectors in myHashMap,
		//and use the equals method to correctly find the
		//right Connector.
		for (Connector e : myHashMap.keySet()) {
			if (e.equals(cnctr)) {
				myHashMap.put(e, c);
				break;
			}
		}
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors (Color c) {
		// You fill this in.
		
		//since we can't iterate and change the same hashmap at the same time, (concurrent modification)
		//make a duplicate board
		HashMap<Connector, Color> dupBoard = new HashMap(myHashMap);
		
		//iterate through all the connectors in the original hashmap
		//remove any connectors that aren't color c in the duplicate
		for (Connector cnct : myHashMap.keySet()) {
			if (!colorOf(cnct).equals(c)) {
				dupBoard.remove(cnct);
			}
		}
		
		//return the iterator of the connectors
		return dupBoard.keySet().iterator();
	}
	
	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors ( ) {
		// You fill this in.
		return myHashMap.keySet().iterator();
	}
	
	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		// You fill this in.
		
		//NOTE: the solution:
		//return myHashMap.get(e);
		//does not work.
		//Connector e points to a different object than
		//the Connector object with same endpoints in myHashMap
		//the API of get method for myHashMap says it uses the equals method of the argument passed into get.
		//However, the get method does not properly function as described in the API for Connector objects.
		//The get method does work properly for String objects however.
		
		//Since for some reason the get method does not work as expected,
		//iterate through the set of keys of myHashMap,
		//find the right Connector that has same endpoints as Connector e,
		//then pass the Connector from the key set as the argument to the get method
		for (Connector cnct : myHashMap.keySet()) {
			if (e.equals(cnct)) {
				return myHashMap.get(cnct);
			}
		}
		
		//code should never reach here if given a legal argument
		return null;
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Connector cnctr, Color c) {
		// You fill this in.
		int f1 = cnctr.endPt1(); //the original two points (the first connector)
		int f2 = cnctr.endPt2();
		int s1; //the second connector's points
		int s2;
		int t1; //the third connector's points
		int t2;
		
		
		//iterate through all connectors with color c
		Iterator<Connector> iter = this.connectors(c);
		
		while(iter.hasNext()) {
			//get one Connector of color c, and find its points
			Connector e = iter.next();
			s1 = e.endPt1();
			s2 = e.endPt2();
			
			//see if second connector shares a point with first connector
			//there are four cases when this is true
			if (f1 == s1 || f1 == s2 || f2 == s1 || f2 == s2) {
				
				//if a second connector shares a point with first connector,
				//the third connector's points can be determined.
				//there are four possible sets of endpoints, dependent on which points are shared by the first and second connectors.
				if (f1 == s1) {
					t1 = f2;
					t2 = s2;
				}
				
				else if (f2 == s1) {
					t1 = f1;
					t2 = s2;
				}
				
				else if (f1 == s2) {
					t1 = f2;
					t2 = s1;
				}
				
				//(f2 == s2)
				else {
					t1 = f1;
					t2 = s1;
				}
				
				//make third connector with the known endpoints
				Connector tc = new Connector (t1, t2);
				
				//see if a third connector with these endpoints of Color c exists
				Iterator<Connector> checkIter = this.connectors(c);
				while (checkIter.hasNext()) {
					if (tc.equals(checkIter.next())) {
						return true;
					}
				}
			}
			//does not form triangle if code reaches here
			//get a new 2nd connector
		}
		
		//does not form triangle after going through all possible 2nd connectors of color c
		//return false
		return false;
	}
	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	public Connector choice ( ) {
		// You fill this in.
		
		//iterate through all white connectors i.e. all possible remaining move
		Iterator<Connector> iter = this.connectors(Color.WHITE);
		Connector myConnector = null;
		
		ArrayList<Connector> possibleMoves = new ArrayList<Connector>();
		
		while (iter.hasNext()) {
			myConnector = iter.next();
			//if the possible move in question does not form a blue triangle, then add to possible moves
			//otherwise, keep iterating through
			if (!formsTriangle(myConnector, Color.BLUE)) {
				possibleMoves.add(myConnector);
			}
		}
		
		//if all possible moves form a blue triangle, return the last connector that it checked
		if (possibleMoves.isEmpty()) {
			return myConnector;
		}
		
		//if possible move is a losing move for the opponent, then avoid making that move
		ArrayList<Connector> betterMoves = new ArrayList<Connector>();
		for (Connector e : possibleMoves) {
			myConnector = e;
			if (!formsTriangle(e, Color.RED)) {
				betterMoves.add(e);
			}
		}
		
		//if all possible moves end up as losing moves for the opponent, return the last connector that it checked
		if (betterMoves.isEmpty()) {
			return myConnector;
		}
		
		//out of all the better moves, choose a connector that won't share points with another connector of same color
		for (Connector e : betterMoves) {
			myConnector = e;
			int f1 = e.endPt1();
			int f2 = e.endPt2();
			int s1;
			int s2;
			
			//check if connector e will share points with a connector that is blue
			Iterator<Connector> check = this.connectors(Color.BLUE);			
			while(check.hasNext()) {
				Connector r = check.next();
				s1 = r.endPt1();
				s2 = r.endPt2();

				if (!(f1 == s1 || f1 == s2 || f2 == s1 || f2 == s2)) {
					return myConnector;
				}
			}
		}
		
		//if all better moves ends up sharing a point with a blue connector, return the last connector it checked
		return myConnector;
	}

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	//	Each connector in the board is properly initialized so that 
	// 	1 <= myPoint1 < myPoint2 <= 6.
	public boolean isOK ( ) {
		// You fill this in.
		

		//size should be 15
		if (myHashMap.size() != 15) {
			return false;
		}
		
		Iterator<Connector> iter = this.connectors();
		while(iter.hasNext()) {
			Connector e = iter.next();
			Color c = colorOf(e);
			
			//connector should be red, blue, or white
			if (c.equals(Color.RED) || c.equals(Color.BLUE)) {
				
				//a colored triangle should not exist on board
				if (formsTriangle(e, c)) {
					return false;
				}
			}			
			else if (!c.equals(Color.WHITE)) {
				return false;
			}
		}
		
		return true;
	}
}
