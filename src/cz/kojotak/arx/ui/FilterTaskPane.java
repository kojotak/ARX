/**
 * 
 */
package cz.kojotak.arx.ui;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

import lombok.Getter;

import org.jdesktop.swingx.JXTaskPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.mode.NoncompetetiveMode;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 19.5.2010
 * @author Kojotak
 */
public class FilterTaskPane extends JXTaskPane {
	
	@Getter
	private CategoryComboBox categoryComboBox;
	
	@Getter
	private AvailibilityComboBox availibilityComboBox;
	
	@Getter
	private PlatfromComboBox platformComboBox;
	
	JLabel catLab;
	JLabel avaLab;
	JLabel platLab;

	public FilterTaskPane() {
		super();
		Application app = Application.getInstance();
		String title = app.getLocalization().getString(this, "TITLE");
		this.setTitle(title);
		this.setCollapsed(true);
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.FILTER));
		

		String catStr = app.getLocalization().getString(this, "CATEGORY");
		String avaStr = app.getLocalization().getString(this, "AVAILIBILITY");
		String platStr = app.getLocalization().getString(this,"PLATFORM");
		catLab = new JLabel(catStr);
		avaLab = new JLabel(avaStr);
		platLab=new JLabel(platStr);
		catLab.setHorizontalAlignment(JLabel.RIGHT);
		avaLab.setHorizontalAlignment(JLabel.RIGHT);
		platLab.setHorizontalAlignment(JLabel.RIGHT);

		categoryComboBox = new CategoryComboBox();
		availibilityComboBox = new AvailibilityComboBox();
		platformComboBox=new PlatfromComboBox();
		arrangeFilter();
	}
	
	public void arrangeFilter(){
		this.removeAll();
		boolean showPlatformFilter=Application.getInstance().getCurrentMode() instanceof NoncompetetiveMode;
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		ParallelGroup firstCol = layout.createParallelGroup();
		ParallelGroup secondCol = layout.createParallelGroup();
		if(showPlatformFilter){
			firstCol.addComponent(platLab);
			secondCol.addComponent(platformComboBox);
		}
		firstCol.addComponent(catLab).addComponent(avaLab);
		secondCol.addComponent(categoryComboBox).addComponent(availibilityComboBox);
		hGroup.addGroup(firstCol);
		hGroup.addGroup(secondCol);
		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(catLab).addComponent(categoryComboBox));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(avaLab).addComponent(availibilityComboBox));
		if(showPlatformFilter){
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(platLab).addComponent(platformComboBox));
		}
		layout.setVerticalGroup(vGroup);

		this.add(panel);
	}

	private static final long serialVersionUID = -1038522044254134838L;

}
