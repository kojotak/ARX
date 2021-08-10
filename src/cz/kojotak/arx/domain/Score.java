/**
 * 
 */
package cz.kojotak.arx.domain;

public record Score (
		long score,
		Float rating,
		boolean finished,
		Integer duration,
		Integer position,
		User player,
		User secondPlayer
		){
}
