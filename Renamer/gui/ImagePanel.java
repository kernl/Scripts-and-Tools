/*	Copyright (C) 2009	Fernando Alexandre

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Represents an image panel.
 * 
 * @author Fernando Alexandre
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Maximum width of an image.
	 */
	protected static final int MAX_WIDTH = 550;

	/**
	 * Thumb of the image.
	 */
	private ImageIcon thumb;

	/**
	 * Size of the panel
	 */
	private Dimension size_thumb;

	/**
	 * Creates an Image panel.
	 * 
	 * @param img
	 *            Path to the image to be shown.
	 */
	public ImagePanel(String img) {
		this(new ImageIcon(img));
	}

	/**
	 * Creates an Image Panel
	 * 
	 * @param img
	 *            ImageIcon object to be shown.
	 */
	public ImagePanel(ImageIcon img) {
		this.setImage(img);
	}

	/**
	 * Sets the image to be shown.
	 * 
	 * @param img
	 *            Image to be shown.
	 */
	public void setImage(ImageIcon img) {
		this.thumb = null;
		this.size_thumb = null;
		this.size_thumb = getThumbSize(img.getImage());
		setPreferredSize(this.size_thumb);
		setSize(this.size_thumb);
		setLayout(null);
		this.validate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.thumb.getImage(), 0, 0, null);
	}

	/**
	 * Determines the thumbnail size.
	 * 
	 * @param img
	 *            Desired image.
	 * @return Dimension with the image.
	 */
	private Dimension getThumbSize(Image img) {
		double ratio;
		double height_ratio = img.getHeight(null)
				/ (double) MainFrame.MAIN_HEIGHT * 1.23;
		double width_ratio = img.getWidth(null) / (double) MAX_WIDTH;

		if (img.getHeight(null) == img.getWidth(null))
			ratio = height_ratio;
		else
			ratio = Math.max(height_ratio, width_ratio);

		int width = (int) (img.getWidth(null) / ratio);
		int height = (int) (img.getHeight(null) / ratio);

		if (img.getHeight(null) == img.getWidth(null))
			getThumbnail(img, height, width);
		else if (ratio == height_ratio)
			getThumbnail(img, -1, height);
		else
			getThumbnail(img, width, -1);

		return new Dimension(width, height);
	}

	/**
	 * Creates a thumbnail.
	 * 
	 * @param width
	 *            Width of the thumbnail.
	 * @param height
	 *            Height of the thumbnail.
	 */
	private void getThumbnail(Image img, int width, int height) {
		this.thumb = new ImageIcon(img.getScaledInstance(
				width, height, Image.SCALE_SMOOTH));
	}

}
