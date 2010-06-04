/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import cz.kojotak.arx.domain.Record;


/**
 * @date 26.1.2010
 * @author Kojotak 
 */
public class RecordImpl implements Record {

	protected Long score;
	protected Float rating;
	protected Boolean finished;
	protected String player;
	protected Integer duration;
	protected Integer position;
	public RecordImpl() {
		super();
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public Boolean getFinished() {
		return finished;
	}
	public Boolean isFinished(){
		return getFinished();
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
