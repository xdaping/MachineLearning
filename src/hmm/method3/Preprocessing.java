package hmm.method3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Preprocessing 
{   
	final static String FOLDER="5";    //�ַ��������������ı��ļ�����, ���������������
	
	static File f2=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\word_status.txt");      //ÿ���ʶ�Ӧ�Ĵ����ļ�
    static File f3=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_word.txt");      //ÿ�����Զ�Ӧ�Ĵ��ļ�
    static File f4=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\status_status.txt");    //ÿ������ת�ƵĴ����ļ�  
    
    static HashMap<String,ArrayList<String>> word_status=new HashMap<String,ArrayList<String>>();    //ÿ���ʶ�Ӧ�Ĵ���
    static HashMap<String,ArrayList<String>> status_word=new HashMap<String,ArrayList<String>>();    //ÿ�����Զ�Ӧ�Ĵ�
    static HashMap<String,ArrayList<String>> status_status=new HashMap<String,ArrayList<String>>();  //ÿ������ת�ƵĴ���    
	
    private static ArrayList<String> filelist = new ArrayList<String>();  
	
	public static void main(String[] args) 
	 {   	
		 long a = System.currentTimeMillis();
	                      		        
		 Preprocessing q=new Preprocessing();	
	 
	     q.refreshFileList("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\train\\"); 	     
	     q.writefile();
	 	     		       
	     System.out.println(System.currentTimeMillis()-a);    	//������õ�ʱ��
	  }         		   
	 
	 public  void refreshFileList(String strPath)
      { 
	        File dir = new File(strPath); 
	        File[] files = dir.listFiles();        
	        
	        if (files == null) 
	        {
	            return; 
	        }
	        
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
	                Preprocessing p=new Preprocessing();				
	       	        p.get_word_status(files[i]);//     	        	                          
	               
	       	        filelist.add(files[i].getAbsolutePath());                    	            
	            } 
	        } 
	    }
	 
	 
	 public  void get_word_status(File f1)
	 {
		try
		 {  
			FileReader fin=new FileReader(f1);		   				
			BufferedReader bin=new BufferedReader(fin);
			String line=null; 
			int m=0;
			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{	
					String str0[]={null,null};
					m++;		    //�к�
					Scanner s= new Scanner(line);
					s.next();		//ÿ�еĵ�һ�����кţ�ȥ��
					
					while(s.hasNext())
					  {  
						  String word1=s.next();						  						      
						  String str[]=word1.split("/");   //��/Ϊ�ָ��������ʺʹ��Էֿ���ȡ����	
						  
						  boolean b1 = word_status.containsKey(str[0]);         //�жϸô��Ƿ���word_status��
					      if (b1 == false) 
					      {           //�� hashmap ���ޣ������
					    	  ArrayList<String> w_s=new ArrayList<String>();						    	  
					    	  w_s.add(str[1]);		
					    	  word_status.put(str[0], w_s);
					       }
					      else         //�� hashmap �к��иô�             
					      {						    	 					    	   
					    			((ArrayList<String>)word_status.get(str[0])).add(str[1]);						    	  						    	  					    		  
					      }
						  
					      boolean b2 = status_word.containsKey(str[1]);         //�жϸô����Ƿ���status_word��
					      if (b2 == false) 
					      {           //�� hashmap ���ޣ������
					    	  ArrayList<String> s_w=new ArrayList<String>();						    	  
					    	  s_w.add(str[0]);					
					    	  status_word.put(str[1], s_w);
					       }
					      else         //�� hashmap �к��иô�             
					      {						    	 					    	   
					    	  ((ArrayList<String>)status_word.get(str[1])).add(str[0]);						    	  						    	  					    		  
					      }
					      
					      if(str0[1]!=null)
					      {
					         boolean b3 = status_status.containsKey(str0[1]);         //�жϸô����Ƿ���status_status��
					         if (b3 == false) 
					         {           //�� hashmap ���ޣ������
					    	       ArrayList<String> s_s=new ArrayList<String>();						    	  
					    	       s_s.add(str[1]);					
					    	       status_status.put(str0[1], s_s);
					          }
					          else         //�� hashmap �к��иô�             
					         {						    	 					    	   
					    			((ArrayList<String>)status_status.get(str0[1])).add(str[1]);						    	  						    	  					    		  
					          }
					       }
						  
					      str0[1]=str[1];						  						  
					  }												
				}							
			}while(line!=null);	 			 			 
		 }
		 catch (IOException e)
         {	
		    System.out.println("IOException e");
		 }
	 }	 
	 
	 public  void writefile()
	 { 
		try
		 {  
			FileWriter fout1=new FileWriter(f2,true);
			FileWriter fout2=new FileWriter(f3,true);
			FileWriter fout3=new FileWriter(f4,true);
			
			Iterator<Map.Entry<String,ArrayList<String>>> it1=word_status.entrySet().iterator();		 
			 while (it1.hasNext())			 //д�� ÿ���ʶ�Ӧ�Ĵ����ļ�
			  {
				  Map.Entry<String,ArrayList<String>> entry=it1.next();
				  fout1.write(entry.getKey());
				  fout1.write(" ");
				
				  ArrayList<String> entry1=word_status.get(entry.getKey());
				
				  for(int i=0;i<entry1.size();i++)
				   {
					  fout1.write(entry1.get(i));
				      fout1.write(" ");				
				   }
				 
				  fout1.write("\r\n");
				  fout1.flush();
			   }
			
			 Iterator<Map.Entry<String,ArrayList<String>>> it2=status_word.entrySet().iterator();		 
			 while (it2.hasNext())         //д�� ÿ�����Զ�Ӧ�Ĵ��ļ�
			  {
				  Map.Entry<String,ArrayList<String>> entry=it2.next();
				  fout2.write(entry.getKey());
				  fout2.write(" ");
				
				  ArrayList<String> entry1=status_word.get(entry.getKey());
				
				  for(int i=0;i<entry1.size();i++)
				  {
					 fout2.write(entry1.get(i));
				     fout2.write(" ");				
				  }
				
				  fout2.write("\r\n");
				  fout2.flush();
			   }
			 
			 Iterator<Map.Entry<String,ArrayList<String>>> it3=status_status.entrySet().iterator();		 
			 while (it3.hasNext())			 //д�� ÿ������ת�ƵĴ����ļ� 
			  {
				  Map.Entry<String,ArrayList<String>> entry=it3.next();
				  fout3.write(entry.getKey());
				  fout3.write(" ");
				
				  ArrayList<String> entry1=status_status.get(entry.getKey());
				
				  for(int i=0;i<entry1.size();i++)
				  {
					 fout3.write(entry1.get(i));
				     fout3.write(" ");				 
				  }
				
				  fout3.write("\r\n");
				  fout3.flush();
			  }
			 
			 fout1.close();
			 fout2.close();
			 fout3.close();						 
		 }
		 catch (IOException e)
         {	
		    System.out.println("IOException e");
		 }	
	 }
}
