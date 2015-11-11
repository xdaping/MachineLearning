package arima;

import java.util.Arrays;
import java.util.Vector;

import arima.ARMAMath;

public class MA {

	double[] stdoriginalData={};
	int q;
	ARMAMath armamath=new ARMAMath();
	
	/** MA模型
	 * @param stdoriginalData //预处理过后的数据
	 * @param q //q为MA模型阶数
	 */
	public MA(double [] stdoriginalData,int q)
	{
		this.stdoriginalData=stdoriginalData;
		this.q=q;
	}
/**
 * 返回MA模型参数
 * @return
 */
	public Vector<double[]> MAmodel()
	{
		Vector<double[]> v=new Vector<double[]>();
		v.add(armamath.getMApara(armamath.autocorGrma(stdoriginalData,q), q));
		
		//System.out.println(Arrays.toString(armamath.autocorGrma(stdoriginalData,q)));
		
		for(int i=0; i<v.size(); i++)
		System.out.println("v:"+Arrays.toString(v.get(i)));
		
		return v;//拿到MA模型里面的参数值
	}
		
	
}
