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
	final static String FOLDER="5";	 //字符串常量，用来改变文件夹名, 即而方便更改组名
	
	static File f2=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\status_num.txt");   	 //词性编号文件 
	static File f3=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\status_status_p.txt");  //每个词性转移的词性 概率文件    
	static File f4=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\status_word_p.txt");    //每个词性对应的词 概率文件
	static File f5=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\word_status_p.txt");    //每个词对应的词性 概率文件 	  
	
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
		 q.statusnumber(); 	     //f2  对状态标号
		 q.initializematrix();   //    初始化status_status矩阵
		 q.statusstatusp();		 //f3  对status_status矩阵赋值
		 q.statuswordp();        //f4  对status_word_p赋值
		 q.wordstatus();         //f5  对word_status赋值	
		 
		 File dir=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\prediction_test");  //在E盘下创建一个空的目录
         if(!dir.exists())
         {
       	  dir.mkdir();
         }
		 
	     q.refreshFileList("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\initialization_test");     
	     	    	 	     		       
	     System.out.println(System.currentTimeMillis()-a);
	  }         		   
	 
	 public  void  statusnumber()  //对状态标号
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
	 
	 public  void  initializematrix()   //初始化status_status矩阵
	   { 	
		 for(int i=0; i<statusnumber+1; i++)
			 for(int j=0; j<statusnumber+1; j++)
				 matrix_status[i][j]=100.0;
	   }	 
	 
	 public  void  statusstatusp()   //对status_status矩阵赋值
	    { 						 
			 try
			 {  
				FileReader fin=new FileReader(f3);		   				
				BufferedReader bin=new BufferedReader(fin);
				
				String line=null; 				
				int m=0;  //表示行
				
				do
				{   			
					line=bin.readLine();				
					if(line!=null)
					{	++m;
					    int n=0;  //表示列
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
	 
	 public  void  statuswordp()   //对status_word_p赋值
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
	 
	 public  void  wordstatus()   //对word_status赋值
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
		File f2=new File("E:\\研一课程\\机器翻译\\train_test\\"+FOLDER+"\\prediction_test\\21009256_"+f1.getName());
		 
		 System.out.println(f1.getName());		 
		 try
		 {  
			FileReader fin=new FileReader(f1);		   				
			BufferedReader bin=new BufferedReader(fin);
				
			String line=null; 
            
			int hang=0;   //行号
			
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
			 
			    ArrayList<String> wordlist=new ArrayList<String>();// 用来对line中的词进行标号
				ArrayList<String> endlist=new ArrayList<String>();// line中的词最好的词性
				
				ArrayList<HashMap<String,Double>>  word_property=new ArrayList<HashMap<String,Double>>(); //用来对line中的每个词标明所有可能的词性
				
				ArrayList<HashMap<String,String>>  status_road=new ArrayList<HashMap<String,String>>(); //line中的每个词的状态路线
				
				Scanner s= new Scanner(line); 
				s.next();  //这是行号，先去掉 ，无需标注
				while(s.hasNext())
				 {  
					 wordlist.add(s.next());
				 }
				
				for(int i=0; i<wordlist.size(); i++ )
				{									
					ArrayList<String> statuslist=word_status.get(wordlist.get(i));  //line中对应的word的可能状态					
					
					HashMap<String,Double> property=new HashMap<String,Double>();  //line中对应状态计算值
					HashMap<String,String> road=new HashMap<String,String>(); //line中对应回路状态
					
					if(i==0)//判断是否是第一个词
					{
						if(word_status.get(wordlist.get(i))!=null)  //line中对应的word不是未登入词
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
						if(word_status.get(wordlist.get(i))!=null)   //line中对应的word不是未登入词
						{
							for(int j=0; j<statuslist.size()-1; j+=2)  //对词的每一个可能的状态进行考虑
						   {							
						       double  sumscore=-10000000000.0;
						       String beststatus=null;
						      
						       String w=statuslist.get(j);
						      
						       Iterator<Map.Entry<String,Double>> before_wscore=(word_property.get(i-1)).entrySet().iterator();//前一个词的状态和得分					      						      					    	  
						       while (before_wscore.hasNext())
						       {
						    	     Map.Entry<String,Double>   entrywscore=before_wscore.next();
						    	  
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+(status_word_p.get(w)).get(wordlist.get(i)))>=sumscore)
						    		  {   //原始概率+转移概率+发射概率
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
						    		System.out.println("在取最佳状态时出错了");
						    	}							    
						    }						    
						 }
						 else    //line中对应的word是未登入词
						 {
							  double  sumscore=-10000000000.0;
						      String beststatus=null;  //此处应先指定一个值
						      
						      ArrayList<String> ww=new ArrayList<String>();
						      ww.add("n");ww.add("v");//ww.add("nP");ww.add("a");ww.add("vN");
						      
						     for(int k=0; k<ww.size(); k++) 
						     {
						    	 String w=ww.get(k);
						         Iterator<Map.Entry<String,Double>> before_wscore=(word_property.get(i-1)).entrySet().iterator();//前一个词的状态和得分					      
						      
						         while (before_wscore.hasNext())
						        {
						    	    Map.Entry<String,Double>  entrywscore=before_wscore.next();
						    	  
						    	   if(w.endsWith("n"))
						    	   {
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0)>=sumscore)
						    		  {   //原始概率+转移概率+发射概率,  发射概率统一设为0.0
						    			  sumscore=((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0);
						    			  beststatus=entrywscore.getKey();						    			  
						    		  }
						    	   }
						    	   else
						    	   {
						    		  if(((entrywscore.getValue())+matrix_status[status_num.get(entrywscore.getKey())][status_num.get(w)]+0.0)>=sumscore)
						    		   {   //原始概率+转移概率+发射概率,  发射概率统一设为0.0
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
						    		
						    		System.out.println("在取  未登入词   状态时出错了");
						    	 }							    
						      } 
						  }													
					 }	
					 word_property.add(property);
					 status_road.add(road);						
				}					

				String endstatus=null;
				double endscore=-10000.0;
				
				Iterator<Map.Entry<String,Double>> it0=(word_property.get(word_property.size()-1)).entrySet().iterator();//最后一个词的得分
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
				
				endlist.add(endstatus);//得到最后一个词的得分最大的那个状态
				
				Iterator<Map.Entry<String,String>> it=(status_road.get(status_road.size()-1)).entrySet().iterator();//最后一个词的状态

				while (it.hasNext())
			      {
				      Map.Entry<String,String> entry=it.next();				     		
				     if(endstatus.equals(entry.getKey()))
				     {
	  			           endstatus=entry.getValue();
				     }				     
			      }
				
				for(int k=status_road.size()-2; k>=0; k-- )  //从line中对应回路状态 取出最好的路线（endlist）
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
