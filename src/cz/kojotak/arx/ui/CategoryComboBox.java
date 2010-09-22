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
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.GenericEnumListRenderer;
import cz.kojotak.arx.util.GenericEnumComparator;

/**
 * @date 21.4.2010
 * @author Kojotak
 */
public class CategoryComboBox extends JComboBox {

	private static final long serialVersionUID = 8070450403361850796L;

	public CategoryComboBox(final GameTable table) {
		super();
		updateCategoryListModel();
		this.setMaximumRowCount(20);
		this.setRenderer(new GenericEnumListRenderer<Category>(null,
				Category.class));
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CategoryComboBox box = (CategoryComboBox) e.getSource();
				Category selected = (Category) box.getSelectedItem();
				FilterModel filterModel = new FilterModel();
				filterModel.setCategory(selected);
				Application app = Application.getInstance();
				app.getLogger(CategoryComboBox.this).info(
						"filtering by " + selected);

				table.updateGameFilter(filterModel);

			}

		});
	}

	public void updateCategoryListModel() {
		Application app = Application.getInstance();
		Set<Category> catSet = app.getCurrentMode().getCategories();
		Vector<Category> v = new Vector<Category>();
		v.add(Category.VSECHNY);
		List<Category> sorted = new ArrayList<Category>(catSet);
		Collections.sort(sorted, new GenericEnumComparator(app.getLanguage()));
		v.addAll(sorted);
		ComboBoxModel model = new DefaultComboBoxModel(v);
		this.setModel(model);
	}

}
