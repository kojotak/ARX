/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import cz.kojotak.arx.domain.User;


public class Record {

	protected Long score;
	protected Float rating;
	protected Boolean finished;
	protected Integer duration;
	protected Integer position;
	protected User player;
	private User secondPlayer;

	public Boolean isFinished() {
		return getFinished();
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

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
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

	public User getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(User secondPlayer) {
		this.secondPlayer = secondPlayer;
	}
}
