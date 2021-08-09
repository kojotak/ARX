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

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.GenericEnumListRenderer;
import cz.kojotak.arx.util.GenericEnumComparator;

/**
 * @date 2.6.2010
 * @author Kojotak
 */
public class PlatfromComboBox extends JComboBox<LegacyPlatform> {

	private static final long serialVersionUID = 4089373502308114318L;

	public PlatfromComboBox() {
		super();
		AnnotationProcessor.process(this);
		this.setMaximumRowCount(LegacyPlatform.values().length);
		this.setRenderer(new GenericEnumListRenderer<LegacyPlatform>(null,
				LegacyPlatform.class));
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlatfromComboBox box = (PlatfromComboBox) e.getSource();
				LegacyPlatform selected = (LegacyPlatform) box.getSelectedItem();
				FilterModel filterModel = new FilterModel();
				filterModel.setPlatform(selected);
				Application app = Application.getInstance();
				app.getLogger(PlatfromComboBox.this).info(
						"filtering by " + selected);
				EventBus.publish(filterModel);
			}

		});
		
		updateListModel(Application.getInstance().getCurrentMode());//XXX delete this and fire mode changed after GUI initialization
	}

	@EventSubscriber
	public void updateListModel(Mode<?> mode) {
		Application app = Application.getInstance();
		Set<LegacyPlatform> platforms = mode.getPlatforms();
		Vector<LegacyPlatform> v = new Vector<LegacyPlatform>();
		List<LegacyPlatform> sorted = new ArrayList<LegacyPlatform>(platforms);
		Collections.sort(sorted, new GenericEnumComparator(app.getLanguage()));
		v.add(LegacyPlatform.ALL);
		v.addAll(sorted);
		ComboBoxModel<LegacyPlatform> model = new DefaultComboBoxModel<LegacyPlatform>(v);
		this.setModel(model);
	}

}
