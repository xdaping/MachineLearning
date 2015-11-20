package km.methods2;
import java.util.Arrays;


public class Demo {

	public static void main(String args[])
	{
		double[][] data = {{100000,100000,100000,100000,100000,100000,100000,100000},
				{Double.MAX_VALUE,Double.MAX_VALUE,0.9200,0.9300,0.9680,0.9680,0.9880,0.9600},
				{0.9000,0.9300,Double.MAX_VALUE,0.9500,0.9080,0.9480,0.9680,0.9600},
				{0.9400,0.9300,0.9400,0.9500,0.9680,0.9080,0.9880,0.9200},		
				{0.8600,0.8900,0.9200,0.9500,0.9880,0.9480,0.9680,0.9200},				
				{100000,100000,100000,100000,100000,100000,100000,100000},
				{0.9400,0.9700,0.9000,0.9300,0.9680,0.9480,0.9680,1.0000},
				{100000,100000,100000,100000,100000,100000,100000,100000 },
				{0.9400,0.9700,0.9000,0.9700,0.9680,0.9480,0.9480,0.9600},
				{100000,100000,100000,100000,100000,100000,100000,100000 }}; 

		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				data[i][j]=-data[i][j];
			}
		}
		
		

		KuhnMunkres algorithm = new KuhnMunkres(10);		
		
		double[] result = new double[10];
		
		algorithm.getMaxBipartie(data, result);
		
		System.out.println(Arrays.toString(algorithm.match));
		System.out.println(Arrays.deepToString(algorithm.matchResult()));
		
	}
}









 