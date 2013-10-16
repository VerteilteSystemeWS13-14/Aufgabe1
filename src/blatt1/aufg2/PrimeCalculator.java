package blatt1.aufg2;

import blatt1.ServerEndpoint;

public final class PrimeCalculator implements Runnable {

	private ServerEndpoint.Request request;
	private ServerEndpoint endpoint;
	
	public PrimeCalculator(ServerEndpoint p_endpoint, ServerEndpoint.Request p_request)
	{
		request = p_request;
		endpoint = p_endpoint;
	}
	
	@Override
	public void run()
	{
		endpoint.send(request.getSender(), calcPrime());
	}	
	
	private boolean calcPrime()
	{
		long number = request.getNumber();
		for (long i = 2; i <= Math.sqrt(number); i++)
		{
			if (number % i == 0)
			{
				return false;
			}
		}
		return true;
	}
}
