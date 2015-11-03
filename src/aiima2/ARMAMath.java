package aiima2;

import Jama.Matrix;

public class ARMAMath {
	public double avgData(double[] dataArray)
	{
		return this.sumData(dataArray)/dataArray.length;
	}
	
	public double sumData(double[] dataArray)
	{
		double sumData=0;
		for(int i=0;i<dataArray.length;i++)
		{
			sumData+=dataArray[i];
		}
		return sumData;
	}
	
	public double stderrData(double[] dataArray)
	{
		return Math.sqrt(this.varerrData(dataArray));
	}
	
	public double varerrData(double[] dataArray)
	{
		double variance=0;
		double avgsumData=this.avgData(dataArray);
		
		for(int i=0;i<dataArray.length;i++)
		{
			dataArray[i]-=avgsumData;
			variance+=dataArray[i]*dataArray[i];
		}
		return variance/dataArray.length;//variance error;
	}
	
	/**
	 * 计算自相关的函数 Tho(k)=Grma(k)/Grma(0)
	 * @param dataArray 数列
	 * @param order 阶数
	 * @return
	 */
	public double[] autocorData(double[] dataArray,int order)
	{
		double[] autoCor=new double[order+1];
		double varData=this.varerrData(dataArray);//标准化过后的方差
		
		for(int i=0;i<=order;i++)
		{
			autoCor[i]=0;
			for(int j=0;j<dataArray.length-i;j++)
			{
				autoCor[i]+=dataArray[j+i]*dataArray[j];
			}
			autoCor[i]/=(dataArray.length-i);
			autoCor[i]/=varData;
		}
		return autoCor;
	}
	
/**
 * Grma
 * @param dataArray
 * @param order
 * @return 序列的自相关系数
 */
	public double[] autocorGrma(double[] dataArray,int order)
	{
		double[] autoCor=new double[order+1];
		for(int i=0;i<=order;i++)
		{
			autoCor[i]=0;
			for(int j=0;j<dataArray.length-i;j++)
			{
				autoCor[i]+=dataArray[j+i]*dataArray[j];
			}
			autoCor[i]/=(dataArray.length-i);
			
		}
		return autoCor;
	}
	
/**
 * 求偏自相关系�?
 * @param dataArray
 * @param order
 * @return
 */
	public double[] parautocorData(double[] dataArray,int order)
	{
		double parautocor[]=new double[order];
		
		for(int i=1;i<=order;i++)
	    {
			parautocor[i-1]=this.parcorrCompute(dataArray, i,0)[i-1];
	    }
		return parautocor;
	}
/**
 * 产生Toplize矩阵
 * @param dataArray
 * @param order
 * @return
 */
	public double[][] toplize(double[] dataArray,int order)
	{//返回toplize二维数组
		double[][] toplizeMatrix=new double[order][order];
		double[] atuocorr=this.autocorData(dataArray,order);

		for(int i=1;i<=order;i++)
		{
			int k=1;
			for(int j=i-1;j>0;j--)
			{
				toplizeMatrix[i-1][j-1]=atuocorr[k++];
			}
			toplizeMatrix[i-1][i-1]=1;
			int kk=1;
			for(int j=i;j<order;j++)
			{
				toplizeMatrix[i-1][j]=atuocorr[kk++];
			}
		}
		return toplizeMatrix;
	}

	/**
	 * 解MA模型的参�?
	 * @param autocorData
	 * @param q
	 * @return
	 */
	public double[] getMApara(double[] autocorData,int q)
	{
		double[] maPara=new double[q+1];//第一个存放噪声参数，后面q个存放ma参数sigma2,ma1,ma2...
		double[] tempmaPara=new double[q+1];
		
		double temp=0;
		boolean iterationFlag=true;
		//解方程组
		//迭代法解方程�?
		maPara[0]=1;//初始�?
		int count=10000;
		while(iterationFlag&&count-->0)
		{
			temp=0;
			for(int i=1;i<maPara.length;i++)
			{
				temp+=maPara[i]*maPara[i];
			}
			tempmaPara[0]=autocorData[0]/(1+temp);
			
			for(int i=1;i<maPara.length;i++)
			{
				temp=0;
				for(int j=1;j<maPara.length-i;j++)
				{
					temp+=maPara[j]*maPara[j+i];
				}
				tempmaPara[i]=-(autocorData[i]/tempmaPara[0]-temp);
			}
			iterationFlag=false;
			for(int i=0;i<maPara.length;i++)
			{
				if(Math.abs(maPara[i]-tempmaPara[i])>0.00001)
				{
					iterationFlag=true;
					break;
				}
			}
			
			System.arraycopy(tempmaPara, 0, maPara, 0, tempmaPara.length);
		}
	
		return maPara;
	}
	/**
	 * 计算自回归系�?
	 * @param dataArray
	 * @param p
	 * @param q
	 * @return
	 */
	public double[] parcorrCompute(double[] dataArray,int p,int q)
	{
		double[][] toplizeArray=new double[p][p];//p阶toplize矩阵�?
		
		double[] atuocorr=this.autocorData(dataArray,p+q);//返回p+q阶的自相关函�?
		double[] autocorrF=this.autocorGrma(dataArray, p+q);//返回p+q阶的自相关系数数
		for(int i=1;i<=p;i++)
		{
			int k=1;
			for(int j=i-1;j>0;j--)
			{
				toplizeArray[i-1][j-1]=atuocorr[q+k++];
			}
			toplizeArray[i-1][i-1]=atuocorr[q];
			int kk=1;
			for(int j=i;j<p;j++)
			{
				toplizeArray[i-1][j]=atuocorr[q+kk++];
			}
		}
		
	    Matrix toplizeMatrix = new Matrix(toplizeArray);//由二位数组转换成二维矩阵
	    Matrix toplizeMatrixinverse=toplizeMatrix.inverse();//矩阵求�?运算
		
	    double[] temp=new double[p];
	    for(int i=1;i<=p;i++)
	    {
	    	temp[i-1]=atuocorr[q+i];
	    }
	    
		Matrix autocorrMatrix=new Matrix(temp, p);
		Matrix parautocorDataMatrix=toplizeMatrixinverse.times(autocorrMatrix); //  [Fi]=[toplize]x[autocorr]';
		//矩阵计算结果应该是按照[a b c]'  列向量存储的
		//System.out.println("row="+parautocorDataMatrix.getRowDimension()+"  Col="+parautocorDataMatrix.getColumnDimension());
		//parautocorDataMatrix.print(p, 2);//(输出几行,小数点后保留位数)
		//System.out.println(parautocorDataMatrix.get(p-1,0));
		
		double[] result=new double[parautocorDataMatrix.getRowDimension()+1];
		for(int i=0;i<parautocorDataMatrix.getRowDimension();i++)
		{
			result[i]=parautocorDataMatrix.get(i,0);
		}
		
		//估算sigmat2
		double sum2=0;
		for(int i=0;i<p;i++)
			for(int j=0;j<p;j++)
			{
				sum2+=result[i]*result[j]*autocorrF[Math.abs(i-j)];
			}
		result[result.length-1]=autocorrF[0]-sum2; //result数组�?���?��存储干扰估计�?
		
		
			return result;   //返回0列的�?���?��就是k阶的偏自相关系数 pcorr[k]=返回�?
	}

	
	
	
	

}
