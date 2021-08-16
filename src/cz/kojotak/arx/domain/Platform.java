package cz.kojotak.arx.domain;

import cz.kojotak.arx.common.Identifiable;
import cz.kojotak.arx.common.Named;

public record Platform(int id, 
		String name, 
		String link, 
		int modeMax) implements Identifiable, Named {
}
