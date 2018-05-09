import data.Data;
import keyboardInput.Keyboard;
import mining.KMeansMiner;
import data.OutOfRangeSampleSize;

public class MainTest 
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Data data =new Data();
		int k;
		char reply;
		
		do
		{
			System.out.println("+++++++++++++++++++++++++++++++++++++ K-MEANS +++++++++++++++++++++++++++++++++++++\n\n");
			System.out.println(data);
			
			System.out.print("Number of k-clusters: ");
			k = Keyboard.readInt();
			
			System.out.println("\n\n++++++++++++++++++++++++++++++++ START COMPUTATION ++++++++++++++++++++++++++++++++\n");
			
			KMeansMiner kmeans=new KMeansMiner(k);
			try
			{
				int numIter=kmeans.kmeans(data);
				System.out.println("\n\nIteration number:"+numIter);
				System.out.println(kmeans.getC().toString(data));
			}
			catch(OutOfRangeSampleSize e) {System.out.println(e.getMessage()+"\n");}
			
			System.out.println("+++++++++++++++++++++++++++++++++ END COMPUTATION +++++++++++++++++++++++++++++++++\n\n");
			
			do
			{
				System.out.print("Repeat execution?(y/n): ");
				reply = Keyboard.readChar();
			}
			while(reply!='y' && reply!='n');
			
		}
		while(reply=='y');
		
		/*
		 * IN CASO DI RIMOZIONE DI QUESTA CLASSE:
		 * 
		 * RIDEFINIRE LE VISIBILITA' PRESENTI IN Data, KMeansMiner, ClusterSet
		 * 
		 * 
		 * */
	}

}
