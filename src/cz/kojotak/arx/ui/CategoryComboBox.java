/**
 * 
 */
package cz.kojotak.arx.ui;

import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.enums.LegacyCategory;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.renderer.NamedWithIdListCellRenderer;

/**
 * @date 21.4.2010
 * @author Kojotak
 */
public class CategoryComboBox extends JComboBox<Category> {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(getClass().getName());

	public CategoryComboBox() {
		super();
		AnnotationProcessor.process(this);
		this.setMaximumRowCount(20);
		this.setRenderer(new NamedWithIdListCellRenderer<Category>());
		this.addActionListener(e-> {
				CategoryComboBox box = (CategoryComboBox) e.getSource();
				Category selected = (Category) box.getSelectedItem();
				FilterModel filterModel = new FilterModel();
				filterModel.setCategory(selected);
				logger.info("filtering by " + selected);
				EventBus.publish(filterModel);
		});
		updateCategoryListModel(Application.getInstance().getCurrentMode());//XXX delete this and fire mode changed after GUI initialization
	}

	@EventSubscriber
	public void updateCategoryListModel(Mode mode) {
		Application app = Application.getInstance();
		Set<Category> catSet = mode.getCategories();
		Vector<Category> v = new Vector<Category>();
		v.add(LegacyCategory.VSECHNY.toCategory());
		v.addAll(catSet);
		ComboBoxModel<Category> model = new DefaultComboBoxModel<>(v);
		this.setModel(model);
	}

}
