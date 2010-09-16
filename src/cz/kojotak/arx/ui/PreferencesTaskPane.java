/**
 * 
 */
package cz.kojotak.arx.ui;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.ParallelGroup;

import org.jdesktop.swingx.JXTaskPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 24.5.2010
 * @author Kojotak 
 */
public class PreferencesTaskPane extends JXTaskPane {
	
	private static final long serialVersionUID = -1612142402940438020L;
	protected transient Application app;
	
	public PreferencesTaskPane(final GameTable table) {
		super();
		app = Application.getInstance();
		String title = app.getLocalization().getString(this, "TITLE");
		this.setTitle(title);
		this.setCollapsed(true);
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.PREFERENCES));

		JPanel panel = new JPanel();
		initDynamically(panel);

		this.add(panel);
	}
	
	private void initDynamically(JPanel panel){
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
//		Mode<?> mode = app.getCurrentMode();
//		List<? extends BaseColumn<?, ?>> cols = mode.getColumns();
		
		ParallelGroup firstColumn= layout.createParallelGroup();
		ParallelGroup secondColumn= layout.createParallelGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
//		for(BaseColumn<?,?> c:cols){
////			if(!c.isSwitchable()){
////				continue;
////			}
//			
//			JCheckBox left = new JCheckBox();
////			JComponent right = c.getCustomized();
//			JComponent right = new JLabel(c.getClass().getSimpleName());
//			right.setAlignmentX(0.5f);
//			firstColumn.addComponent(left);
//			secondColumn.addComponent(right);
//			
//			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
//					.addComponent(left)
//					.addComponent(right)
//					);
//		}

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup()
			.addGroup(firstColumn).addGroup(secondColumn);
		
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	
}
