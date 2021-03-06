//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;

public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		//g.setColor(Color.GREEN);
		//g.fillRect(x, y, width, height);
		Image img = Toolkit.getDefaultToolkit().getImage("space.gif");
		g.drawImage(img, x, y, width, height, null);
	}
	
	
	public void move(int direction,int direction2){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
		y += (step * direction2);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}

}
