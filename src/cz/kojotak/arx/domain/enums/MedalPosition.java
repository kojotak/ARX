/**
 * 
 */
package cz.kojotak.arx.domain.enums;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public enum MedalPosition {
	GOLD,SILVER,BRONZE;
		
	public static MedalPosition resolveFrom(Integer position){
		if(position==null){
			//avoid NPE during out boxing
			return null;
		}
		switch(position){
			case 1: return GOLD;
			case 2: return SILVER;
			case 3: return BRONZE;
			default: return null;
		}
	}
}
