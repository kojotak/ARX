/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.enums.FinishedStatus;
import cz.kojotak.arx.ui.renderer.FinishedTableCellRenderer;

/**
 * @date 6.10.2010
 * @author Kojotak 
 */
public class FinishedRecordColumn extends BaseColumn<Score,FinishedStatus> {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 3908252780614317493L;
	
	public FinishedRecordColumn() {
		super();
		this.setCellRenderer(new FinishedTableCellRenderer());
	}

	@Override
	public Class<FinishedStatus> getType() {
		return FinishedStatus.class;
	}

	@Override
	public FinishedStatus getValue(Score source) {
		return source.finished()?FinishedStatus.SOME_BODY_FINISHED:FinishedStatus.NONE_FINISHED;
	}

}
