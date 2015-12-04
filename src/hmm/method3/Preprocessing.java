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
	final static String FOLDER="5";    //字符串常量，用来改变文件夹名, 即而方便更改组名
	
	static File f2=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\word_status.txt");      //每个词对应的词性文件
    static File f3=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\status_word.txt");      //每个词性对应的词文件
    static File f4=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\status_status.txt");    //每个词性转移的词性文件  
    
    static HashMap<String,ArrayList<String>> word_status=new HashMap<String,ArrayList<String>>();    //每个词对应的词性
    static HashMap<String,ArrayList<String>> status_word=new HashMap<String,ArrayList<String>>();    //每个词性对应的词
    static HashMap<String,ArrayList<String>> status_status=new HashMap<String,ArrayList<String>>();  //每个词性转移的词性    
	
    private static ArrayList<String> filelist = new ArrayList<String>();  
	
	public static void main(String[] args) 
	 {   	
		 long a = System.currentTimeMillis();
	                      		        
		 Preprocessing q=new Preprocessing();	
	 
	     q.refreshFileList("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\train\\"); 	     
	     q.writefile();
	 	     		       
	     System.out.println(System.currentTimeMillis()-a);    	//输出所用的时间
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
					m++;		    //行号
					Scanner s= new Scanner(line);
					s.next();		//每行的第一个是行号，去掉
					
					while(s.hasNext())
					  {  
						  String word1=s.next();						  						      
						  String str[]=word1.split("/");   //以/为分隔符，将词和词性分开并取出来	
						  
						  boolean b1 = word_status.containsKey(str[0]);         //判断该词是否在word_status中
					      if (b1 == false) 
					      {           //若 hashmap 中无，则添加
					    	  ArrayList<String> w_s=new ArrayList<String>();						    	  
					    	  w_s.add(str[1]);		
					    	  word_status.put(str[0], w_s);
					       }
					      else         //若 hashmap 中含有该词             
					      {						    	 					    	   
					    			((ArrayList<String>)word_status.get(str[0])).add(str[1]);						    	  						    	  					    		  
					      }
						  
					      boolean b2 = status_word.containsKey(str[1]);         //判断该词性是否在status_word中
					      if (b2 == false) 
					      {           //若 hashmap 中无，则添加
					    	  ArrayList<String> s_w=new ArrayList<String>();						    	  
					    	  s_w.add(str[0]);					
					    	  status_word.put(str[1], s_w);
					       }
					      else         //若 hashmap 中含有该词             
					      {						    	 					    	   
					    	  ((ArrayList<String>)status_word.get(str[1])).add(str[0]);						    	  						    	  					    		  
					      }
					      
					      if(str0[1]!=null)
					      {
					         boolean b3 = status_status.containsKey(str0[1]);         //判断该词性是否在status_status中
					         if (b3 == false) 
					         {           //若 hashmap 中无，则添加
					    	       ArrayList<String> s_s=new ArrayList<String>();						    	  
					    	       s_s.add(str[1]);					
					    	       status_status.put(str0[1], s_s);
					          }
					          else         //若 hashmap 中含有该词             
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
			 while (it1.hasNext())			 //写入 每个词对应的词性文件
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
			 while (it2.hasNext())         //写入 每个词性对应的词文件
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
			 while (it3.hasNext())			 //写入 每个词性转移的词性文件 
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
