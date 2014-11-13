/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.rui.chm.swing.CHMPane;
import cz.kojotak.arx.Application;
import cz.kojotak.arx.properties.Licence;
import cz.kojotak.arx.ui.renderer.HelpChoicesRenderer;

/**
 * @author TBe
 *
 */
public class HelpComboBox extends JComboBox<String> {

	private static final long serialVersionUID = 7184865050053098182L;

	public static final String LOC_KEY="LABEL";
	public static final String ROTAXMAME="ROTAXMAME";
	public static final String CREDITS="CREDITS";
	public static final String LICENCE="LICENCE";
	transient private Application app;
	private JFrame jf;

	public HelpComboBox() {
		super();
		app=Application.getInstance();
		this.setModel(new DefaultComboBoxModel<String>(new String[]{ROTAXMAME,LICENCE,CREDITS}));
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
				}else if(LICENCE.equals(selected)){
					HelpComboBox.this.selectLicence();
				}
				String title = HelpComboBox.this.jf.getTitle();
				String section = app.getLocalization().getString(HelpComboBox.this, selected);
				section = section.toLowerCase();
				HelpComboBox.this.jf.setTitle(title+" - "+section);
				source.setSelectedIndex(0);//vratim prvni vybrany
			}

		});
		this.setRenderer(new HelpChoicesRenderer(this));
		this.setPreferredSize(new Dimension(110,28));
	}

	private JFrame getHelpFrame(){
		JFrame jf = new JFrame();
		Image ico = app.getMainWindow().getIconImage();
		jf.setIconImage(ico);
		jf.setSize(new Dimension(500,500));
		jf.setVisible(true);
		jf.setTitle(app.getLocalization().getString(this, LOC_KEY));
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);	
		if(this.jf!=null){
			//stare zavreme
			this.jf.dispose();
		}
		this.jf=jf;		
		return jf;
	}

	protected void selectRotaxmame(){
		JFrame jf = getHelpFrame();
		String cd = Application.getInstance().getTmpDir();
		cd+=File.separator+"napoveda.chm";
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
	
	protected void selectLicence(){
		JFrame jf = getHelpFrame();
		
		StringBuilder sb = new StringBuilder("<html><body><h1>");
		Licence loc = app.getLicence();
		sb.append(loc.getString(loc, Licence.HEADER));
		sb.append("</h1>");
		for(Licence.Item licence:Licence.Item.values()){
			String key = licence.name();
			String name = loc.getString(key, Licence.PROP_NAME);
			String url = loc.getOptionalString(key, Licence.PROP_URL);
			String lic = loc.getString(key, Licence.PROP_LIC);
			String desc = loc.getOptionalString(key, Licence.PROP_DESC);
			sb.append("<h2>").append(name).append("</h2><ul>");
			sb.append("<li>").append(lic).append("</li>");
			if(url!=null){
				sb.append("<li>").append(url).append("</li>");
			}
			if(desc!=null){
				sb.append("<li>").append(desc).append("</li>");
			}
		}
		JEditorPane editor = new JEditorPane("text/html", sb.toString());
		editor.setEditable(false);
		JScrollPane scroll = new JScrollPane(editor,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jf.add(scroll);
		editor.select(0,1);
	}

}
