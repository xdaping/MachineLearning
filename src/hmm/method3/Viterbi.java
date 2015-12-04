package hmm.method3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Viterbi 
{	
	final static String FOLDER="5";	 //�ַ��������������ı��ļ�����, ���������������
	
	static File f2=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_num.txt");   	 //���Ա���ļ� 
	static File f3=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_status_p.txt");  //ÿ������ת�ƵĴ��� �����ļ�    
	static File f4=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_word_p.txt");    //ÿ�����Զ�Ӧ�Ĵ� �����ļ�
	static File f5=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\word_status_p.txt");    //ÿ���ʶ�Ӧ�Ĵ��� �����ļ� 	  
	
	static HashMap<String,Integer> status_num=new HashMap<String,Integer>();//
	static double matrix_status[][] =new double[131][131];	
	static HashMap<String,HashMap<String,Double>> status_word_p=new HashMap<String,HashMap<String,Double>>();//	
	static HashMap<String,ArrayList<String>> word_status=new HashMap<String,ArrayList<String>>();//
    
    private static ArrayList<String> filelist = new ArrayList<String>();  
    static int statusnumber=0;
    
    public static void main(String[] args) 
	 {   	
		 long a = System.currentTimeMillis();
	                      		        
		 Viterbi q=new Viterbi();	
		 q.statusnumber(); 	     //f2  ��״̬���
		 q.initializematrix();   //    ��ʼ��status_status����
		 q.statusstatusp();		 //f3  ��status_status����ֵ
		 q.statuswordp();        //f4  ��status_word_p��ֵ
		 q.wordstatus();         //f5  ��word_status��ֵ	
		 
		 File dir=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\prediction_test");  //��E���´���һ���յ�Ŀ¼
         if(!dir.exists())
         {
       	  dir.mkdir();
         }
		 
	     q.refreshFileList("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\initialization_test");     
	     	    	 	     		       
	     System.out.println(System.currentTimeMillis()-a);
	  }         		   
	 
	 public  void  statusnumber()  //��״̬���
	    { 						 
			 try
			 {  
				FileReader fin=new FileReader(f2);		   				
				BufferedReader bin=new BufferedReader(fin);
				String line=null; 
				do
				{   			
					line=bin.readLine();				
					if(line!=null)
					{							
						Scanner s= new Scanner(line);
						String word=s.next();
						int num=Integer.parseInt(s.next());
						status_num.put(word, num);	
						
						statusnumber=num;
					}																			
				}while(line!=null);					
			 }
			 catch (IOException e)
	        {	
			    System.out.println("IOException e");
			 }			 			 
	   }
	 
	 public  void  initializematrix()   //��ʼ��status_status����
	   { 	
		 for(int i=0; i<statusnumber+1; i++)
			 for(int j=0; j<statusnumber+1; j++)
				 matrix_status[i][j]=100.0;
	   }	 
	 
	 public  void  statusstatusp()   //��status_status����ֵ
	    { 						 
			 try
			 {  
				FileReader fin=new FileReader(f3);		   				
				BufferedReader bin=new BufferedReader(fin);
				
				String line=null; 				
				int m=0;  //��ʾ��
				
				do
				{   			
					line=bin.readLine();				
					if(line!=null)
					{	++m;
					    int n=0;  //��ʾ��
						Scanner s= new Scanner(line);
						while(s.hasNext())
						  {
						     matrix_status[m][++n]=s.nextDouble();						     
						  }
					}																			
				}while(line!=null);					
			 }
			 catch (IOException e)
	        {	
			    System.out.println("IOException statusstatusp");
			 }		 
	   }	 
	 
	 public  void  statuswordp()   //��status_word_p��ֵ
	    { 						 
			 try
			 {  
				FileReader fin=new FileReader(f4);		   				
				BufferedReader bin=new BufferedReader(fin);
				String line=null; 								
				do
				{   			
					line=bin.readLine();				
					if(line!=null)
					{						
						Scanner s= new Scanner(line);						
						
						String status=s.next();
						HashMap<String,Double> word_p=new HashMap<String,Double>();//
						while(s.hasNext())
						  { 
							  word_p.put(s.next(), s.nextDouble());						     
						  }
						
						status_word_p.put(status, word_p);
					}																			
				}while(line!=null);					
			 }
			 catch (IOException e)
	        {	
			    System.out.println("IOException statuswordp");
			 }		 
	   }
	 
	 public  void  wordstatus()   //��word_status��ֵ
	    { 						 
			 try
			 {  
				FileReader fin=new FileReader(f5);		   				
				BufferedReader bin=new BufferedReader(fin);
				String line=null; 
								
				do
				{   			
					line=bin.readLine();				
					if(line!=null)
					{						
						Scanner s= new Scanner(line);						
						String word=s.next();																		
						ArrayList<String> status=new ArrayList<String>();//
						while(s.hasNext())
						  { 
							//status.add(s.next());	
							status.add(String.valueOf(s.next()));
						  }						
						word_status.put(word, status);
					}																			
				}while(line!=null);					
			 }
			 catch (IOException e)
	        {	
			    System.out.println("IOException e");
			 }		 
	   }
	 
	 public  void refreshFileList(String strPath)
     { 		   
			File dir = new File(strPath); 
	        File[] files = dir.listFiles();        
	        if (files == null) 
	            return; 
	        for (int i = 0; i < files.length; i++)
	        { 
	            if (files[i].isDirectory()) 
	            { 
	                refreshFileList(files[i].getAbsolutePath()); 
	            } 
	           else 
	            { 
	                String strFileName = files[i].getAbsolutePath().toLowerCase();
	                System.out.println("---"+strFileName);                            
	                Viterbi b=new Viterbi();				
	       	        b.hadle(files[i]) ;    	        
	                          
	               filelist.add(files[i].getAbsolutePath());                    	            
	            } 
	        } 
	    }
	 
	
	 public  void  hadle(File f1)
    { 
		File f2=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\prediction_test\\21009256_"+f1.getName());
		 
		 System.out.println(f1.getName());		 
		 try
		 {  
			FileReader fin=new FileReader(f1);		   				
			BufferedReader bin=new BufferedReader(fin);
				
			String line=null; 
            
			int hang=0;   //�к�
			
			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{	
					hang++;
					Viterbi c=new Viterbi();
					c.computerstatus(f2, line,hang);							
				}					
			 } while(line!=null);	 			 
		  }		 
		  catch (FileNotFoundException ex)
		    {		
			  System.out.println("f1 File not Found");
			}  
	        catch (IOException ex1)
		    {	    }		 
	 }
	 
	 public void computerstatus(File f, String line, int hang)
	 {	
		 try
		 {
			   FileWriter fout=new FileWriter(f,true);
			 
			    ArrayList<String> wordlist=new ArrayList<String>();// ������line�еĴʽ��б��
				ArrayList<String> endlist=new ArrayList<String>();// line�еĴ���õĴ���
				
				ArrayList<HashMap<String,Double>>  word_property=new ArrayList<HashMap<String,Double>>(); //������line�е�ÿ���ʱ������п��ܵĴ���
				
				ArrayList<HashMap<String,String>>  status_road=new ArrayList<HashMap<String,String>>(); //line�е�ÿ���ʵ�״̬·��
				
				Scanner s= new Scanner(line); 
				s.next();  //�����кţ���ȥ�� �������ע
				while(s.hasNext())
				 {  
					 wordlist.add(s.next());
				 }
				
				for(int i=0; i<wordlist.size(); i++ )
				{									
					ArrayList<String> statuslist=word_status.get(wordlist.get(i));  //line�ж�Ӧ��word�Ŀ���״̬					
					
					HashMap<String,Double> property=new HashMap<String,Double>();  //line�ж�Ӧ״̬����ֵ
					HashMap<String,String> road=new HashMap<String,String>(); //line�ж�Ӧ��·״̬
					
					if(i==0)//�ж��Ƿ��ǵ�һ����
					{
						if(word_status.get(wordlist.get(i))!=null)  //line�ж�Ӧ��word����δ�����
						{						
						  for(int j=0; j<statuslist.size()-1; j+=2)
						  {
							if(statuslist.get(j)!=null&&statuslist.get(j+1)!=null)
							{
							  property.put(statuslist.get(j),  Double.parseDouble((statuslist.get(j+1))));
							  
							  road.put(statuslist.get(j), "-1");						  
							}
						  }
						}
					    else
					    {
						    property.put("n",0.0);
						    road.put("n","-1");
					     }						
					}
					else
					{						
						if(word_status.get(wordlist.get(i))!=null)   //line�ж�Ӧ��word����δ�����
						{
							for(int j=0; j<statuslist.size()-1; j+=2)  //�Դʵ�ÿһ�����ܵ�״̬���п���
						   {							
						       double  sumscore=-10000000000.0;
						       String beststatus=null;
						      
						       String w=statuslist.get(j);
						      
						       Iterator<Map.Entry<String,Double>> before_wscore=(word_property.get(i-1)).entrySet().iterator();//ǰһ���ʵ�״̬�͵÷�					      						      					    	  
						       while (before_wscore.hasNext())
						       {
						    	     Map.Entry<String,Double>   entrywscore=before_wscore.next();
						    	  
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+(status_word_p.get(w)).get(wordlist.get(i)))>=sumscore)
						    		  {   //ԭʼ����+ת�Ƹ���+�������
						    			  sumscore=((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+(status_word_p.get(w)).get(wordlist.get(i)));
						    			  beststatus=entrywscore.getKey();
						    			  
						    		  }
					           }
						    	
						    	property.put(statuslist.get(j),  sumscore);							
						    	
						    	if(beststatus!=null)
						    	{
						    		road.put(statuslist.get(j), beststatus);
						    	}
						    	else
						    	{   
						    		System.out.println("��ȡ���״̬ʱ������");
						    	}							    
						    }						    
						 }
						 else    //line�ж�Ӧ��word��δ�����
						 {
							  double  sumscore=-10000000000.0;
						      String beststatus=null;  //�˴�Ӧ��ָ��һ��ֵ
						      
						      ArrayList<String> ww=new ArrayList<String>();
						      ww.add("n");ww.add("v");//ww.add("nP");ww.add("a");ww.add("vN");
						      
						     for(int k=0; k<ww.size(); k++) 
						     {
						    	 String w=ww.get(k);
						         Iterator<Map.Entry<String,Double>> before_wscore=(word_property.get(i-1)).entrySet().iterator();//ǰһ���ʵ�״̬�͵÷�					      
						      
						         while (before_wscore.hasNext())
						        {
						    	    Map.Entry<String,Double>  entrywscore=before_wscore.next();
						    	  
						    	   if(w.endsWith("n"))
						    	   {
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0)>=sumscore)
						    		  {   //ԭʼ����+ת�Ƹ���+�������,  �������ͳһ��Ϊ0.0
						    			  sumscore=((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0);
						    			  beststatus=entrywscore.getKey();						    			  
						    		  }
						    	   }
						    	   else
						    	   {
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0)>=sumscore)
						    		   {   //ԭʼ����+ת�Ƹ���+�������,  �������ͳһ��Ϊ0.0
						    			  sumscore=((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0);
						    			  beststatus=entrywscore.getKey();						    			  
						    		   } 
						    	   }						    	
						        } 
						     
						    	 property.put(w,sumscore);							
						    	 if(beststatus!=null)
						    	 {
						    		road.put(w, beststatus);
						    	 }
						    	 else
						    	 {
						    		
						    		System.out.println("��ȡ  δ�����   ״̬ʱ������");
						    	 }							    
						      } 
						  }													
					 }	
					 word_property.add(property);
					 status_road.add(road);						
				}					

				String endstatus=null;
				double endscore=-10000.0;
				
				Iterator<Map.Entry<String,Double>> it0=(word_property.get(word_property.size()-1)).entrySet().iterator();//���һ���ʵĵ÷�
				Map.Entry<String,Double> entry0=it0.next();
				endstatus=entry0.getKey();
				endscore=entry0.getValue();
				
				 while (it0.hasNext())
			      {
					 entry0=it0.next();
					 if(entry0.getValue()>endscore)
					 {
						 endstatus=entry0.getKey();
						 endscore=entry0.getValue();
					 }					 
			      }
				
				endlist.add(endstatus);//�õ����һ���ʵĵ÷������Ǹ�״̬
				
				Iterator<Map.Entry<String,String>> it=(status_road.get(status_road.size()-1)).entrySet().iterator();//���һ���ʵ�״̬

				while (it.hasNext())
			      {
				      Map.Entry<String,String> entry=it.next();				     		
				     if(endstatus.equals(entry.getKey()))
				     {
	  			           endstatus=entry.getValue();
				     }				     
			      }
				
				for(int k=status_road.size()-2; k>=0; k-- )  //��line�ж�Ӧ��·״̬ ȡ����õ�·�ߣ�endlist��
				{										
			  			endlist.add(endstatus);
			  			endstatus=(status_road.get(k)).get(endstatus);			  												
				}
				
				fout.write(hang+" ");
				
				for(int k1=0, k2=endlist.size()-1; k1<wordlist.size(); k1++, k2-- )
				{
					fout.write(wordlist.get(k1)+"/");
					fout.write(endlist.get(k2)+" ");
					fout.flush();
				}
				fout.write("\r\n");	
				fout.close();
				fout.flush();				
		 }
		 catch (FileNotFoundException ex)
		 {		
			  System.out.println("f File not Found");
	     }  
	     catch (IOException ex1)
		 {	    }			 
	 }
	 
}
