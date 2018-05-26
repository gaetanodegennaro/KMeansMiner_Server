import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import keyboardInput.Keyboard;
import mining.KMeansMiner;
import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;


public class MainTest6 {

	/**
	 * @param args
	 */
	
	private static int menu(){
		int answer;
		System.out.println("Scegli una opzione");
		do{
			System.out.println("(1) Carica Cluster da File");
			System.out.println("(2) Carica Dati");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
		
	}
	
	static KMeansMiner learningFromFile() throws FileNotFoundException, IOException, ClassNotFoundException{
		String fileName="";
		System.out.print("Nome archivio:");
		fileName=Keyboard.readString();
		return new KMeansMiner(fileName+".dmp");
		
	}
	public static void main(String[] args) 
	{
		
		do
		{
			int menuAnswer=menu();
			switch(menuAnswer)
			{
				case 1:
				{
					try
					{
						KMeansMiner kmeans=learningFromFile();
						System.out.println(kmeans);
					}
					catch (FileNotFoundException e1) {e1.printStackTrace();}
					catch (IOException e1) {e1.printStackTrace();}
					catch (ClassNotFoundException e1) {e1.printStackTrace();}
				}
				break;
				case 2:
				{
					Data data;
					try
					{
						data =new Data("playtennis");
					}
					catch(NoValueException e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
						break;
					}
					catch(SQLException e)
					{
						System.out.println(e.getMessage());e.printStackTrace();
						break;
					}
					catch(DatabaseConnectionException e)
					{
						System.out.println(e.getMessage());e.printStackTrace();
						break;
					}
					catch(EmptySetException e)
					{
						System.out.println(e.getMessage());e.printStackTrace();
						break;
					}
					
					System.out.println(data);
					char answer='y';
					do{
						int k=1;
						System.out.print("Inserisci k:");
						k=Keyboard.readInt();
						KMeansMiner kmeans=new KMeansMiner(k);
						try
						{
							int numIter=kmeans.kmeans(data);
							System.out.println("Numero di Iterazione:"+numIter);
							System.out.println(kmeans.getC().toString(data));
							System.out.print("Nome file di backup:");
							String fileName=Keyboard.readString()+".dmp";
							System.out.println("Salvataggio in "+fileName);
							try {
								kmeans.salva(fileName);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("Fine operazioni di salvataggio!");
							
							
						}
						catch(OutOfRangeSampleSize e)
						{
							System.out.println(e.getMessage());
						}
						System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
						answer=Keyboard.readChar();
					}
					while(answer=='y');
				}
				break;
				default:
					System.out.println("Opzione non valida!");
		
			}
			
			System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
			}
		while(true);
		}
	}



