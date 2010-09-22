/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 946035265025633452L;

	final JTextField input;

	public SearchPanel(final GameTable table) {
		super();
		JLabel label = new JLabel();
		label.setText(Application.getInstance().getLocalization().getString(
				this, "LABEL"));
		input = new JTextField();
		input.setColumns(10);
		input.setText("");
		input.getDocument().addDocumentListener(new DocumentListener() {
			private void onChange(DocumentEvent e) {
				String str = SearchPanel.this.input.getText();
				FilterModel filterModel = new FilterModel();
				filterModel.setSearch(str);
				Application app = Application.getInstance();
				app.getLogger(this).info("filtering by search string: " + str);
				table.updateGameFilter(filterModel);
			}

			public void changedUpdate(DocumentEvent e) {
				onChange(e);
			}

			public void removeUpdate(DocumentEvent e) {
				onChange(e);
			}

			public void insertUpdate(DocumentEvent e) {
				onChange(e);
			}
		});

		JLabel icon = new JLabel(Application.getInstance().getIconLoader()
				.tryLoadIcon(GUIIcons.SEARCH));
		this.setLayout(new BorderLayout());
		// this.add(label, BorderLayout.WEST);//bez popisku je to hezci
		this.add(input, BorderLayout.CENTER);
		this.add(icon, BorderLayout.EAST);
	}

}
