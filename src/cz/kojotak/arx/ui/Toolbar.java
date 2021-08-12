package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.ui.icon.GUIIcons;
import cz.kojotak.arx.ui.listener.ModeChangeListener;
import cz.kojotak.arx.ui.renderer.IconizedTextListRenderer;

public class Toolbar extends JPanel {

	private static final long serialVersionUID = -1150251988753412200L;
	private JComboBox<String> vyberModu;
	private JComboBox<User> vyberHrace;

	public Toolbar(final MainWindow window,final CategoryComboBox combo) {
		super();

		//initialize combos
		ComboBoxModel<String> modelVyberModu = createGameModeComboBox();
		vyberModu = new JComboBox();
		vyberModu.setModel(modelVyberModu);
		Icon joyIcon = Application.getInstance().getIconLoader().tryLoadIcon(GUIIcons.JOY);
		IconizedTextListRenderer rndr = new IconizedTextListRenderer(joyIcon);
		vyberModu.setRenderer(rndr);
		vyberModu.addActionListener(new ModeChangeListener(window));
		vyberModu.setPreferredSize(new Dimension(120,28));
		vyberHrace = new PlayerComboBox();

		//initialize holder panels
		JPanel leftPart = new JPanel();
		JPanel rightPart = new JPanel();
		JPanel middlePart = new JPanel();
		leftPart.setLayout(new FlowLayout(FlowLayout.RIGHT));
		middlePart.setLayout(new FlowLayout(FlowLayout.LEFT));
		rightPart.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftPart.setPreferredSize(new Dimension(256,leftPart.getPreferredSize().height));

		//assemble components
		leftPart.add(vyberModu);
		leftPart.add(vyberHrace);
		middlePart.add(new UpdateComboBox());
		middlePart.add(new ChatButton(window));
		middlePart.add(new SettingsButton());
		middlePart.add(new HelpComboBox());
		rightPart.add(new SearchPanel());

		//add components
		this.setLayout(new BorderLayout());
		this.add(leftPart, BorderLayout.WEST);
		this.add(middlePart,BorderLayout.CENTER);
		this.add(rightPart, BorderLayout.EAST);
	}

	private ComboBoxModel<String> createGameModeComboBox() {
		Application app = Application.getInstance();
		List<Mode> modes = app.getModes();
		String[] names = new String[modes.size()];
		for (int i = 0; i < modes.size(); i++) {
			names[i] = modes.get(i).getName();
		}
		ComboBoxModel<String> model = new DefaultComboBoxModel(names);
		model.setSelectedItem(app.getCurrentMode().getName());
		return model;
	}


}
