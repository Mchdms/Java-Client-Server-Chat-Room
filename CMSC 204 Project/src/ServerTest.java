import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ServerTest {
	
	private static ArrayList<Integer> list = new ArrayList<Integer>();
	
	public ServerTest(int testint) {
		list.add(testint);
	}
	
	public static void main(String args[]) {
		ServerThreadList serverThreads = new ServerThreadList();
		ServerThread test1 = new ServerThread(serverThreads);
		System.out.println(test1.connections);
		ServerThread test2 = new ServerThread(serverThreads);
		
		System.out.println(test1.connections);
		System.out.println(test2.connections);
	}
	
	static class ServerThread extends Thread implements Observer {
		
		ArrayList<ServerThread> connections = new ArrayList<ServerThread>();
		
		public ServerThread(ServerThreadList threadList) {
			threadList.addObserver(this);
			threadList.addThread(this);
			connections = threadList.threadList;
		}

		@Override
		public void update(Observable threadList, Object arg1) {
			if (threadList.getClass() == ServerThreadList.class) {
				connections = ((ServerThreadList) threadList).threadList;
			}
			System.out.println(arg1);
		}
		
	}
	
	static class ServerThreadList extends Observable {
		ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();

		public void addThread(ServerThread serverThread) {
			threadList.add(serverThread);
		}
	}	

}
