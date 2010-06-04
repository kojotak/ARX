/**
 * 
 */
package cz.kojotak.arx.domain.enums;

import lombok.Getter;

/**
 * Supported game platforms
 * @date 2.6.2010
 * @author Kojotak 
 */
public enum Platform {
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
	
	@Getter
	private String emulator;
		
	private Platform(String emulator) {
		this.emulator = emulator;
	}


	/**
	 * Resolve known string representations of platform into {@link Platform} itself
	 * @param string
	 * @return resolved {@link Platform} or null if not resolved
	 */
	public static Platform resolve(String string){
		for(Platform p:Platform.values()){
			String s = p.getEmulator();
			if(s==null){
				continue;
			}
			if(s.equals(string)){
				return p;
			}
		}
		return Platform.ALL;
	}

}
