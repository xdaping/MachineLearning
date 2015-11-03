package aiima2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class App 
{     
    public static void main(String args[])
	{
		Scanner ino=null;
			
		try {
			/*********************************************************/
			ArrayList<Double> arraylist=new ArrayList<Double>();
			ino=new Scanner(new File("data/arima/test"));
			
			while(ino.hasNext())
			{
				arraylist.add(Double.parseDouble(ino.next()));
			}
			
			double[] dataArray=new double[arraylist.size()]; 
			
			for(int i=0;i<dataArray.length;i++)
				dataArray[i]=arraylist.get(i);
	
			
			ARIMAiFlex myarima=new ARIMAiFlex(dataArray);
			//currentAlgorithm cc=new currentAlgorithm(dataArray);
			
			/*********************************************************/
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ino.close();
		}
	}
}
