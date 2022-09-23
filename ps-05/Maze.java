import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame {

	public Maze() {
		
		this.add(new MazeGridPanel(100,100));
		this.setSize(800, 800);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
			new Maze();
	}
}
