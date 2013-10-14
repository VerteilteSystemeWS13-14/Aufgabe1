package blatt1;

import java.net.InetSocketAddress;
import java.net.SocketAddress;




public class SyncPrimeClient {
    private final long number;
    
    SyncPrimeClient(long number) {
	this.number = number;
    }
    
    public void run() {
	ClientEndpoint endpoint = new ClientEndpoint();
	SocketAddress server = new InetSocketAddress("localhost", 4711);
		
	endpoint.send(server, number);
	boolean isPrime = endpoint.blockingReceive();
	System.out.println("Die Zahl " + number + " ist " 
		+ (isPrime ? "eine " : "keine ") + "Primzahl");
    }
    
    public static void main(String[] args) {
	for ( long i = 1000000000000000000L; i < 1000000000000000010L; i++ ) {
	    new SyncPrimeClient(i).run();
	}
    }
        
    
}
