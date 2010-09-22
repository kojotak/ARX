/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.enums.Platform;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.GenericEnumListRenderer;
import cz.kojotak.arx.util.GenericEnumComparator;

/**
 * @date 2.6.2010
 * @author Kojotak
 */
public class PlatfromComboBox extends JComboBox {

	private static final long serialVersionUID = 4089373502308114318L;

	public PlatfromComboBox(final GameTable table) {
		super();
		updateListModel();
		this.setMaximumRowCount(Platform.values().length);
		this.setRenderer(new GenericEnumListRenderer<Platform>(null,
				Platform.class));
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlatfromComboBox box = (PlatfromComboBox) e.getSource();
				Platform selected = (Platform) box.getSelectedItem();
				if (Platform.ALL.equals(selected)) {
					selected = null;
				}
				FilterModel filterModel = new FilterModel();
				filterModel.setPlatform(selected);
				Application app = Application.getInstance();
				app.getLogger(PlatfromComboBox.this).info(
						"filtering by " + selected);

				table.updateGameFilter(filterModel);
			}

		});
	}

	public void updateListModel() {
		Application app = Application.getInstance();
		Set<Platform> platforms = app.getCurrentMode().getPlatforms();
		Vector<Platform> v = new Vector<Platform>();
		List<Platform> sorted = new ArrayList<Platform>(platforms);
		Collections.sort(sorted, new GenericEnumComparator(app.getLanguage()));
		v.add(Platform.ALL);
		v.addAll(sorted);
		ComboBoxModel model = new DefaultComboBoxModel(v);
		this.setModel(model);
	}

}
