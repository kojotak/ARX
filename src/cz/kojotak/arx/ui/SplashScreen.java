/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * Splash screen
 * 
 * @date 26.9.2010
 * @author Kojotak
 */
public class SplashScreen extends JWindow {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7763452240461525048L;

	JLabel splashImage = new JLabel();
	JLabel messageLabel = new JLabel();
	JPanel progressPanel = new JPanel();
	JPanel messagePanel = new JPanel();
	JProgressBar progressBar = new JProgressBar();
	ImageIcon imageIcon;
	static final Color rmBlue = new Color(0x29,0x6a,0xb9);
	static final Font messageFont = new Font(null,Font.BOLD,12);

	public SplashScreen(ImageIcon imageIcon) {
		Container container = this.getContentPane();
		GroupLayout layout = new GroupLayout(container);
		this.setLayout(layout);	   
		
		this.messageLabel.setForeground(Color.GREEN);
		this.messageLabel.setText("Inicializuju...");
		this.messageLabel.setForeground(rmBlue);
		this.messageLabel.setFont(messageFont);
		this.imageIcon = imageIcon;
		splashImage.setIcon(imageIcon);
		progressPanel.setLayout(new BorderLayout());
		progressPanel.setBackground(Color.BLACK);
		progressBar.setForeground(rmBlue);
		progressBar.setIndeterminate(false);
		this.getContentPane().add(splashImage, BorderLayout.CENTER);
		this.getContentPane().add(progressPanel, BorderLayout.SOUTH);
		progressPanel.add(progressBar, BorderLayout.CENTER);
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(messageLabel,BorderLayout.CENTER);
		messagePanel.setBackground(Color.BLACK);
				
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				           .addComponent(splashImage)
				           .addComponent(messagePanel)
				           .addComponent(progressPanel)
				           )
				);
		layout.setVerticalGroup(
				   layout.createSequentialGroup()
				      .addComponent(splashImage)
				      .addComponent(messagePanel)
				      .addComponent(progressPanel)
				);
		
		this.pack();
	}

	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(final String message, final int progress) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(progress>=0){
					if(progressBar.isIndeterminate()){
						progressBar.setIndeterminate(false);
					}
					progressBar.setValue(progress>100?100:progress);
				}else{
					progressBar.setIndeterminate(true);
				}
				
				messageLabel.setText("   "+message);
			}
		});
	}
	
	public void setProgress(Progress progress){
		this.setProgress(progress.message, progress.percent);
	}

	public void setScreenVisible(final boolean visible) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(visible);
			}
		});
	}
	
	public static class Progress{
		private int percent;
		private String message;
		
		public Progress(int percent, String message) {
			super();
			this.percent = percent;
			this.message = message;
		}

		public int getPercent() {
			return percent;
		}

		public String getMessage() {
			return message;
		}
		
	}

}
