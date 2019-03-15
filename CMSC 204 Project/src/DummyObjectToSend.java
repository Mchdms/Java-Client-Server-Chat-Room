import java.io.Serializable;

public class DummyObjectToSend implements Serializable{

	/**
	 * Used for verifying that the object is the same after interpretation of serial stream
	 */
	private static final long serialVersionUID = 12345L;
	
	String message;
	
	public DummyObjectToSend(String m) {
		this.message = m;
	}

}
