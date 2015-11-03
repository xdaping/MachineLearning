package aiima2;
import java.util.*;

public class ARMA {
	double[] stdoriginalData={};
	int p;
	int q;
	ARMAMath armamath=new ARMAMath();
	
	/**
	 * ARMA模型
	 * @param stdoriginalData
	 * @param p,q //p,q为MA模型阶数
	 */
	public ARMA(double [] stdoriginalData,int p,int q)
	{
		this.stdoriginalData=new double[stdoriginalData.length];
		System.arraycopy(stdoriginalData, 0, this.stdoriginalData, 0, stdoriginalData.length);
		this.p=p;
		this.q=q;	
	}
	public Vector<double[]> ARMAmodel()
	{
		double[] arcoe=armamath.parcorrCompute(stdoriginalData, p, q);
		double[] autocorData=getautocorofMA(p, q, stdoriginalData, arcoe);
		double[] macoe=armamath.getMApara(autocorData, q);//得到MA模型里面的参数�?
		Vector<double[]> v=new Vector<double[]>();
		v.add(arcoe);
		v.add(macoe);
		return v;
	}
	/**
	 * 得到MA的自相关系数
	 * @param p
	 * @param q
	 * @param stdoriginalData
	 * @param autoCordata
	 * @return
	 */
	public double[] getautocorofMA(int p,int q,double[] stdoriginalData,double[] autoRegress)
	{
		int temp=0;
		double[] errArray=new double[stdoriginalData.length-p];
		int count=0;
		for(int i=p;i<stdoriginalData.length;i++)
		{
			temp=0;
			for(int j=1;j<=p;j++)
				temp+=stdoriginalData[i-j]*autoRegress[j-1];
			errArray[count++]=stdoriginalData[i]-temp;//保存估计残差序列
		}
		return armamath.autocorGrma(errArray, q);
	}
	
	
	
}
