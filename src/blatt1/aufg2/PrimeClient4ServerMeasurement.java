package blatt1.aufg2;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import blatt1.ClientEndpoint;

public class PrimeClient4ServerMeasurement {
	private static volatile boolean done = false;

	private static class ProgressIndicator implements Runnable {
		@Override
		public void run() {
			while (!done) {
				System.out.print(".");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			System.out.println();
		}

	}

	private class Receiver implements Runnable {
		private final ClientEndpoint endpoint;

		Receiver(ClientEndpoint endpoint) {
			this.endpoint = endpoint;
		}

		@Override
		public void run() {
			endpoint.blockingReceive();
			endGate.countDown();
		}
	}

	private final long number;
	private final CountDownLatch endGate;

	PrimeClient4ServerMeasurement(long number, CountDownLatch endGate) {
		this.number = number;
		this.endGate = endGate;
	}

	public void run() {
		ClientEndpoint endpoint = new ClientEndpoint();
		SocketAddress server = new InetSocketAddress("localhost", 4711);
		endpoint.send(server, number);

		new Thread(new Receiver(endpoint)).start();
	}

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch endGate = new CountDownLatch(10);
		System.out.print("Messung läuft");
		new Thread(new ProgressIndicator()).start();
		long before = System.nanoTime();
		for (long i = 1000000000000000000L; i < 1000000000000000010L; i++) {
			new PrimeClient4ServerMeasurement(i, endGate).run();
		}
		endGate.await();
		long after = System.nanoTime();
		done = true;
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out
				.println("Fertig nach " + (after - before) / 1000000000 + "s");
	}
}
