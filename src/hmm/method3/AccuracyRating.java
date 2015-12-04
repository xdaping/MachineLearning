package hmm.method3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//1  0.8987455197132617
//2  0.8822669887780549
//3  0.8904616796094237
//4  0.896054114994363
//5  0.8860200012628286

public class AccuracyRating
{
	 final static String FOLDER="5";	  //�ַ��������������ı��ļ�����, ���������������
	
	 private static ArrayList<String> filelist = new ArrayList<String>();  
	 
	 private static long sumword=0, rightword=0;
		
	    public static void main(String[] args) 
		 {   	
			 long a = System.currentTimeMillis();
		                      		        
			 AccuracyRating q=new AccuracyRating();				
			 
		     q.refreshFileList("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\prediction_test"); 
		     
		     System.out.println("sumword:"+sumword+"rightword:"+rightword);
		     System.out.println("AccuracyRating:"+1.0*rightword/sumword);
		 	     		       
		     System.out.println(System.currentTimeMillis()-a);    //������õ�ʱ��
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
		                AccuracyRating p=new AccuracyRating();				
		       	        p.hadle(files[i]) ;    	        
		                          
		               filelist.add(files[i].getAbsolutePath());                    		            
		            } 
		        } 
		    }
		 
		 public  void  hadle(File f1)
	    { 
			 File f2=new File("E:\\��һ�γ�\\��������\\train_test\\"+FOLDER+"\\test\\"+f1.getName());
						 
			 System.out.println(f1.getName());
			 
			 try
			 {  
				FileReader fin=new FileReader(f1);		//  ����Ĵ��Ա�ע 				
				BufferedReader bin=new BufferedReader(fin);
				
				FileReader fin0=new FileReader(f2);		//  ԭʼ�Ĵ��Ա�ע				
				BufferedReader bin0=new BufferedReader(fin0);
				
				String line=null; 
				
				String line0=null;				
				
				do
				{   			
					line=bin.readLine();
					line0=bin0.readLine();
					if(line!=null&&line0!=null)
					{							
						Scanner s= new Scanner(line);	
						Scanner s0= new Scanner(line0);	 
						
						while(s.hasNext()&&s0.hasNext())
						  {  
							  sumword++;
							 							  
							  if(s.next().equals(s0.next()))
							  {
								  rightword++;
							  }							  						      
						  }								
					}							
				}while(line!=null&&line0!=null);	 			
			 }		 
			 catch (FileNotFoundException ex)
			    {		
				  System.out.println("f1 File not Found");
				}  
		        catch (IOException ex1)
			    {	    }			 
		 }
}
