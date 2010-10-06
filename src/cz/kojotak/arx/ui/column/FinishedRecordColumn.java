/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.enums.FinishedStatus;
import cz.kojotak.arx.ui.renderer.FinishedTableCellRenderer;

/**
 * @date 6.10.2010
 * @author Kojotak 
 */
public class FinishedRecordColumn extends BaseColumn<Record,FinishedStatus> {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 3908252780614317493L;
	
	public FinishedRecordColumn() {
		super();
		this.setCellRenderer(new FinishedTableCellRenderer());
		this.setVisible(false);
	}

	@Override
	public Class<FinishedStatus> getType() {
		return FinishedStatus.class;
	}

	@Override
	public FinishedStatus getValue(Record source) {
		return source.isFinished()?FinishedStatus.SOME_BODY_FINISHED:FinishedStatus.NONE_FINISHED;
	}

}
