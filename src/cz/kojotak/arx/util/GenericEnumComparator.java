/**
 * 
 */
package cz.kojotak.arx.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import cz.kojotak.arx.domain.Language;

/**
 * Enum comparator based on enum's names
 * @date 16.5.2010
 * @author Kojotak 
 */
public class GenericEnumComparator implements Comparator<Enum<?>> {

	Collator col=null;
	public GenericEnumComparator(Language lang) {
		super();
		Locale loc = lang.getLocale();
		col = Collator.getInstance(loc);
	}

	@Override
	public int compare(Enum<?> e1, Enum<?> e2) {
		String s1=e1!=null?e1.name():"";
		String s2=e2!=null?e2.name():"";
		return col.compare(s1,s2);
	}

}
