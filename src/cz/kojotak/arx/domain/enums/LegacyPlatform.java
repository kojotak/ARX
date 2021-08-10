/**
 * 
 */
package cz.kojotak.arx.domain.enums;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Platform;

/**
 * Supported game platforms
 * @date 2.6.2010
 * @author Kojotak 
 */
public enum LegacyPlatform {
	ALL(null),
	ATARI("atari"),
	ATARI2600("atari2600"),
	AMIGA("amiga"),
	C64("c64"),
	COLECO("coleco"),
	PC_DOS("pc"),
	FLASH("flash"),
	GAMEGEAR("gamegear"),
	INTELLIVISION("intellivision"),
	GAMEBOY("gameboy"),
	LYNX("lynx"),
	MAME("mame"),
	MASTERSYSTEM("mastersystem"),
	MEGADRIVE("megadrive"),
	MSX("msx"),
	N64("n64"),
	NEOGEO("neogeop"),
	NES("nes"),
	PCENGINE("pcengine"),
	SNES("snes"),
	ZX("zx")
	;
	
	private String emulator;
		
	private LegacyPlatform(String emulator) {
		this.emulator = emulator;
	}

	public String getEmulator() {
		return emulator;
	}
	
	public Platform toPlatform() {
		String text = Application.getInstance().getLocalization().getString("LegacyPlatform", this.name());
		return new Platform(this.ordinal(), text, this.getEmulator(), MAME.equals(this) ? 2 : 1);
	}

	/**
	 * Resolve known string representations of platform into {@link LegacyPlatform} itself
	 * @param string
	 * @return resolved {@link LegacyPlatform} or null if not resolved
	 */
	public static LegacyPlatform resolve(String string){
		for(LegacyPlatform p:LegacyPlatform.values()){
			String s = p.getEmulator();
			if(s==null){
				continue;
			}
			if(s.equals(string)){
				return p;
			}
		}
		return LegacyPlatform.ALL;
	}

}
