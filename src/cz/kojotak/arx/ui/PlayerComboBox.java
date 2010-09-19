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
import cz.kojotak.arx.ui.event.RebuiltGameTable;
import cz.kojotak.arx.ui.renderer.PlayerListRenderer;

/**
 * @date 19.4.2010
 * @author Kojotak
 */
public class PlayerComboBox extends JComboBox {

	private static final long serialVersionUID = -8481423043530284950L;
	public static final String ADD_NEW="_NEW";

	public PlayerComboBox() {
		super();
		this.setModel(getUsersComboBox());
		this.setEditable(false);
		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox) event.getSource();
				String usrName = (String) cb.getSelectedItem();
				if (PlayerComboBox.ADD_NEW.equals(usrName)) {
					Application.getInstance().getLogger(this).info(
							"TODO add new player...");
					usrName = null;
				}
				Application.getInstance().setPlayer(usrName);
				EventBus.publish(new RebuiltGameTable());
				Application.getInstance().getLogger(this).info(
						"selected user " + usrName);

			}

		});
		this.setRenderer(new PlayerListRenderer());
		this.setPreferredSize(new Dimension(this.getPreferredSize().width,28));
	}


	private ComboBoxModel getUsersComboBox() {
		Application app = Application.getInstance();
		List<String> usrNames = app.getPlayers();
		usrNames.add(ADD_NEW);
		ComboBoxModel model = new DefaultComboBoxModel(new Vector<String>(usrNames));
		model.setSelectedItem(app.getCurrentUser().getId());
		return model;
	}
}
