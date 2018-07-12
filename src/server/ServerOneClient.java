package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.KMeansMiner;

/**
 * Thread che serve un singolo client. Si serve di un Socket per ottenere gli InputStream e OutputStream
 * necessari per la comunicazione col client.
 * Una volta collegati gestisce le richieste inviategli dal client e risponde di conseguenza.
 * In base alle richieste del client si può:<br>
 * - Prelevare dati dal database ed eseguire l'algoritmo KMeans, fornendo i risultati al client 
 * - Salva il risultato dell'elaborazione del KMeans in un file
 * - Prelevare un ClusterSet (già costruito in precedenza) da file, specificando il nome dello stesso
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * 
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html">InputStream</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html">OutputStream</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html">Thread</a>
 *
 */
class ServerOneClient extends Thread
{
	/**
	 * Utilizzato per stabilire la connessione con un client.
	 */
	private Socket socket;
	
	/**
	 * Stream di input che permette di ricevere le richieste dal client.
	 */
	private ObjectInputStream in;
	
	/**
	 * Stream di output che permette di rispondere alle richieste del client.
	 */
	private ObjectOutputStream out;
	
	/**
	 * Utilizzato per la computazione dell'algoritmo KMeans
	 */
	private KMeansMiner kmeans;
	
	/**
	 * Inizializza il Socket con i valori di s.
	 * Ricava {@link #in} e {@link #out} da {@link #socket} dopo che sia stato inizializzato.
	 * 
	 * @param s socket che consente l'inizializzazione di {@link #socket}
	 * 
	 * @throws IOException se ci sono problemi di connessione
	 */
	public ServerOneClient(Socket s) throws IOException
	{
		this.socket = s;
		/*inizializza in e out*/
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		this.start();
	}
	
	/**
	 * Override del metodo run() della classe Thread.
	 * Una volta collegati gestisce le richieste inviategli dal client e risponde di conseguenza.
	 * In base alle richieste del client:<br>
	 * - Preleva dati dal database ed eseguire l'algoritmo KMeans, fornendo i risultati al client<br>
	 * - Salva il risultato dell'elaborazione del KMeans in un file<br> 
	 * - Preleva un ClusterSet (già costruito in precedenza) da file, specificando il nome dello stesso<br>
	 * 
	 */
	public void run() 
	{
		Data data = null;
		String tableName = "";
		int k = 0;
		try
		{
			while(true)
			{
				String operation = in.readObject().toString();
				switch(operation)
				{
					case "0" : 
					{
						tableName = in.readObject().toString();
						try
						{
							data = new Data(tableName);
						}
						catch(NoValueException ex) {out.writeObject(ex.getMessage()); break;}
						catch(SQLException ex) {out.writeObject(ex.getMessage()); break;}
						catch(DatabaseConnectionException ex) {out.writeObject(ex.getMessage()); break;}
						catch(EmptySetException ex) {out.writeObject(ex.getMessage()); break;}
						
						out.writeObject("OK");
					}
					break;
					case "1" :
					{
						k = (Integer) in.readObject();
						kmeans = new KMeansMiner(k);
						try
						{
							int numIter = kmeans.kmeans(data);
							
							this.out.writeObject("OK");
							this.out.writeObject(numIter);
							this.out.writeObject(kmeans.getC().toString(data));

						}
						catch(OutOfRangeSampleSize ex) {out.writeObject(ex.getMessage());}
					}
					break;
					case "2" :
					{
						try
						{
							kmeans.salva(tableName+"_"+k);
						}
						catch(IOException ex) {out.writeObject("Error during saveing file."); break;}
						out.writeObject("OK");
					}
					break;
					case "3" :
					{
						tableName = in.readObject().toString();
						k = (Integer) in.readObject();
						try
						{
							kmeans = new KMeansMiner(tableName+"_"+k);
						}
						catch(FileNotFoundException ex) {out.writeObject("Error file not found."); break;}
						catch(IOException ex) {out.writeObject("Error during reading file."); break;}
						out.writeObject("OK");
						out.writeObject(kmeans.getC().toString());
					}
					break;
				}
			}
		}
		catch(ClassNotFoundException e){e.printStackTrace();}
		catch(IOException e){System.out.println("A client has been disconnected.");}
	}
}
