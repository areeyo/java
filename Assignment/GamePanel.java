import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Image;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;
	private Image imgBg;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		imgBg = Toolkit.getDefaultToolkit().getImage("OPH.jpg");
		big.drawImage(imgBg, 0, 0, 400, 600, null);
	}
	
	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.WHITE);		
 		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void bloodSpaceShip(int b){
		
		big.setColor(Color.RED);
		big.fillOval( 2, 0, b, 10 );
		for(Sprite s : sprites){
			s.draw(big);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
