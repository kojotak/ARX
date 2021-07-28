/**
 * 
 */
package cz.kojotak.arx.ui.column;

import javax.swing.Icon;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.FinishedStatus;
import cz.kojotak.arx.ui.renderer.FinishedTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class FinishedColumn extends BaseColumn<WithStatistics,FinishedStatus>{

	private static final long serialVersionUID = 1L;

	@Override
	public FinishedStatus getValue(WithStatistics source) {
		return source.getStatistics().getFinishedStatus();
	}
	
	public FinishedColumn() {
		super();
		this.setCellRenderer(new FinishedTableCellRenderer());
	}
	
	@Override
	public Class<FinishedStatus> getType() {
		return FinishedStatus.class;
	}

	@Override
	protected Icon getHeaderIcon() {
		return Application.getInstance().getIconLoader().tryLoadIcon(FinishedStatus.CURRENT_PLAYER_FINISHED);
	}
	
	

}
