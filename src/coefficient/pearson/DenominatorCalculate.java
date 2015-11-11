package coefficient.pearson;

import java.util.List;

/**
 * @author alan-king
 *
 */
public class DenominatorCalculate {
	
	//add denominatorCalculate method
	public double calculateDenominator(List<Double> xList,List<Double> yList){
		double standardDifference = 0.0;
		int size = xList.size();
		double xAverage = 0.0;
		double yAverage = 0.0;
		double xException = 0.0;
		double yException = 0.0;
		double temp = 0.0;
		for(int i=0;i<size;i++){
			temp += xList.get(i);
		}
		xAverage = temp/size;
		
		for(int i=0;i<size;i++){
			temp += yList.get(i);
		}
		yAverage = temp/size;
		
		for(int i=0;i<size;i++){
			xException += Math.pow(xList.get(i)-xAverage,2);
			yException += Math.pow(yList.get(i)-yAverage, 2);
		}
		//calculate denominator of 
		return standardDifference = Math.sqrt(xException*yException);
	}
}

