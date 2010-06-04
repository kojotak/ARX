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
	String bestPlayerSign=null;
	int recordsCount=0;
	int ratingsCount=0;
	Boolean playerFinished=null;
	Boolean somebodyFinished=false;
	Long highestScore=null;
	Long playerScore=null;
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
	
	public Integer getOponentDiff() {
		return oponentDiff;
	}
	
	public User getOponent() {
		return oponent;
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
				playerScore = score;
			}
			if(score>highest){
				highest=score;
				bestPlayerSign=sign;
			}
			if(record.isFinished()){
				somebodyFinished=true;
			}
			if(record.getRating()!=null){
				ratingsCount++;
				ratingsSum+=record.getRating();
			}
			if(sign.equals(oponent.getId())){
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
			points=(recordsCount-playerPosition)*10 + (int)((float)playerScore*100/highestScore);
		}
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerPosition()
	 */
	@Override
	public Integer getPlayerPosition() {
		return playerPosition;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerRating()
	 */
	@Override
	public Float getPlayerRating() {
		return playerRating;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerRelativePosition()
	 */
	@Override
	public Float getPlayerRelativePosition() {
		return playerRelativePosition;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerSign()
	 */
	@Override
	public String getPlayerSign() {
		return playerSign;
	}

	@Override
	public Float getAverageRating() {
		return averageRating;
	}

	@Override
	public int getRecordsCount() {
		return recordsCount;
	}

	@Override
	public int getRatingsCount() {
		return ratingsCount;
	}

	@Override
	public Boolean hasPlayerFinished() {
		return playerFinished;
	}

	@Override
	public Boolean hasSomebodyFinished() {
		return somebodyFinished;
	}

	@Override
	public Long getHighestScore() {
		return highestScore;
	}

	@Override
	public Long getUserScore() {
		return playerScore;
	}

	@Override
	public String getBestPlayer() {
		return bestPlayerSign;
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

	@Override
	public Integer getPoints() {
		return points;
	}
}
