package distance;

public class LatLngDistance implements Distance{
	private static double EARTH_RADIUS = 6378.137;//µ¥Î»:km
	
	public double getDistance(double start_lat, double start_lng, 
			double end_lat, double end_lng){
		double rank = getStraightDistance(start_lat, end_lng, end_lat, end_lng);
		
		double column = getStraightDistance(start_lat, start_lng, start_lat, end_lng);
	
		return rank + column;
	}
	
	private double getStraightDistance(double start_lat, double start_lng, 
			double end_lat, double end_lng) {
		double radLat1 = rad(start_lat);
		double radLat2 = rad(end_lat);
	    double a = radLat1 - radLat2;
	    double b = rad(start_lng) - rad(end_lng);

	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	    s = s * EARTH_RADIUS;
	    return s;	
	}
	
	private static double rad(double d){
		   return d * Math.PI / 180.000;
	}
	
	public static void main(String args[]){
		LatLngDistance d = new LatLngDistance();
		System.out.println(d.getDistance(39.90509033203125, 116.3507080078125, 
				39.92706298828125, 116.3507080078125));
		
		System.out.println(d.getStraightDistance(39.0000, 116.3507080078125, 
				39.00001, 116.3507080078125));
		
		
	}
}
