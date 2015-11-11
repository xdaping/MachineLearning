package arima;

import java.util.Arrays;
import java.util.Vector;

import arima.ARMAMath;

public class MA {

	double[] stdoriginalData={};
	int q;
	ARMAMath armamath=new ARMAMath();
	
	/** MAģ��
	 * @param stdoriginalData //Ԥ������������
	 * @param q //qΪMAģ�ͽ���
	 */
	public MA(double [] stdoriginalData,int q)
	{
		this.stdoriginalData=stdoriginalData;
		this.q=q;
	}
/**
 * ����MAģ�Ͳ���
 * @return
 */
	public Vector<double[]> MAmodel()
	{
		Vector<double[]> v=new Vector<double[]>();
		v.add(armamath.getMApara(armamath.autocorGrma(stdoriginalData,q), q));
		
		//System.out.println(Arrays.toString(armamath.autocorGrma(stdoriginalData,q)));
		
		for(int i=0; i<v.size(); i++)
		System.out.println("v:"+Arrays.toString(v.get(i)));
		
		return v;//�õ�MAģ������Ĳ���ֵ
	}
		
	
}
