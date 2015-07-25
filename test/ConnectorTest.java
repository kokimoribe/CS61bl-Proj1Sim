import junit.framework.TestCase;

public class ConnectorTest extends TestCase {
	
	public void testConnector() {
		Connector myConnector = Connector.toConnector("14");
		assertEquals(myConnector, new Connector(1,4));
		Connector myConnector1 = Connector.toConnector("    23");
		assertEquals(myConnector1, new Connector(2,3));
		Connector myConnector2 = Connector.toConnector("15   ");
		assertEquals(myConnector2, new Connector(1,5));
		Connector myConnector3 = Connector.toConnector("    52    ");
		assertEquals(myConnector3, new Connector(2,5));
	}
}