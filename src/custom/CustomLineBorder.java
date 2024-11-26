package custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class CustomLineBorder extends AbstractBorder { //240531 15:00
	public static final int TOP = 0x1;
	public static final int LEFT = 0x2;
	public static final int BOTTOM = 0x4;
	public static final int RIGHT = 0x8;
	private Color mColor;
	private int mThickness;
	private int mTarget;

	public CustomLineBorder(Color colour, int thickness, int target) {
		mColor = colour;
		mThickness = thickness;
		mTarget = target;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (g != null) {
			g.setColor(mColor);
			if ((mTarget & TOP) != 0) {
				for (int i = 0; i < mThickness; i++) {
					g.drawLine(x, y + i, width, y + i);
				}
			}
			if ((mTarget & LEFT) != 0) {
				for (int i = 0; i < mThickness; i++) {
					g.drawLine(x + i, y, x + i, height);
				}
			}
			if ((mTarget & BOTTOM) != 0) {
				for (int i = 0; i < mThickness; i++) {
					g.drawLine(x, height - i - 1, width, height - i - 1);
				}
			}
			if ((mTarget & RIGHT) != 0) {
				for (int i = 0; i < mThickness; i++) {
					g.drawLine(width - i - 1, y, width - i - 1, height);
				}
			}
		}
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return (getBorderInsets(c, new Insets(0, 0, 0, 0)));
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.top = 0;
		insets.left = 0;
		insets.bottom = 0;
		insets.right = 0;
		if ((mTarget & TOP) != 0) {
			insets.top = mThickness;
		}
		if ((mTarget & LEFT) != 0) {
			insets.left = mThickness;
		}
		if ((mTarget & BOTTOM) != 0) {
			insets.bottom = mThickness;
		}
		if ((mTarget & RIGHT) != 0) {
			insets.right = mThickness;
		}

		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}
}
