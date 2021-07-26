/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import java.util.List;


import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.enums.FinishedStatus;

/**
 * @date 7.2.2010
 * @author Kojotak 
 */
public class SingleGameStatistics implements GameStatistics {
	
	Integer playerPosition=null;
	Float playerRating=null;
	
	Float averageRating=null;
	Float playerRelativePosition=null;
	String playerSign=null;
	
	String bestPlayer=null;
	int recordsCount=0;
	int ratingsCount=0;
	Boolean playerFinished=null;
	Boolean somebodyFinished=false;
	
	Long highestScore=null;
	Long userScore=null;
	
	Integer oponentDiff=null;
	
	User oponent=null;
	Integer points=null;
		
	public SingleGameStatistics(Competetive<Record> game,User user,User oponent) {
		super();
		List<Record> records = game.getRecords();
		this.oponent=oponent;
		if(records!=null && records.size()>0){
			init(records,user,oponent);
		}
	}

	private void init(List<Record> records,User user,User oponent){
		recordsCount=records.size();
		Float ratingsSum=0F;
		Long highest = 0L;
		playerSign = user.getId();
		Integer oponentPosition=null;
		for(int i=0;i<records.size();i++){
			Record record = records.get(i);
			String sign = record.getPlayer();
			Long score=record.getScore();
			if(sign.equals(user.getId())){
				playerPosition=i+1;
				playerRating = record.getRating();
				playerFinished = record.isFinished();
				userScore = score;
			}
			if(score>highest){
				highest=score;
				bestPlayer=sign;
			}
			if(record.isFinished()){
				somebodyFinished=true;
			}
			if(record.getRating()!=null){
				ratingsCount++;
				ratingsSum+=record.getRating();
			}
			if(oponent!=null && sign.equals(oponent.getId())){
				oponentPosition=record.getPosition();
			}
		}
		if(ratingsCount>0){
			averageRating=ratingsSum/ratingsCount;
		}
		if(recordsCount>0){
			highestScore=highest;
		}
		if(playerPosition!=null){
			playerRelativePosition=100F - (100F * (playerPosition-1) / recordsCount);
			playerRelativePosition = new Float(Math.round(playerRelativePosition));
			if(oponentPosition!=null){
				this.oponentDiff=oponentPosition-playerPosition;
			}
			points=(recordsCount-playerPosition)*10 + (int)((float)userScore*100/highestScore);
		}
	}

	@Override
	public FinishedStatus getFinishedStatus() {
		if(this.playerFinished!=null && this.playerFinished){
			return FinishedStatus.CURRENT_PLAYER_FINISHED;
		}else if(this.somebodyFinished!=null && this.somebodyFinished){
			return FinishedStatus.SOME_BODY_FINISHED;
		}else{
			return null;
		}
	}

	public Integer getPlayerPosition() {
		return playerPosition;
	}

	public Float getPlayerRating() {
		return playerRating;
	}

	public Float getAverageRating() {
		return averageRating;
	}

	public Float getPlayerRelativePosition() {
		return playerRelativePosition;
	}

	public String getPlayerSign() {
		return playerSign;
	}

	public String getBestPlayer() {
		return bestPlayer;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public int getRatingsCount() {
		return ratingsCount;
	}

	public Boolean getPlayerFinished() {
		return playerFinished;
	}

	public Boolean getSomebodyFinished() {
		return somebodyFinished;
	}

	public Long getHighestScore() {
		return highestScore;
	}

	public Long getUserScore() {
		return userScore;
	}

	public Integer getOponentDiff() {
		return oponentDiff;
	}

	public User getOponent() {
		return oponent;
	}

	public Integer getPoints() {
		return points;
	}
	
}
