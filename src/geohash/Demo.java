package geohash;

import ch.hsr.geohash.GeoHash;

public class Demo {

	public static void main(String args[]) {
                
        //test1();
        
        test2();
    }	

	private static void test2() {
		GeoHash geoHash = GeoHash.withCharacterPrecision(39.69731931820395,116.06873609678199, 6);
		System.out.println(geoHash.toString());
		System.out.println(geoHash.getBoundingBox().getLatitudeSize());
		geoHash = GeoHash.withCharacterPrecision(39.69731931820395+0.003,116.06873609678199+0.003, 6);
		System.out.println(geoHash.toString());
		geoHash = GeoHash.withCharacterPrecision(39.69731931820395+0.005,116.06873609678199-0.003, 6);
		System.out.println(geoHash.toString());
		geoHash = GeoHash.withCharacterPrecision(39.69731931820395-0.003,116.06873609678199+0.003, 6);
		System.out.println(geoHash.toString());
		geoHash = GeoHash.withCharacterPrecision(39.69731931820395-0.003,116.06873609678199-0.003, 6);
		System.out.println(geoHash.getBoundingBox().getLatitudeSize());
	}

	


	private static void test1() {
		GeoHash geoHash = GeoHash.withCharacterPrecision(39.69731931820395,116.06873609678199, 5);       
        
        System.out.println(geoHash.toString());
        System.out.println("格子号："+geoHash.toBase32());
        System.out.println("格子中心："+geoHash.getBoundingBoxCenterPoint());
        System.out.println("格子边界："+geoHash.getBoundingBox());
        
        for (GeoHash one : geoHash.getAdjacent()) {
            System.out.println(one.toString());
        }
		
	}
	
	

}
