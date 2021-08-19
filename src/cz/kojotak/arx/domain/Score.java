/**
 * 
 */
package cz.kojotak.arx.domain;

public record Score (
		long score,
		Integer points,
		Float rating,
		boolean finished,
		Integer duration,
		Integer position,
		Player player,
		Player secondPlayer
		){
}
