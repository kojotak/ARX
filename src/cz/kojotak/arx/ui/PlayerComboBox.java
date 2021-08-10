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
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.ui.renderer.PlayerListRenderer;

/**
 * @date 19.4.2010
 * @author Kojotak
 */
public class PlayerComboBox extends JComboBox<User> {

	private static final long serialVersionUID = -8481423043530284950L;
	public static final String ADD_NEW="_NEW";

	public PlayerComboBox() {
		super();
		this.setModel(createModel());
		this.setEditable(false);
		this.addActionListener(event->{
			JComboBox<User> cb = (JComboBox) event.getSource();
			User usrName = (User) cb.getSelectedItem();
			Application.getInstance().setCurrentUser(usrName);
			EventBus.publish(Application.getInstance().getCurrentUser());
			Application.getLogger(this).info("selected user " + usrName);
		});
		this.setRenderer(new PlayerListRenderer());
		this.setPreferredSize(new Dimension(this.getPreferredSize().width,28));
	}


	private ComboBoxModel<User> createModel() {
		Application app = Application.getInstance();
		List<User> usrNames = app.getPlayers();
		//usrNames.add(ADD_NEW);
		ComboBoxModel<User> model = new DefaultComboBoxModel<User>(new Vector<User>(usrNames));
		model.setSelectedItem(app.getCurrentUser());
		return model;
	}
}
