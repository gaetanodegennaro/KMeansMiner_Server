package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Stabilisce connessioni multiple a più client. Utilizza la classe ServerSocket per ascoltare
 * sulla porta (default=8080) le varie richieste di connessione. Accetta le richieste mediante 
 * il metodo ServerSocket.accept() e passa l'oggetto Socket corrispondente a un ServerOneClient
 * che estende {@link Thread} e che gestisce il servizio. Se ServerOneClient solleva una IOException
 * chiude il socket corrispondente. 
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * 
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html">ServerSocket</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html">Socket</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html#accept()">ServerSocket.accept()</a>
 * @see ServerOneClient
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html">Thread</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">IOException</a>
 */
class MultiServer
{
	/**
	 * Porta utilizzata da ServerSocket per l'ascolto di connessioni in entrata.
	 */
	private final int port;
	
	/**
	 * Inizializza {@link #port} con il parametro passato al costruttore.
	 * Invoca il metodo {@link #run()}
	 * 
	 * @param port numero di porta
	 */
	public MultiServer(int port)
	{
		this.port = port;
		this.run();
	}
	
	/**
	 * Utilizza la classe ServerSocket per ascoltare
	 * sulla porta (default=8080) le varie richieste di connessione. Accetta le richieste mediante 
	 * il metodo ServerSocket.accept() e passa l'oggetto Socket corrispondente a un ServerOneClient 
	 * che estende Thread e che gestisce il servizio. Se ServerOneClient solleva una IOException
	 * chiude il socket corrispondente. 
	 * 
	 * 
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html">ServerSocket</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html">Socket</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html#accept()">ServerSocket.accept()</a>
	 * @see ServerOneClient
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html">Thread</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">IOException</a>
	 * 
	 */
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(port);
			while(true)
			{
				try
				{
					Socket s = serverSocket.accept();
					new ServerOneClient(s);
				}
				catch(IOException e) {e.printStackTrace();}
			}
		}
		catch(BindException e) {System.out.println("Another KMeans_Server insance is already running.");}
		catch(IOException e) {e.printStackTrace();}
		finally
		{
			try
			{
				/*if a bindException occurs serverSocket value is null*/
				if(serverSocket!=null) serverSocket.close();
			}
			catch(IOException e) {System.out.println("Error during closing server.");}
		}
	}
	
	public static void main(String args[])
	{
		new MultiServer(8080);
	}
}
