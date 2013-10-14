package blatt1;

public final class BlockingThread extends Thread {

	private ClientEndpoint endpoint;
	private Boolean isPrime;
	
	public void setEndpoint(final ClientEndpoint p_endpoint)
	{
		endpoint = p_endpoint;
	}
	
	public Boolean isPrime()
	{
		return isPrime;
	}
	
	@Override
	public void run()
	{
		isPrime = endpoint.blockingReceive();
	}
}
