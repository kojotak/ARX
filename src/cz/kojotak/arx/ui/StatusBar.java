/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.ui.icon.ResizeIcon;

/**
 * @see Swing Hacks, chapter 36
 * @date 10.2.2010
 * @author Kojotak
 */
public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JPanel contentPanel;
	SimpleDateFormat DB_VERSION = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	JLabel gamesLabel = new JLabel();
	JLabel playersLabel = new JLabel();
	JLabel scoresLabel = new JLabel();
	
	public StatusBar() {
		AnnotationProcessor.process(this);
		setPreferredSize(new Dimension(getWidth(), 23));
		setLayout(new BorderLayout());

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(new JLabel(new ResizeIcon()), BorderLayout.SOUTH);
		rightPanel.setOpaque(false);

		add(rightPanel, BorderLayout.EAST);
		setBackground(SystemColor.control);

		Application app=Application.getInstance();
		
		Localization loc = app.getLocalization();
		String version = new StringBuilder(loc.getString(this, "VERSION_TITLE"))
			.append(" ").append(readAppVersion()).toString();
		
		Date updated = app.getSqliteImporter().getLastUpdate();
		String dbversion = updated!=null?new StringBuilder(loc.getString(this, "DB_VERSION"))
			.append(" ").append(DB_VERSION.format(updated)).toString():"?";
		
		contentPanel = new JPanel();
			
		contentPanel.add(gamesLabel);
		contentPanel.add(new SeparatorPanel(Color.GRAY, Color.WHITE));
		contentPanel.add(playersLabel);
		contentPanel.add(new SeparatorPanel(Color.GRAY, Color.WHITE));
		contentPanel.add(scoresLabel);
		contentPanel.add(new SeparatorPanel(Color.GRAY, Color.WHITE));
		contentPanel.add(new JLabel(dbversion));
		contentPanel.add(new SeparatorPanel(Color.GRAY, Color.WHITE));
		contentPanel.add(new JLabel(version));
		contentPanel.setOpaque(false);
		updateLabels(app.getCurrentMode());
		add(contentPanel, BorderLayout.WEST);
	}
	
	@EventSubscriber
	public void updateLabels(Mode mode) {
		Application.getLogger(this).fine("updating labels for " + mode);
		Localization loc = Application.getInstance().getLocalization();
		String games = loc.getString(this, "GAMES") + " " + mode.getGameCount();
		String players = loc.getString(this, "PLAYERS") + " " + mode.getPlayerCount();
		String recs = loc.getString(this, "RECORDS") + " " + mode.getRecordCount();
		gamesLabel.setText(games);
		playersLabel.setText(players);
		scoresLabel.setText(recs);
	}
	
	private String readAppVersion() {
		URL url = getClass().getClassLoader().getResource("META-INF/MANIFEST.MF");
		if(url!=null) {
		    try {
		        Manifest manifest = new Manifest(url.openStream());
		        Attributes attrs = manifest.getMainAttributes();
		        if(attrs!=null) {
		        	Object attr = attrs.getValue("Build-Timestamp");
		        	if(attr instanceof String vs) {
		        		return vs;
		        	}
		        }
		        Application.getLogger(this).log(Level.WARNING, "Application version not found in manifest");
		      } catch (IOException e) {
		        Application.getLogger(this).log(Level.WARNING, "Failed to read manifest", e);
		      }
		}
		return "DEVEL";
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int y = 0;
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(196, 194, 183));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(218, 215, 201));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 217));
		g.drawLine(0, y, getWidth(), y);

		y = getHeight() - 3;
		g.setColor(new Color(233, 232, 218));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 216));
		g.drawLine(0, y, getWidth(), y);
		y = getHeight() - 1;
		g.setColor(new Color(221, 221, 220));
		g.drawLine(0, y, getWidth(), y);
	}

	static class SeparatorPanel extends JPanel {
		private static final long serialVersionUID = -6844524453420132242L;
		private Color leftColor;
		private Color rightColor;

		public SeparatorPanel(Color left, Color right) {
			this.leftColor = left;
			this.rightColor = right;
			setOpaque(false);
		}

		protected void paintComponent(Graphics g) {
			g.setColor(leftColor);
			g.drawLine(0, 0, 0, getHeight());
			g.setColor(rightColor);
			g.drawLine(1, 0, 1, getHeight());

		}
	}

}
