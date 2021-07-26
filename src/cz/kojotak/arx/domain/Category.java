/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Reverse engineered rotaxmame categories
 * @date 14.10.2009
 * @author Kojotak 
 */
public enum Category {
	
	//zatim prazdne, cisla neodpovidaji
	ADVENTURY(1001),
	DEMA(1002),
	FPS(1003),
	KARETNI(1004),
	MMORG(1005),
	ONLINE(1006),
	RPG(1007),
	STRATEGIE(1008),
	TEXTOVKY(1009),
	
	//moje pridane
	NEROZPOZNANE(666),
	
	/**
	 * represents all categories...
	 */
	VSECHNY(123),
	
	//rotaxi kategorie
	ZAVODNI(1),
	BOJOVE(2),
	BLUDISTOVKY(3),
	RUZNE(4),
	PINBALY(5),
	PLOSINOVKY(6),
	LOGICKE(7),
	STRILECKY(8),
	LETADLA(9),
	SPORTOVNI(10),
	MLATICKY(11),
	EROTICKE(12),
	HUDEBNI(16),
	NEZARAZENE(50),	
	;
	
	/**
	 * kod kategorie, kterou pouziva Rotacak
	 */
	public final Integer code;
		
	private Category(Integer code){
		this.code = code;
	}
	
	static Map<Integer,Category> codes=new HashMap<Integer,Category>();
	static{
		for(Category cat:Category.values()){
			codes.put(cat.code, cat);
		}
	}
	public static Category resolve(Integer code){
		Category cat = codes.get(code);
		return cat!=null?cat:NEROZPOZNANE;
	}
	public static Category resolve(String code){
		return resolve(Integer.parseInt(code));
	}
}
