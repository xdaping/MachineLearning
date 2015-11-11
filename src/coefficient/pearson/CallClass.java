package coefficient.pearson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author alan-king
 *
 */
public class CallClass {
	
	
	public static void main(String[] args) throws IOException{
		double CORR = 0.0;
		List<Double> xList = new ArrayList<Double>();;
		List<Double> yList = new ArrayList<Double>();
		
		xList.add(2.0);xList.add(4.0);xList.add(7.0);
		yList.add(6.0);yList.add(8.0);yList.add(4.0);
		
		NumeratorCalculate nc = new NumeratorCalculate(xList,yList);
		double numerator = nc.calcuteNumerator();
		DenominatorCalculate dc = new DenominatorCalculate();
		double denominator = dc.calculateDenominator(xList, yList);
		CORR = numerator/denominator;

		System.out.printf("CORR = "+CORR);
	}
}

