import javax.swing.JFrame;

public class mainBird {

	public static void main(String[] args) {
		FlappyBird flappy=new FlappyBird();
		JFrame f =new JFrame();
		f.setSize(800 , 800);
		f.setResizable(false);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setTitle("Flappy Game");
        f.setBounds(500, 120, 800, 800);
		f.add(flappy);
	}

}
