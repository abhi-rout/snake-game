package myproject;
import javax.swing.JFrame;

public class Frame extends JFrame {
	Frame(){
		this.setTitle("SnakeGame");
		this.add(new Panel());
		this.setVisible(true);
		this.setResizable(false);
		this.pack(); // the default settings for different os will not affect the game. everyone will get uniform experience
	}

}
