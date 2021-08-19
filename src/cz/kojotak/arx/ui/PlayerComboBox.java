/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.bushe.swing.event.EventBus;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Player;
import cz.kojotak.arx.ui.renderer.PlayerListRenderer;

/**
 * @date 19.4.2010
 * @author Kojotak
 */
public class PlayerComboBox extends JComboBox<Player> {

	private static final long serialVersionUID = -8481423043530284950L;
	public static final String ADD_NEW="_NEW";

	public PlayerComboBox() {
		super();
		this.setModel(createModel());
		this.setEditable(false);
		this.addActionListener(event->{
			JComboBox<Player> cb = (JComboBox) event.getSource();
			Player usrName = (Player) cb.getSelectedItem();
			Application.getInstance().setCurrentPlayer(usrName);
			EventBus.publish(Application.getInstance().getCurrentPlayer());
		});
		this.setRenderer(new PlayerListRenderer());
		this.setPreferredSize(new Dimension(this.getPreferredSize().width,28));
	}


	private ComboBoxModel<Player> createModel() {
		Application app = Application.getInstance();
		List<Player> usrNames = app.getPlayers();
		//usrNames.add(ADD_NEW);
		ComboBoxModel<Player> model = new DefaultComboBoxModel<Player>(new Vector<Player>(usrNames));
		model.setSelectedItem(app.getCurrentPlayer());
		return model;
	}
}
