/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.ui.renderer.CategoryTableCellRenderer;

/**
 * @date 25.3.2010
 * @author Kojotak 
 */
public class CategoryColumn extends BaseColumn<Game,Category> {

	private static final long serialVersionUID = 1L;

	@Override
	public Category getValue(Game source) {
		return source.getCategory();
	}
		
	public CategoryColumn() {
		super();
		this.setCellRenderer(new CategoryTableCellRenderer());
	}
	
	@Override
	public Class<Category> getType() {
		return Category.class;
	}
}
