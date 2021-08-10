/**
 * 
 */
package cz.kojotak.arx.domain.enums;

import java.util.HashMap;
import java.util.Map;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;

/**
 * Reverse engineered rotaxmame categories
 * @date 14.10.2009
 * @author Kojotak 
 */
public enum LegacyCategory {
	
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
		
	private LegacyCategory(Integer code){
		this.code = code;
	}
	
	public Category toCategory() {
		String text = Application.getInstance().getLocalization().getString("Category", this.name());
		return new Category(this.code, text);
	}
	
	static Map<Integer,LegacyCategory> codes=new HashMap<Integer,LegacyCategory>();
	static{
		for(LegacyCategory cat:LegacyCategory.values()){
			codes.put(cat.code, cat);
		}
	}
	public static LegacyCategory resolve(Integer code){
		LegacyCategory cat = codes.get(code);
		return cat!=null?cat:NEROZPOZNANE;
	}
	public static LegacyCategory resolve(String code){
		return resolve(Integer.parseInt(code));
	}
}
