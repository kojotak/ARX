/**
 * 
 */
package cz.kojotak.arx.ui.icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * @see Swing Hacks, chapter 3
 * @date 10.2.2010
 * @author Kojotak
 */
public class ResizeIcon implements Icon {

	private static final int WIDTH = 12;
	private static final int HEIGHT = 12;
	private static final Color SQUARE_COLOR_LEFT = new Color(184, 180, 163);
	private static final Color SQUARE_COLOR_TOP_RIGHT = new Color(184, 180, 161);
	private static final Color SQUARE_COLOR_BOTTOM_RIGHT = new Color(184, 181,
			161);
	private static final Color THREE_D_EFFECT_COLOR = new Color(255, 255, 255);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
	@Override
	public int getIconHeight() {
		return HEIGHT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int firstRow = 0;
		int firstColumn = 0;
		int rowDiff = 4;
		int columnDiff = 4;

		// The row difference works out to be four because the square is two
		// pixels wide, plus one pixel for the white effect, plus one pixel for
		// spacing. The same deal applies for the column difference. From there,
		// it's easy to calculate the other rows and columns based on the
		// starting row and column and their distances from each other:

		int secondRow = firstRow + rowDiff;
		int secondColumn = firstColumn + columnDiff;
		int thirdRow = secondRow + rowDiff;
		int thirdColumn = secondColumn + columnDiff;

		// Next, paint the white squares: one in the first row, two in the
		// second, and three in the third. Notice that the white squares are
		// offset by one pixel because the column and row variables (firstRow,
		// secondRow, etc.) reference the gray squares. These squares provide
		// the 3D effect.

		// first row
		draw3dSquare(g, firstColumn + 1, thirdRow + 1);

		// second row
		draw3dSquare(g, secondColumn + 1, secondRow + 1);
		draw3dSquare(g, secondColumn + 1, thirdRow + 1);

		// third row
		draw3dSquare(g, thirdColumn + 1, firstRow + 1);
		draw3dSquare(g, thirdColumn + 1, secondRow + 1);
		draw3dSquare(g, thirdColumn + 1, thirdRow + 1);

		// Finally, paint the gray squares on top of the white squares:

		// first row
		drawSquare(g, firstColumn, thirdRow);

		// second row
		drawSquare(g, secondColumn, secondRow);
		drawSquare(g, secondColumn, thirdRow);

		// third row
		drawSquare(g, thirdColumn, firstRow);
		drawSquare(g, thirdColumn, secondRow);
		drawSquare(g, thirdColumn, thirdRow);

	}

	private void drawSquare(Graphics g, int x, int y) {
		Color oldColor = g.getColor();
		g.setColor(SQUARE_COLOR_LEFT);
		g.drawLine(x, y, x, y + 1);
		g.setColor(SQUARE_COLOR_TOP_RIGHT);
		g.drawLine(x + 1, y, x + 1, y);
		g.setColor(SQUARE_COLOR_BOTTOM_RIGHT);
		g.drawLine(x + 1, y + 1, x + 1, y + 1);
		g.setColor(oldColor);
	}

	private void draw3dSquare(Graphics g, int x, int y) {
		Color oldColor = g.getColor();
		g.setColor(THREE_D_EFFECT_COLOR);
		g.fillRect(x, y, 2, 2);
		g.setColor(oldColor);
	}
}
