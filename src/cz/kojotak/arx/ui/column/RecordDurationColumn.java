/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;

/**
 * @date 4.10.2010
 * @author Kojotak 
 */
public class RecordDurationColumn extends BaseColumn<Score, String> {

	public RecordDurationColumn() {
		super();
	}

	/**
	 * generated
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String getValue(Score source) {
		Integer duration=source.duration();
		if(duration==null){
			return "";
		}
		int sec = duration % 60;
		duration /= 60;
		int min = duration % 60;
		duration /= 60;
		int hrs = duration % 60;
		StringBuilder sb = new StringBuilder();
		if(hrs<10){
			sb.append("0");
		}
		sb.append(hrs).append(":");
		if(min<10){
			sb.append("0");
		}
		sb.append(min).append(":");
		if(sec<10){
			sb.append("0");
		}
		sb.append(sec);
		return sb.toString();
	}

}
