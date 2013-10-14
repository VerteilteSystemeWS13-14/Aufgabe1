package blatt1;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public final class PollingPrimeClient {
	private final long number;

	PollingPrimeClient(long number) {
		this.number = number;
	}

	public void run() {
		ClientEndpoint endpoint = new ClientEndpoint();
		SocketAddress server = new InetSocketAddress("localhost", 4711);

		endpoint.send(server, number);
	
		Boolean isPrime = null;
		System.out.print("Die Zahl " + number + " ist ");
		while(true)
		{
			isPrime = endpoint.nonBlockingReceive();
			if(isPrime != null)
				break;
				
			System.out.print(".");
			waitASecond();
		}
		
		System.out.println((isPrime ? "eine " : "keine ") + "Primzahl");
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

	public static void main(String[] args) {
		for (long i = 1000000000000000000L; i < 1000000000000000010L; i++) {
			new PollingPrimeClient(i).run();
		}
	}
}
