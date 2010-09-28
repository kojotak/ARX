/**
 * 
 */
package cz.kojotak.arx.util;

/**
 * @date 28.9.2010
 * @author Kojotak
 */
public enum StorageUnit {
	BYTE(1L, "B"), 
	KILOBYTE(1L << 10, "KB"), 
	MEGABYTE(1L << 20, "MB"), 
	GIGABYTE(1L << 30, "GB"),
	TERABYTE(1L << 40, "TB"), 
	PETABYTE(1L << 50, "PB");

	private long divider;
	private String name;

	private StorageUnit(long divider, String name) {
		this.divider = divider;
		this.name = name;
	}
	
	public static StorageUnit of(long number){
		StorageUnit unit = BYTE;
		for(StorageUnit u:StorageUnit.values()){
			if(u.divider>number){
				break;
			}
			unit=u;
		}
		return unit;
	}
	
	public static String toString(long number){
		StorageUnit unit = of(number);
		long whole = number / unit.divider;
		//long reminder = number % unit.divider;
		return whole+unit.name;
	}

	public static void main(String... args){
		for(StorageUnit unit:StorageUnit.values()){
			System.out.println(unit.name+" "+unit.divider);
		}
		
		for(long l:new long[]{0L,1L,5L,1023L,1024L,1025L,1048575L,1048576L,1048577L}){
			System.out.println(""+l+" "+of(l).name+" "+toString(l));			
		}
	}
}
