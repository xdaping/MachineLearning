package km.methods2;
import java.util.Arrays;


public class Demo {

	public static void main(String args[])
	{
		double[][] data = {{Double.MAX_VALUE,0.9700,0.8600,0.9700,0.9680,0.9480,0.9680,0.9800},
				{Double.MAX_VALUE,Double.MAX_VALUE,0.9200,0.9300,0.9680,0.9680,0.9880,0.9600},
				{0.9000,0.9300,Double.MAX_VALUE,0.9500,0.9080,0.9480,0.9680,0.9600},
				{0.9400,0.9300,0.9400,0.9500,0.9680,0.9080,0.9880,0.9200},		
				{0.8600,0.8900,0.9200,0.9500,0.9880,0.9480,0.9680,0.9200},				
				{0.9200,Double.MAX_VALUE,0.9200,0.9500,0.9080,0.9480,0.9880,0.9200},
				{0.9400,0.9700,0.9000,0.9300,0.9680,0.9480,0.9680,1.0000},
				{0.9200,0.9700,0.9200,0.9300,0.9880,0.9880,0.9480,0.9600},
				{0.9400,0.9700,0.9000,0.9700,0.9680,0.9480,0.9480,0.9600},
				{0.9200,0.9500,0.9200,0.9700,0.9480,0.9480,0.9480,0.9800 }}; 

		double[][] data1 = new double[10][8];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				data1[i][j]=-data[i][j];
			}
		}

		KuhnMunkres algorithm = new KuhnMunkres(10);		
		
		double[] result = new double[10];
		
		algorithm.getMaxBipartie(data1, result);
		
		System.out.println(Arrays.toString(algorithm.match));
		System.out.println(Arrays.deepToString(algorithm.matchResult()));
		
	}
}









 