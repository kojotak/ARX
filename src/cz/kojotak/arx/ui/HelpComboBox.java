/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.rui.chm.swing.CHMPane;
import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;
import cz.kojotak.arx.ui.renderer.HelpChoicesRenderer;

/**
 * @author TBe
 *
 */
public class HelpComboBox extends JComboBox {

	private static final long serialVersionUID = 7184865050053098182L;

	public static final String LOC_KEY="LABEL";
	public static final String ROTAXMAME="ROTAXMAME";
	public static final String CREDITS="CREDITS";
	transient private Application app;
	private JFrame jf;

	public HelpComboBox() {
		super();
		app=Application.getInstance();
		this.setModel(new DefaultComboBoxModel(new String[]{ROTAXMAME,CREDITS}));
		this.setEditable(false);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				HelpComboBox source = (HelpComboBox)event.getSource();
				String selected = source.getSelectedItem().toString();
				if(ROTAXMAME.equals(selected)){
					HelpComboBox.this.selectRotaxmame();
				}else if(CREDITS.equals(selected)){
					HelpComboBox.this.selectCredits();
				}
				source.setSelectedIndex(0);//vratim prvni vybrany
			}

		});
		this.setRenderer(new HelpChoicesRenderer(this));
		this.setPreferredSize(new Dimension(110,28));
	}

	private JFrame getHelpFrame(){
		JFrame jf = new JFrame();
		jf.setSize(new Dimension(500,500));
		jf.setVisible(true);
		jf.setTitle(app.getLocalization().getString(this, LOC_KEY));
		String helpIcoPath = Application.getInstance().getIcons().getString(GUIIcons.HELP);
		jf.setIconImage(Application.getInstance().getIconLoader().loadIcoAsImage(helpIcoPath));
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//FIXME broken
		if(this.jf!=null){
			//stare zavreme
			this.jf.dispose();
		}else{
			this.jf=jf;
		}
		return jf;
	}

	protected void selectRotaxmame(){
		JFrame jf = getHelpFrame();
		String cd = Application.currentDir;
		cd+=File.separator+"tmp"+File.separator+"napoveda.chm";
		JPanel panel = null;
		try{
			panel=new CHMPane(cd);
		}catch(Exception ex){
			app.getLogger(this).error("cannot load help chm", ex);
			panel = new JPanel();
			JLabel er = new JLabel("nelze zobrazit napovedu: "+ex.getMessage());
			panel.add(er);
		}
		jf.setLayout(new BorderLayout());
		jf.add(panel, BorderLayout.CENTER);
	}

	protected void selectCredits(){
		JFrame jf = getHelpFrame();
		JPanel panel = new JPanel();
		String text = app.readFile("credits.htm");
		if(text==null){
			text="";
		}
		JLabel label = new JLabel(text);
		panel.add(label);
		jf.add(panel);
	}

}
