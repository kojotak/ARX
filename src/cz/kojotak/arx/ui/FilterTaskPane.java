/**
 * 
 */
package cz.kojotak.arx.ui;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.bushe.swing.event.EventBus;
import org.jdesktop.swingx.JXTaskPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 19.5.2010
 * @author Kojotak
 */
public class FilterTaskPane extends JXTaskPane {
	
	private CategoryComboBox categoryComboBox;
	private AvailibilityComboBox availibilityComboBox;
	private PlatfromComboBox platformComboBox;
	private JTextField searchInput;
	
	JLabel catLab;
	JLabel avaLab;
	JLabel platLab;
	JLabel searchLab;

	public FilterTaskPane() {
		super();
		Application app = Application.getInstance();
		String title = app.getLocalization().getString(this, "TITLE");
		this.setTitle(title);
		this.setCollapsed(false);
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.FILTER));
		
		
		String searchStr = app.getLocalization().getString(this, "SEARCH");
		String catStr = app.getLocalization().getString(this, "CATEGORY");
		String avaStr = app.getLocalization().getString(this, "AVAILIBILITY");
		String platStr = app.getLocalization().getString(this,"PLATFORM");
		searchLab = new JLabel(searchStr);
		catLab = new JLabel(catStr);
		avaLab = new JLabel(avaStr);
		platLab=new JLabel(platStr);
		searchLab.setHorizontalAlignment(JLabel.RIGHT);
		catLab.setHorizontalAlignment(JLabel.RIGHT);
		avaLab.setHorizontalAlignment(JLabel.RIGHT);
		platLab.setHorizontalAlignment(JLabel.RIGHT);

		categoryComboBox = new CategoryComboBox();
		availibilityComboBox = new AvailibilityComboBox();
		platformComboBox=new PlatfromComboBox();
		searchInput = createSearchInput();
		arrangeFilter();
	}
	
	private JTextField createSearchInput() {
		var input = new JTextField();
		input.setColumns(10);
		input.setText("");
		input.getDocument().addDocumentListener(new DocumentListener() {
			private void onChange(DocumentEvent e) {
				String str = FilterTaskPane.this.searchInput.getText();
				FilterModel filterModel = new FilterModel();
				filterModel.setSearch(str);
				Application.getLogger(this).info("filtering by search string: " + str);
				EventBus.publish(filterModel);
			}

			public void changedUpdate(DocumentEvent e) {
				onChange(e);
			}

			public void removeUpdate(DocumentEvent e) {
				onChange(e);
			}

			public void insertUpdate(DocumentEvent e) {
				onChange(e);
			}
		});
		return input;
	}
	
	public void arrangeFilter(){
		this.removeAll();
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		ParallelGroup firstCol = layout.createParallelGroup();
		ParallelGroup secondCol = layout.createParallelGroup();
		firstCol.addComponent(searchLab);
		secondCol.addComponent(searchInput);
		firstCol.addComponent(platLab);
		secondCol.addComponent(platformComboBox);
		firstCol.addComponent(catLab).addComponent(avaLab);
		secondCol.addComponent(categoryComboBox).addComponent(availibilityComboBox);
		hGroup.addGroup(firstCol);
		hGroup.addGroup(secondCol);
		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(searchLab).addComponent(searchInput));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(catLab).addComponent(categoryComboBox));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(avaLab).addComponent(availibilityComboBox));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(platLab).addComponent(platformComboBox));
		layout.setVerticalGroup(vGroup);

		this.add(panel);
	}

	private static final long serialVersionUID = 1L;

	public CategoryComboBox getCategoryComboBox() {
		return categoryComboBox;
	}

	public AvailibilityComboBox getAvailibilityComboBox() {
		return availibilityComboBox;
	}

	public PlatfromComboBox getPlatformComboBox() {
		return platformComboBox;
	}

}
