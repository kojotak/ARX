/**
 *
 */
package cz.kojotak.arx.domain;

import java.util.Locale;

/**
 * @author TBe
 */
public enum Language {
	CZECH(new Locale("CS")),
	ENGLISH(Locale.ENGLISH)
	;

	private Locale loc;

	private Language(Locale loc) {
		this.loc = loc;
	}

	public Locale getLocale(){
		return loc;
	}
}
