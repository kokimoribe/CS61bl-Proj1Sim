public class Connector {
	
	// Implement an immutable connector that connects two points on the game board.
	// Invariant: 1 <= myPoint1 < myPoint2 <= 6.
	
	private int myPoint1, myPoint2;
	
	public Connector (int p1, int p2) {
		if (p1 < p2) {
			myPoint1 = p1;
			myPoint2 = p2;
		} else {
			myPoint1 = p2;
			myPoint2 = p1;
		}
	}
	
	public int endPt1 ( ) {
		return myPoint1;
	}
	
	public int endPt2 ( ) {
		return myPoint2;
	}
	
	public boolean equals (Object obj) {
		Connector e = (Connector) obj;
		return (e.myPoint1 == myPoint1 && e.myPoint2 == myPoint2);
	}

	public String toString ( ) {
		return "" + myPoint1 + myPoint2;
	}
	
	// Format of a connector is endPoint1 + endPoint2 (+ means string concatenation),
	// possibly surrounded by white space. Each endpoint is a digit between
	// 1 and 6, inclusive; moreover, the endpoints aren't identical.
	// If the contents of the given string is correctly formatted,
	// return the corresponding connector.  Otherwise, throw IllegalFormatException.
	public static Connector toConnector (String s) throws IllegalFormatException {
		
		//trim will return the string without the white spaces surrounding the digits if there are any
		//then turn that trimmed string to a char array
		char[] myChars = s.trim().toCharArray();
		
		//with whitespaces removed, character length should be exactly 2
		if (myChars.length != 2) {
			//if not then throw exception
			throw new IllegalFormatException("Enter exactly 2 digits.");
		}
		
		//int array to store the two digits
		int[] points = new int[2];
		char c;
		
		//turn each char in the chararray into an int
		for (int i = 0; i < myChars.length; i++) {
			c = myChars[i];
			
			//c should be a digit from 1 to 6
			if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6') {
				//if it is, get the numerical value and store it as an int value
				points[i] = Character.getNumericValue(c);
			}
			
			else {
				//if not, then throw exception
				throw new IllegalFormatException("Digit must be from 1 to 6.");
			}
		}
		
		//make sure the two digits are not the same
		if (points[0] == points[1]) {
			//if they are, throw exception
			throw new IllegalFormatException("Digits cannot be identical.");
		}
		
		//return connector with points being the two digit input
		return new Connector(points[0], points[1]);
	}
}
