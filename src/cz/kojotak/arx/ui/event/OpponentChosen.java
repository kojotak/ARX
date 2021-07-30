/**
 * 
 */
package cz.kojotak.arx.ui.event;

import java.util.Objects;

import cz.kojotak.arx.domain.User;

/**
 * @date 28.3.2011
 * @author Kojotak 
 */
public class OpponentChosen {

	private User opponent;

	public User getOpponent() {
		return opponent;
	}

	public void setOpponent(User opponent) {
		this.opponent = opponent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(opponent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpponentChosen other = (OpponentChosen) obj;
		return Objects.equals(opponent, other.opponent);
	}
	
}
