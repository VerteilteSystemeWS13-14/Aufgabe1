package blatt1;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public final class AsyncPrimeClient {
	private final long number;
	private BlockingThread thread;
	
	AsyncPrimeClient(long number) {
		this.number = number;
	}

	public void run() {
		ClientEndpoint endpoint = new ClientEndpoint();
		SocketAddress server = new InetSocketAddress("localhost", 4711);

		endpoint.send(server, number);
	
		System.out.print("Die Zahl " + number + " ist ");
		startThread(endpoint);
		while(true)
		{	
			waitASecond();
			if(!hasStopped())
				System.out.print(".");
			else
				break;
		}
		
		System.out.println((thread.isPrime() ? "eine " : "keine ") + "Primzahl");
	}
	
	private void waitASecond()
	{
		try
		{
			TimeUnit.SECONDS.sleep(1);
		}
		catch(Exception e)
		{
			System.err.println("#+*Absturz.");
			System.exit(1);
		}
	}
	
	private void startThread(ClientEndpoint p_endpoint)
	{
		thread = new BlockingThread();
		thread.setEndpoint(p_endpoint);
		thread.start();
	}
	
	private boolean hasStopped()
	{
		return thread.getState() == Thread.State.TERMINATED;
	}

	public static void main(String[] args) {
		for (long i = 1000000000000000000L; i < 1000000000000000010L; i++) {
			new AsyncPrimeClient(i).run();
		}
	}
}
