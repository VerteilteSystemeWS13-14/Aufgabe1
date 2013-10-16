package blatt1.aufg2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import blatt1.ServerEndpoint;

public final class ThreadPooledPrimeServer {
	
	private ExecutorService executor;
	private final ServerEndpoint endpoint;
	
	private static final int MAX_THREADS = 2;
	
	public ThreadPooledPrimeServer()
	{
		executor = Executors.newFixedThreadPool(MAX_THREADS);
		endpoint = new ServerEndpoint();
	}
	
	void run() {
		System.out.println("ThreadPooledPrimeServer up and running...");

		while (true)
		{
			ServerEndpoint.Request request = endpoint.blockingReceive();
			PrimeCalculator calc = new PrimeCalculator(endpoint, request);
			executor.execute(calc);
		}
	}

	public static void main(String[] args)
	{
		new ThreadPooledPrimeServer().run();
	}
		
}
