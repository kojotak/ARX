/**
 * 
 */
package cz.kojotak.arx.domain.game;

import cz.kojotak.arx.domain.Category;

/**
 * @date 24.1.2010
 * @author Kojotak 
 */
public class MameGameDouble extends BaseMameGame {

	public MameGameDouble(String id,Category category, String title, String file) {
		super(id,category, title, file);
	}

	private String secondPlayerSign;

	public String getSecondPlayerSign() {
		return secondPlayerSign;
	}

	public void setSecondPlayerSign(String secondPlayerSign) {
		this.secondPlayerSign = secondPlayerSign;
	}

}
