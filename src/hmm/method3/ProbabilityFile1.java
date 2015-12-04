package hmm.method3;    //�Ծ���matric_status[][]������ƽ��

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class ProbabilityFile1 
{
	final static String FOLDER="5";   //�ַ��������������ı��ļ�����, ���������������
	
	static File f1=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\word_status.txt");       //ÿ���ʶ�Ӧ�Ĵ��� �ļ�
    static File f2=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_word.txt");       //ÿ�����Զ�Ӧ�Ĵ� �ļ�
    static File f3=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_status.txt");     //ÿ������ת�ƵĴ��� �ļ� 
	
	
	static File f11=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\word_status_p.txt");    //ÿ���ʶ�Ӧ�Ĵ��� �����ļ�
    static File f22=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_word_p.txt");    //ÿ�����Զ�Ӧ�Ĵ� �����ļ�
    static File f33=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_status_p.txt");  //ÿ������ת�ƵĴ��� �����ļ�   
    static File f4=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_num.txt");        //���Ա���ļ� 
    
    static HashMap<String,Integer> status_num=new HashMap<String,Integer>();    //���Ա�ţ���1��ʼ���б��
    static double matric_status[][] =new double[100][100];		//ת�Ƹ��ʾ���
    static int statusnumber=0;			 //�����ܸ���
    
    public static void main(String[] args) 
	 {
    	ProbabilityFile1 a=new ProbabilityFile1();
    	
    	long time = System.currentTimeMillis();
    	
    	a.word_max_status();
    	a.status_word_p();
    	a.status_status_p(); 
    	
    	System.out.println(System.currentTimeMillis()-time);    	//������õ�ʱ��
	 }
    
    public  void  word_max_status()   // ÿ���ʶ�Ӧ״̬,���������Ǹ�����ǰ��
    { 		
		 try
		 {  
			FileReader fin=new FileReader(f1);		   				
			BufferedReader bin=new BufferedReader(fin);			
			FileWriter fout=new FileWriter(f11);
						
			String line=null; 
			int m=0;
			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{						
					Scanner s= new Scanner(line);					
					String word=s.next();
					fout.write(word);
					fout.write(" ");					
					HashMap<String, Integer> ws=new HashMap<String, Integer>();
					while(s.hasNext())
					  {  
						  String status=s.next();
						  
						  boolean bb = ws.containsKey(status);         //�жϸô����Ƿ���HASHMAP��
					      if (bb == false) 
					      {           //�� hashmap ���ޣ������					    	  		
					    	  ws.put(status, 1);
					       }
					      else         //�� hashmap �к��иô�             
					      {			
					    	   if(ws.get(status)!=null)
					    	   {
					    			int count=ws.get(status)+1;
					    			ws.put(status,count);
					    	   }
					       }												
					   }
								
					int maxnum=0;
					double sumnum=0.0;
                    String maxstatus=null;
					  
                    Iterator<Map.Entry<String, Integer>> it0=ws.entrySet().iterator();		 
					while (it0.hasNext())
					 {						  
						Map.Entry<String, Integer> entry=it0.next();
						sumnum+=entry.getValue();
						if(maxnum<entry.getValue())
						{
						   maxnum=entry.getValue();
						   maxstatus=entry.getKey();
						 }
					  }
					  
					fout.write(maxstatus);
					fout.write(" ");
					fout.write(String.valueOf(Math.log(1.0*maxnum/sumnum)));
					
					Iterator<Map.Entry<String, Integer>> it=ws.entrySet().iterator();		 
					while (it.hasNext())
					 {
						  Map.Entry<String, Integer> entry=it.next();
						 if(entry.getKey()!=maxstatus) 
						 {
							fout.write(" ");
							fout.write(entry.getKey());
						    fout.write(" ");
						    fout.write(String.valueOf(Math.log(1.0*entry.getValue()/sumnum)));						 
						    fout.flush();	
						 }
					  }
					  fout.write("\r\n");
					  fout.flush();						
				}															
			}while(line!=null);	 			 
			fout.close(); 			
		 }
		 catch (IOException e)
        {	
		    System.out.println("IOException e");
		 }
		 System.out.println("word_max_status finish"); 
	 }
	
    public  void  status_word_p()
    { 		
		 try
		 {  
			FileReader fin=new FileReader(f2);		   				
			BufferedReader bin=new BufferedReader(fin);
			
			FileWriter fout=new FileWriter(f22);
			
			FileWriter fout1=new FileWriter(f4);
			
			String line=null; 
			int m=0;   //״̬��
			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{	
					int n=0;		//һ��״̬��Ӧ�Ĵʵ��ܸ���
					Scanner s= new Scanner(line);					
					String status=s.next();					
					
					fout1.write(status);
					fout1.write(" ");
					fout1.write(String.valueOf(++m));
					fout1.write("\r\n");
					fout1.flush();
					
					status_num.put(status, m);
					
					fout.write(status);
					fout.write(" ");					
					
					HashMap<String, Double> ws=new HashMap<String, Double>();
					while(s.hasNext())
					  {  
						  n++;
						  String word=s.next();
						  boolean bb = ws.containsKey(word);         //�жϸô��Ƿ���HASHMAP��
					      if (bb == false) 
					      {           //�� ��map ���ޣ������					    	  		
					    	  ws.put(word, 1.0);
					       }
					      else         //�� ��map �к��иô�             
					      {			
					    	   if(ws.get(word)!=null)
					    	   {
					    			double count=ws.get(word)+1;
					    			ws.put(word,count);					    			
					    	   }
					       }												
					   }					
					
					 Iterator<Map.Entry<String, Double>> it=ws.entrySet().iterator();		 
					  while (it.hasNext())
					  {
						Map.Entry<String, Double> entry=it.next();
						double word_p=entry.getValue()/n;
						
						fout.write(entry.getKey());
						fout.write(" ");
						fout.write(String.valueOf(Math.log(word_p)));
						fout.write(" ");
					  }
					  					
					  fout.write("\r\n");
					  fout.flush();		
				}						
			}while(line!=null);
			
			statusnumber=m;
			 
			fout.close(); 			
		  }
		 catch (IOException e)
         {	
		    System.out.println("IOException e");
		 }
		 System.out.println("status_word_p finish"); 
		 
	 }
    
    public  void  status_status_p()
    { 
    	for(int i=1; i<statusnumber+1; i++)  //�Ծ����ʼ��
		{
			for(int j=0; j<statusnumber+1; j++)
			{
				matric_status[i][j]=100.0;
			}
		}
    	
		 try
		 {  
			FileReader fin=new FileReader(f3);		   				
			BufferedReader bin=new BufferedReader(fin);
			
			FileWriter fout=new FileWriter(f33);
			
			String line=null; 

			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{	
					int n=0;		//һ��״̬��Ӧ��״̬���ܸ���
					Scanner s= new Scanner(line);					
					String status0=s.next();										
					
					HashMap<String, Double> ws=new HashMap<String, Double>();
					while(s.hasNext())
					  {  
						  n++;
						  String status=s.next();
						  boolean bb = ws.containsKey(status);         //�жϸô��Ƿ���HASHMAP��
					      if (bb == false) 
					      {           //�� hashmap ���ޣ������					    	  		
					    	  ws.put(status, 1.0);
					       }
					      else         //�� hashmap �к��иô�             
					      {			
					    	   if(ws.get(status)!=null)
					    	   {
					    			double count=ws.get(status)+1;
					    			ws.put(status,count);					    			
					    	   }
					       }												
					   }					
					
					 Iterator<Map.Entry<String, Double>> it=ws.entrySet().iterator();		 
					  while (it.hasNext())
					  {
						Map.Entry<String, Double> entry=it.next();
						double status_p=((entry.getValue()*5.0+1.0)/(n*5+statusnumber));
						matric_status[status_num.get(status0)][status_num.get(entry.getKey())]=status_p;						
					  }
					  
					 for(int j=1; j<statusnumber+1; j++)
					 {
						 if(matric_status[status_num.get(status0)][j]==100.0)
						 {
							 matric_status[status_num.get(status0)][j]=1.0/(n*5.0+statusnumber);
						 }
					 }					
				}						
			}while(line!=null);			
			
			for(int i=1; i<statusnumber+1; i++)
			{
				for(int j=1; j<statusnumber+1; j++)
				{
					fout.write(String.valueOf(Math.log(matric_status[i][j])));
					fout.write(" ");
				}
				fout.write(" \r\n");
				fout.flush();
			}			
			 
			fout.close(); 			
		  }
		 catch (IOException e)
         {	
		    System.out.println("IOException e");
		 }
		 System.out.println("status_status_p finish"); 		 
	 }
}
