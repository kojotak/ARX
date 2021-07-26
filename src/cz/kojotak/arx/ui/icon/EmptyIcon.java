package cz.kojotak.arx.ui.icon;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * An empty icon with arbitrary width and height.
 * @see http://www.java2s.com/Tutorial/Java/0240__Swing/Anemptyiconwitharbitrarywidthandheight.htm
 */
public final class EmptyIcon implements Icon {

  private int width;
  private int height;
  
  /**
   * Default 16x16 empty icon
   */
  public EmptyIcon() {
    this(16, 16);
  }
  
  public EmptyIcon(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getIconHeight() {
    return height;
  }

  public int getIconWidth() {
    return width;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
  }

}
