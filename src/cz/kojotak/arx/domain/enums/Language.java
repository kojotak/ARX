/**
 *
 */
package cz.kojotak.arx.domain.enums;

import java.util.Locale;

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
