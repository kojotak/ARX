/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Collections;
import java.util.List;

import cz.kojotak.arx.domain.AmigaMetaData;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.impl.Record;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.LegacyPlatform;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class AmigaGame extends LegacyCompetitiveGame implements Competetive,AmigaMetaData,WithStatistics {

	protected List<Record> records;
	private String md5Disk1;
	private String md5Cfg;
	private String md5Start;
	
	public AmigaGame(String id,Category category, String title, String file) {
		super(id,category, LegacyPlatform.AMIGA, title, file);
		records = Collections.emptyList();
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
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
