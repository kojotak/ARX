/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import cz.kojotak.arx.ui.renderer.UpdateChoicesRenderer;

/**
 * Provides menu for update related actions
 * @date 24.5.2010
 * @author Kojotak
 */
public class UpdateComboBox extends JComboBox {

	private static final long serialVersionUID = -16918413012906286L;
	public static final String LOC_KEY="LABEL";
	public static final String AVAILIBILITY="AVAILIBILITY";
	public static final String DATABASE="DATABASE";
	public static final String PROGRAM="PROGRAM";
	public static final String[] CHOICES=new String[]{DATABASE,AVAILIBILITY,PROGRAM};

	public UpdateComboBox() {
		super();
		this.setModel(new DefaultComboBoxModel(CHOICES));
		this.setEditable(false);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				UpdateComboBox source = (UpdateComboBox)event.getSource();
				String selected = source.getSelectedItem().toString();
				if(PROGRAM.equals(selected)){
					UpdateComboBox.this.selectedProgram();
				}else if(DATABASE.equals(selected)){
					UpdateComboBox.this.selectedDatabases();
				}else if(AVAILIBILITY.equals(selected)){
					UpdateComboBox.this.selectedAvailibility();
				}
				source.setSelectedIndex(0);
			}

		});
		this.setRenderer(new UpdateChoicesRenderer(this));
		this.setPreferredSize(new Dimension(110,28));
	}

	protected void selectedAvailibility(){
		
	}
	protected void selectedProgram(){
		
	}
	protected void selectedDatabases(){
		
	}
}
