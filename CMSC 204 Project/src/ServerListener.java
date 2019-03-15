import java.io.PrintWriter;

/**
 * Interface 
 * @author Daniel
 *
 */
public interface ServerListener {
	public void clientConncted(ClientInstance client, PrintWriter out);
	public void clientDisconnected(ClientInstance client);
	public void recivedInput(ClientInstance client, String msg);
	public void serverClosed();
}
