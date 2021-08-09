/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bushe.swing.event.EventBus;
import org.jdesktop.swingx.JXTaskPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.event.OpponentChosen;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 24.5.2010
 * @author Kojotak 
 */
public class PreferencesTaskPane extends JXTaskPane {
	
	private static final long serialVersionUID = -1612142402940438020L;
	protected transient Application app;
	
	private final JLabel opponentLabel;
	
	private final JTextField opponentField;
	
	public PreferencesTaskPane() {
		super();
		app = Application.getInstance();
		String title = app.getLocalization().getString(this, "TITLE");
		setTitle(title);
		setCollapsed(true);
		setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.PREFERENCES));
		opponentLabel = new JLabel(app.getLocalization().getString(this, "OPPONENT"));
		opponentField=new JTextField(3);
		opponentField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				String text = opponentField.getText();
				if(text==null || text.length()==0 || text.length()==3){
					//send update only for full or empty nick name
					OpponentChosen event = new OpponentChosen(null); //TODO fixme
					EventBus.publish(event);					
				}
			}

		});
		arrangeLayout();
	}
	
	private void arrangeLayout(){
		JPanel panel = new JPanel();
		this.add(panel);
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		
		ParallelGroup firstColumn= layout.createParallelGroup();
		ParallelGroup secondColumn= layout.createParallelGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		
		firstColumn.addComponent(opponentLabel);
		secondColumn.addComponent(opponentField);
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(opponentLabel).addComponent(opponentField));

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup()
			.addGroup(firstColumn).addGroup(secondColumn);
		
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}

	public JTextField getOpponentField() {
		return opponentField;
	}
	
}
