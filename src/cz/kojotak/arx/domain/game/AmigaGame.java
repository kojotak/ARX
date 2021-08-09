/**
 * 
 */
package cz.kojotak.arx.domain.game;

import cz.kojotak.arx.domain.AmigaMetaData;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.game.Game;
/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class AmigaGame extends CompetitiveGame implements AmigaMetaData,WithStatistics {

	private String md5Disk1;
	private String md5Cfg;
	private String md5Start;
	
	public AmigaGame(String id,Category category, String title, String file) {
		super(id,category, LegacyPlatform.AMIGA, title, file);
	}

	public String getMd5Disk1() {
		return md5Disk1;
	}

	public void setMd5Disk1(String md5Disk1) {
		this.md5Disk1 = md5Disk1;
	}

	public String getMd5Cfg() {
		return md5Cfg;
	}

	public void setMd5Cfg(String md5Cfg) {
		this.md5Cfg = md5Cfg;
	}

	public String getMd5Start() {
		return md5Start;
	}

	public void setMd5Start(String md5Start) {
		this.md5Start = md5Start;
	}	
	
}
