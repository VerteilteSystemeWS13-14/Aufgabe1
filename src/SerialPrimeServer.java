package blatt1;



public class SerialPrimeServer {
    private final ServerEndpoint endpoint;

    SerialPrimeServer() {
	endpoint = new ServerEndpoint();
    }

    private boolean isPrime(long number) {
	for (long i = 2; i <= Math.sqrt(number); i++) {
	    if (number % i == 0) {
		return false;
	    }
	}
	return true;
    }

    void run() {
	System.out.println("Serial PrimeServer up and running...");

	while (true) {
	    ServerEndpoint.Request request = endpoint.blockingReceive();
	    boolean prime = isPrime(request.getNumber());
	    endpoint.send(request.getSender(), prime);
	}
    }

    public static void main(String[] args) {
	new SerialPrimeServer().run();
    }

}
