package geohash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;

public class AreaGridSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "F:/EDJData/geohashGridSet/beijing.txt";
		getAreaGridSetPoint(39.688931, 116.071945, 40.187268, 116.746896, fileName);//北京
		
		fileName = "F:/EDJData/geohashGridSet/shanghai.txt";
		getAreaGridSetPoint(30.826114, 121.089869, 31.419654, 121.929246, fileName);//上海
	}

	
	
	private static void getAreaGridSetPoint(double minLat, double minLng,
			double maxLat, double maxLng, String fileName) {
		List<GeoHash> ups = new ArrayList<GeoHash>(); 
		double lat = minLat; 
		double lng = minLng; 

		GeoHash geohash = GeoHash.withCharacterPrecision(lat, lng, 5); 
		ups.add(geohash); 
		GeoHash cur = geohash; 

		while (true) { 
			GeoHash north = cur.getNorthernNeighbour(); 
			if (north.getBoundingBox().getMinLat() > maxLat) { 
				break; 
			} 

			ups.add(north); 
			cur = north; 
		} 

		List<GeoHash> allGeohashs = new ArrayList<GeoHash>(); 
		allGeohashs.addAll(ups); 

		for (GeoHash one : ups) { 
			cur = one; 
			while (true) { 
				GeoHash east = cur.getEasternNeighbour(); 
				if (east.getBoundingBox().getMinLon() > maxLng) { 
					break; 
				} 
	
				allGeohashs.add(east); 
				cur = east; 
				} 
		} 	
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
			for (GeoHash one : allGeohashs) { 
				WGS84Point cp = one.getBoundingBoxCenterPoint();
				
				double[] wgs84 = CoordinateTransform.bd09ToWgs84(cp.getLongitude(), cp.getLatitude());
				
				System.out.println(one.toBase32()+" "+cp.toString());
				//System.out.println(one.toBase32()+" "+wgs84[1]+","+wgs84[0]);
				
				//bw.write(one.toBase32()+" "+cp.toString());
				
				bw.write(one.toBase32()+" "+wgs84[1]+","+wgs84[0]);
				bw.newLine();
				bw.flush();

			}
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		 
		
	}
	
}
