package cz.kojotak.arx.ui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import lombok.Getter;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.mode.NoncompetetiveMode;
import cz.kojotak.arx.ui.model.GenericTableColumnModel;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * Hlavni okno
 * @date 21.3.2010
 * @author Kojotak
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -4442291784760181286L;

	private RecordPanel records;
	private GameTable table;
	private Toolbar toolbar;
	
	@Getter
	private JSplitPane splitter;
	
	@Getter
	private Sidebar sidebar;
	
	JPanel upper;

	public GameTable getGameTable() {
		return table;
	}

	public MainWindow() {
		super();
		AnnotationProcessor.process(this);
		this.setMinimumSize(new Dimension(640,480));
		Application app = Application.getInstance();
		String title = app.getLocalization().getString(this, "TITLE");
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image ico = app.getIconLoader().loadIcoAsImage("mamu"+File.separator+"39in1.ico");
		this.setIconImage(ico);
		this.setLayout(new BorderLayout());
		Container container = this.getContentPane();

		upper = new JPanel();
		JPanel lower = new JPanel();
		upper.setLayout(new BorderLayout());
		lower.setLayout(new BorderLayout());
		splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upper,lower);
		splitter.setContinuousLayout(true);
		container.add(splitter,BorderLayout.CENTER);

		
		//changeTable();
		Mode<?> mode = app.getCurrentMode();
		GenericTableColumnModel cm=new GenericTableColumnModel(mode);
		@SuppressWarnings("unchecked")GenericTableModel<?> tm = new GenericTableModel(mode.getGames(),mode.getColumns());
		table = new GameTable(tm,cm);

		JScrollPane scrollbars = new JScrollPane();
		scrollbars.setViewportView(table);

		JPanel centerHolder = new JPanel();
		centerHolder.setLayout(new BorderLayout());
		centerHolder.add(scrollbars, BorderLayout.CENTER);

		records = new RecordPanel(this,mode);
		upper.add(centerHolder, BorderLayout.CENTER);
		upper.add(records, BorderLayout.EAST);

		sidebar = new Sidebar();
		toolbar = new Toolbar(this,sidebar.getFilter().getCategoryComboBox());
		upper.add(sidebar,BorderLayout.WEST);
		container.add(toolbar, BorderLayout.NORTH);

		JPanel statusBar = new StatusBar();
		container.add(statusBar,BorderLayout.SOUTH);
		JPanel chat = new JPanel();//new ChatPanel();
		chat.setPreferredSize(new Dimension(0,0));
		chat.setMinimumSize(new Dimension(0,0));
		lower.add(chat,BorderLayout.CENTER);
		splitter.setDividerLocation(1.0);
		switchRecordPanel(mode);
				
		this.pack();
	}
	
	@EventSubscriber
	public void switchRecordPanel(Mode<?> mode){
		boolean showRecords = !(mode instanceof NoncompetetiveMode);
		boolean added=false;
		for(Component c:upper.getComponents()){
			if(c.equals(records)){
				added=true;
				break;
			}
		}
//		if(showRecords){
//			records.setRecordTable(mode);
//		}
		if(showRecords && !added){
			upper.add(records, BorderLayout.EAST);
		}else if(!showRecords && added){
			upper.remove(records);
		}
	}

//	@EventSubscriber
//	public void changeTable(Mode<?> mode){
//		GenericTableColumnModel cm=new GenericTableColumnModel(mode);
//		@SuppressWarnings("unchecked")GenericTableModel<?> tm = new GenericTableModel(mode.getGames(),mode.getColumns());
//		if(table!=null){
//			table.setColumnModel(cm);
//		}else{
//			table = new GameTable(tm,cm,records.table);
//		}
//	}
}
