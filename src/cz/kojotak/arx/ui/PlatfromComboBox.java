/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.PlatformListCellRenderer;

/**
 * @date 2.6.2010
 * @author Kojotak
 */
public class PlatfromComboBox extends JComboBox<Platform> {

	private static final long serialVersionUID = 4089373502308114318L;

	public PlatfromComboBox() {
		super();
		AnnotationProcessor.process(this);
		this.setMaximumRowCount(LegacyPlatform.values().length);
		//this.setRenderer(new GenericEnumListRenderer<Platform>(null, Platform.class));
		this.setRenderer(new PlatformListCellRenderer());
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlatfromComboBox box = (PlatfromComboBox) e.getSource();
				LegacyPlatform selected = (LegacyPlatform) box.getSelectedItem();
				FilterModel filterModel = new FilterModel();
				filterModel.setPlatform(selected);
				Application.getLogger(PlatfromComboBox.this).info("filtering by " + selected);
				EventBus.publish(filterModel);
			}

		});
		
		updateListModel(Application.getInstance().getCurrentMode());//XXX delete this and fire mode changed after GUI initialization
	}

	@EventSubscriber
	public void updateListModel(Mode<?> mode) {
		Set<Platform> platforms = mode.getPlatforms();
		Vector<Platform> v = new Vector<Platform>();
		v.add(LegacyPlatform.ALL.toPlatform());
		v.addAll(platforms);
		ComboBoxModel<Platform> model = new DefaultComboBoxModel<Platform>(v);
		this.setModel(model);
	}

}
