/**
 * 
 */
package cz.kojotak.arx.domain.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
@EqualsAndHashCode
public abstract class SimpleGame implements Game {

	@Getter
	private Category category;
	
	@Getter
	private String title;
	
	@Getter
	private String file;
	
	@Getter
	private String id;
	
	@Getter
	@Setter
	private Float averageRatings;
	
	public SimpleGame(String id,Category category, String title, String file) {
		super();
		this.category = category;
		this.file = file;
		this.title = title;
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+title;
	}
	
}
