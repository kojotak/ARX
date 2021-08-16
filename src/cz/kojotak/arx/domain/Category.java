package cz.kojotak.arx.domain;

import cz.kojotak.arx.common.Identifiable;
import cz.kojotak.arx.common.Named;

public record Category(
		int id, 
		String name) implements Identifiable, Named {
}
