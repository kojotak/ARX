/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import lombok.Getter;
import lombok.Setter;
import cz.kojotak.arx.domain.Record;


/**
 * @date 26.1.2010
 * @author Kojotak 
 */
public class RecordImpl implements Record {

	@Getter
	@Setter
	protected Long score;
	
	@Getter
	@Setter
	protected Float rating;
	
	@Getter
	@Setter
	protected Boolean finished;
	
	@Getter
	@Setter
	protected String player;
	
	@Getter
	@Setter
	protected Integer duration;

	@Getter
	@Setter
	protected Integer position;
	
	public RecordImpl() {
		super();
	}	

	@Override
	public Boolean isFinished() {
		return getFinished();
	}
	
}
