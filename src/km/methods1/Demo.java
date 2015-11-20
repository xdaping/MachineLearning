package km.methods1;

import java.util.Arrays;
import java.util.Random;

public class Demo {

	public static void main(String args[]){
		Random random = new Random();
		
		
		
		int X = 2500, Y = 2500;
		int[][] data = new int[X][Y];
		
		for(int i=0; i<data.length; i++){
			for(int j=0; j<data[0].length; j++){
				data[i][j] = Math.abs(random.nextInt())%5000;
				
				//System.out.println(-Math.abs(random.nextInt())%5000);
			}
		}
		
		for(int i=0; i<data.length; i++){
			
			//System.out.println(Arrays.toString(data[i]));
			
		}
		
		long t1 = System.currentTimeMillis();
		
		int[][] result = KuhnMunkres.runKMAlgorithm(data);//维数：data.length X 2
		System.out.println(System.currentTimeMillis()-t1);
		//output: [[4, 0], [9, 1], [0, 2], [7, 3], [2, 4], [3, 5], [8, 6], [5, 7]]
		System.out.println(Arrays.deepToString(result));
		
		
	}
}
