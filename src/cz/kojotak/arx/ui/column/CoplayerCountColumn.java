package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Player;

public class CoplayerCountColumn extends BaseColumn<Game,Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer getValue(Game game) {
		Player player = game.getStatistics().getPlayer();
		long count = game.getRecords2P().stream().filter( s -> player.equals(s.player()) || player.equals(s.secondPlayer()) ).count();
		return count > 0 ? (int)count : null;
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

}
