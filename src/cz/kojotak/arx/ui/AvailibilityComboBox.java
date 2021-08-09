/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.bushe.swing.event.EventBus;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.enums.Availibility;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.GenericEnumListRenderer;
import cz.kojotak.arx.util.AvailibilityComparator;

/**
 * @date 19.5.2010
 * @author Kojotak
 */
public class AvailibilityComboBox extends JComboBox<Availibility> {

	private static final long serialVersionUID = 9214892993791504736L;

	public AvailibilityComboBox() {
		super();
		Availibility[] values = Availibility.values();
		Arrays.sort(values, new AvailibilityComparator());
		this.setModel(new DefaultComboBoxModel<Availibility>(values));
		this.setMaximumRowCount(values.length);
		this.setRenderer(new GenericEnumListRenderer<Availibility>(null,Availibility.class));
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AvailibilityComboBox box = (AvailibilityComboBox) e.getSource();
				Availibility av = (Availibility) box.getSelectedItem();
				FilterModel filterModel = new FilterModel();
				filterModel.setAvailibility(av);
				Application.getLogger(AvailibilityComboBox.this)
						.info("availibility set to " + av);
				EventBus.publish(filterModel);				
			}

		});
	}
}
