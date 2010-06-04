/**
 * 
 */
package cz.kojotak.arx.domain.game;

import lombok.Getter;
import lombok.Setter;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Record2P;

/**
 * @date 24.1.2010
 * @author Kojotak 
 */
public class MameGameDouble extends BaseMameGame<Record2P> {

	public MameGameDouble(String id,Category category, String title, String file) {
		super(id,category, title, file);
	}

	@Getter
	@Setter
	protected String secondPlayerSign;

}
