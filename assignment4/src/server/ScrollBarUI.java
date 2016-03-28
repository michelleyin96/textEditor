package server;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScrollBarUI extends BasicScrollBarUI {
	Color trackCol;
	Color thumbCol;
	Image downIcon, upIcon;
	Image im, mid;
	
	ScrollBarUI() {
		super();
		trackCol = new Color(250, 129, 50);
		thumbCol = new Color(232, 106, 23);
		try {
			upIcon = ImageIO.read(new File("img/scrollbar/red_sliderUp.png"));
			downIcon = ImageIO.read(new File("img/scrollbar/red_sliderDown.png"));
			im = ImageIO.read(new File("img/scrollbar/red_button05.png"));
			mid = ImageIO.read(new File("img/scrollbar/red_button03.png"));
		} catch (IOException e) {
			System.out.println("ERROR - " + e.getMessage());
		}
	}
	
	
	@Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {  
		g.setColor(trackCol);
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		mid = mid.getScaledInstance(20, mid.getHeight(null), Image.SCALE_DEFAULT);
		g.drawImage(mid, trackBounds.x, trackBounds.y, null);
		g.drawImage(mid, trackBounds.x, trackBounds.y + trackBounds.height - im.getHeight(null), null);
    }
	
   @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {       
    	g.setColor(thumbCol);
    	g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }
	
	@Override
	protected JButton createDecreaseButton(int orientation) {		
		return getAppropriateButton(orientation);
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return getAppropriateButton(orientation);
	}
	
	protected JButton getAppropriateButton(int orientation) {
		if (orientation == SwingConstants.SOUTH) {
			JButton downArrow = new JButton() {
				private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g) {
					downIcon = downIcon.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
					g.drawImage(downIcon, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
			downArrow.setFocusPainted(false);
			return downArrow;
		}
		else if (orientation == SwingConstants.NORTH) {
			JButton upArrow = new JButton() {

				protected void paintComponent(Graphics g) {
					upIcon = upIcon.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
					g.drawImage(upIcon, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
			
			upArrow.setFocusPainted(false);
			return upArrow;
		}
		return null;
	}
}
