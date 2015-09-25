package arima;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class test1 {

	public static void main(String args[])
	{
		for(int j=0; j<1; j++){
			Scanner ino=null;
			try {
				ArrayList<Double> arraylist=new ArrayList<Double>();//所有序列数据
				ino=new Scanner(new File("data/arima/test"));
				while(ino.hasNext())
				{
					arraylist.add(Double.parseDouble(ino.next()));
				}
				double[] dataArray=new double[arraylist.size()-1];//训练数据 
				for(int i=0;i<arraylist.size()-1;i++)
					dataArray[i]=arraylist.get(i);
				
				//System.out.println(arraylist.size());
					
				ARIMA arima=new ARIMA(dataArray); 
				
				int []model=arima.getARIMAmodel();
				System.out.println("Best model is [p,q]="+"["+model[0]+" "+model[1]+"]");
				System.out.println("Predict value="+arima.aftDeal(arima.predictValue(model[0],model[1])));
				System.out.println("Predict error="+(arima.aftDeal(arima.predictValue(model[0],model[1]))-arraylist.get(arraylist.size()-1))/arraylist.get(arraylist.size()-1)*100+"%");
				
				//System.out.println((arima.aftDeal(arima.predictValue(model[0],model[1]))-arraylist.get(arraylist.size()-1)));
				//
				//System.out.println(((arima.predictValue(model[0],model[1]))-arraylist.get(arraylist.size()-1)));
				//System.out.println(((arima.predictValue(model[0],model[1]))+"-"+arraylist.get(arraylist.size()-1)));
	
				
				
				//System.out.println(arraylist.get(arraylist.size()-1));
	
				
			//	String[] str = (String[])list1.toArray(new String[0]);	
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ino.close();
			}
		}
	}
	
	
}
