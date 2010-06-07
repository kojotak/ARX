/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import java.util.List;

import lombok.Getter;

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
	
	@Getter	Integer playerPosition=null;
	@Getter	Float playerRating=null;
	
	@Getter	Float averageRating=null;
	@Getter	Float playerRelativePosition=null;
	@Getter	String playerSign=null;
	
	@Getter String bestPlayer=null;
	@Getter	int recordsCount=0;
	@Getter	int ratingsCount=0;
	@Getter Boolean playerFinished=null;
	@Getter Boolean somebodyFinished=false;
	
	@Getter	Long highestScore=null;
	@Getter	Long userScore=null;
	
	@Getter Integer oponentDiff=null;
	
	@Getter User oponent=null;
	@Getter	Integer points=null;
		
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
}
