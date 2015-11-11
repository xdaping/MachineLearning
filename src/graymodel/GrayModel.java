package graymodel;

import java.util.Arrays;

/**
 * 灰度预测模型
 */
public class GrayModel {
	private double a0, a1, a2;
	private int size;
	private double error;

	public GrayModel() {
	}

	public void build(double[] x0) {
		size = x0.length;
		double[] x1 = new double[size];
		x1[0] = x0[0];
		for (int i = 1; i < size; i++) {
			x1[i] = x0[i] + x1[i - 1];
		}
		double[][] b = new double[size - 1][2];
		double[][] bt = new double[2][size - 1];
		double[][] y = new double[size - 1][1];
		for (int i = 0; i < b.length; i++) {
			b[i][0] = -(x1[i] + x1[i + 1]) / 2;
			b[i][1] = 1;
			bt[0][i] = b[i][0];
			bt[1][i] = 1;
			y[i][0] = x0[i + 1];
		}
		
		//System.out.println(Arrays.deepToString(y));
		
		double[][] t = new double[2][2];
		multiply(bt, b, t);//t=bt*b
		t = inverse(t);
		double[][] t1 = new double[2][size - 1];
		multiply(t, bt, t1);
		double[][] t2 = new double[2][1];
		multiply(t1, y, t2);
		a0 = t2[0][0];
		double u = t2[1][0];
		a2 = u / a0;
		a1 = x0[0] - a2;
		a0 = -a0;
		
		// x1[i]=(x0[0]-u/a0)*exp(-a0*i)+u/a0

		error = 0;
		for (int i = 0; i < x0.length; i++) {
			double d = (x0[i] - getX0(i));
			error += d * d;
		}
		error /= x0.length;
	}

	/**
	 * 误差
	 * 
	 * @return
	 */
	public double getError() {
		return error;
	}

	double getX1(int k) {
		return a1 * Math.exp(a0 * k) + a2;
	}

	double getX0(int k) {
		// return a0 * a1 * Math.exp(a0 * k);
		if (k == 0)
			return a1 * Math.exp(a0 * k) + a2;
		else
			return a1 * (Math.exp(a0 * k) - Math.exp(a0 * (k - 1)));
	}

	/**
	 * 预测后续的值
	 * 
	 * @param index
	 * @return
	 */
	public double nextValue(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException();
		return getX0(size + index);
	}

	/**
	 * 预测下一个值
	 * 
	 * @return
	 */
	public double nextValue() {
		return nextValue(0);
	}

	static double[][] inverse(double[][] t) {
		double[][] a = new double[2][2];
		double det = t[0][0] * t[1][1] - t[0][1] * t[1][0];
		a[0][0] = t[1][1] / det;
		a[0][1] = -t[1][0] / det;
		a[1][0] = -t[0][1] / det;
		a[1][1] = t[0][0] / det;
		return a;
	}

	static void multiply(double[][] left, double[][] right, double[][] dest) {
		int n1 = left.length;
		int m1 = left[0].length;
		int m2 = right[0].length;
		for (int k = 0; k < n1; k++) {
			for (int s = 0; s < m2; s++) {
				dest[k][s] = 0;
				for (int i = 0; i < m1; i++) {
					dest[k][s] += left[k][i] * right[i][s];
				}
			}
		}
	}
	
	public static void test1(){
		GrayModel gs = new GrayModel();		
		double[] y = new double[]{71.1, 72.0, 72.1, 72.1, 71.4, 72.0, 72.6};
		System.out.println(Arrays.toString(y));
		
		gs.build(y);
		
		System.out.println("误差："+Math.sqrt(gs.getError()));
		System.out.println("真实值：  预测值");
		for (int i = 0; i < y.length; i++) {
			System.out.println(y[i] +": "+ gs.getX0(i));
		}
		
		System.out.println("后续预测值：");
		for (int i = 0; i < 25; i++) {
			//预测值
			System.out.println(gs.nextValue(i));			
		}		
	}

	public static void main(String[] args) {
		test1();

	}
}