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
	ALL(null), //0
	ATARI("atari"), //1
	ATARI2600("atari2600"), //2
	AMIGA("amiga"), //3
	C64("c64"), //4
	COLECO("coleco"), //5
	PC_DOS("pc"), //6
	FLASH("flash"), //7
	GAMEGEAR("gamegear"), //8
	INTELLIVISION("intellivision"), //9
	GAMEBOY("gameboy"), //10
	LYNX("lynx"), //11
	MAME("mame"), //12
	MASTERSYSTEM("mastersystem"), //13
	MEGADRIVE("megadrive"), //14
	MSX("msx"), //15
	N64("n64"), //16
	NEOGEO("neogeop"), //17
	NES("nes"), //18
	PCENGINE("pcengine"), //18
	SNES("snes"), //19
	ZX("zx") //20
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
