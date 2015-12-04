package hmm.method3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandleTest   //删除语料中词性标记
{
	final static String FOLDER="5";     //字符串常量，用来改变文件夹名, 即而方便更改组名
	
    private static ArrayList<String> filelist = new ArrayList<String>();  
	
	public static void main(String[] args) 
	 {   	
		 long a = System.currentTimeMillis();
	                      		        
		 HandleTest q=new HandleTest();	
		 
		 File dir=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\initialization_test\\");  //在E盘下创建一个空的目录
         if(!dir.exists())
         {
       	  dir.mkdir();
         }
         
	     q.refreshFileList("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\test\\"); 
	     
	     System.out.println(System.currentTimeMillis()-a);   //输出所用的时间
	  }         		   
	 
	 public  void refreshFileList(String strPath)
      { 		    
			File dir = new File(strPath); 
	        File[] files = dir.listFiles();        
	        
	        if (files ==null) 
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
	                HandleTest p=new HandleTest();				
	       	        p.hadle(files[i]) ;    	        
	                          
	               filelist.add(files[i].getAbsolutePath());                    	            
	            } 
	        } 
	    }
	 
	 
	 public  void  hadle(File f1)
     { 
		 File f2=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\initialization_test\\"+f1.getName());
		 
		 try
		 {  
			FileReader fin=new FileReader(f1);		   				
			BufferedReader bin=new BufferedReader(fin);
			
			FileWriter fout=new FileWriter(f2);
			
			String line=null;   

			do
			{   			
				line=bin.readLine();				
				if(line!=null)
				{						
					Scanner s= new Scanner(line);
					fout.write(s.next());
					fout.write(" ");
					
					while(s.hasNext())
					  {  
						   String word=s.next();
						   String str[]=word.split("/");   //以/为分隔符，将词和词性分开并取出来										
					       fout.write(str[0]);
					       fout.write(" ");					      
					       fout.flush();
					  }
					
					fout.write("\r\n");							
				}						
			}while(line!=null);	 
			 
			fout.close(); 
		 }
		 catch (IOException e)
         {	
		    System.out.println("IOException e");
		 }		 		 
	 }	 
}
