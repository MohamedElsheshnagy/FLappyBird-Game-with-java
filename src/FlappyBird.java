import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener ,KeyListener,MouseListener {
	
	
	public final int WIDTH =800 , HEIGHT =800;	
	//public Renderer renderer ;
	public Rectangle bird; 
	public int ticks,yMotion,score;
	public boolean gameOver  , started ;
	public ArrayList<Rectangle> columns;
	public Random rand ;
	
	
	public FlappyBird() {
		//JFrame f =new JFrame();
		Timer timer =new Timer(20,this);
		rand = new Random();
		addKeyListener(this);
		setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
		addMouseListener(this);
		bird =new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
		columns =new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		timer.start();

	}
	
	public void addColumn(boolean start) {
		int space=300;
		int height= 50+rand.nextInt(300);
		int width=100;
		
		if(start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		
	}
	
	public void paintColumn(Graphics g,Rectangle colum) {
		g.setColor(Color.green.darker());
		g.fillRect(colum.x, colum.y, colum.width, colum.height);
	}
	
	public void Jump() {
		if(gameOver) {
			bird =new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
			columns.clear();
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			yMotion=0;
			score=0;
			
			gameOver=false;
			
		}
		if(!started) {
			started=true;
		}
		if(!gameOver) {
			if(yMotion > 0) {
				yMotion=0;
			}
			yMotion-=10;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		int speed=10;
		ticks++;
		
		if(started) {
				
			for(int i=0; i < columns.size();i++) {
				Rectangle column = columns.get(i);
				column.x-=speed;
			}
			
			if(ticks %2==0 && yMotion < 15) {
				yMotion +=2;
			}
			
			for(int i=0; i < columns.size();i++) {
				Rectangle column = columns.get(i);
				
				if(column.x + column.width < 0) {
					columns.remove(column);
					if(column.y == 0) {
						addColumn(false);
					}
				}
				
			}
			bird.y+=yMotion;
			for(Rectangle column : columns) {
				
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10)
				{
					score++;
				}
				
				if(column.intersects(bird)) {
					gameOver=true;
					bird.x=column.x - bird.width;
				}
			}
			
			if(bird.y > HEIGHT-120 || bird.y <0) {
					gameOver=true;
				}
			if(bird.y + yMotion >= HEIGHT - 120) {
				bird.y = HEIGHT- 120 - bird.height;
			}
		}
		
		repaint();
		
	}
	
	
	public void paint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WIDTH,120);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-120, WIDTH,20);
		
		
		for(Rectangle column : columns) {
			paintColumn(g, column);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		if(!started) {
			g.drawString("Click to start", 90, HEIGHT/2-50);
		}
		if(gameOver) {
			g.drawString("Game Over !", 100, HEIGHT/2-50);
		}
		if(!gameOver && started ) {
			g.drawString(String.valueOf(score), WIDTH/2-25, 100);
		}
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			Jump();
		}
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Jump();		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}



}
