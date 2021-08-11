package cz.kojotak.arx;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.User;

public interface Importer extends RunnableWithProgress {

	Collection<User> getPlayers();

	List<Game> getSinglePlayerGames();

	List<Game> getDoublePlayerGames();

	Date getLastUpdate();
}
