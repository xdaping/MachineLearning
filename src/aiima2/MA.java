package aiima2;

import java.util.Vector;

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
		this.stdoriginalData=new double[stdoriginalData.length];
		System.arraycopy(stdoriginalData, 0, this.stdoriginalData, 0, stdoriginalData.length);
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
		
		return v;//拿到MA模型里面的参数�?
	}
	
	

}
