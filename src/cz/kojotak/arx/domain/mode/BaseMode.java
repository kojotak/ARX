/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.Collections;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.Searchable;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.ui.model.FilterModel;

/**
 * Base localized mode predecessor with some common behavior
 * @date 28.3.2010
 * @author Kojotak 
 */
public abstract class BaseMode<T extends Game> implements Mode<T>,Searchable {

	protected String desc;
	protected String name;
	protected Set<Category> cats=Collections.emptySet();
	
	@Getter
	@Setter
	protected FilterModel filter;
	
	/**
	 * Each subclass should initialize {@link #cats}
	 * @param games
	 */
	protected BaseMode() {
		super();
		Localization loc = Application.getInstance().getLocalization();
		this.desc = loc.getString(this, "DESC");
		this.name = loc.getString(this, "NAME");
		this.filter = new FilterModel();
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<Category> getCategories() {
		return cats;
	}

	
}
