package app;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;


/**
 * Class BallDemo - provides two short demonstrations showing how to use the 
 * Canvas class. 
 *
 * @author Rinaldo Lanza, game idea 
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (23-Jan-2002)
 */

public class BallGame {
	
	private static final int GROUNDLINE = 400;
	
	private Canvas myCanvas;
	private Vector<BouncingBall> balls = new Vector<BouncingBall>();

	/**
	 * Create a BallDemo object. Creates a fresh canvas and makes it visible.
	 */
	public BallGame() {
		myCanvas = new Canvas("Ball Demo", 600, 500, Color.WHITE);
		myCanvas.setVisible(true);
		// Aufgabe 1: MouseListener registrieren
		myCanvas.addMouseListener(new DartArrow());		
	}

	/**
	 * Simulates some bouncing balls
	 */
	public void startGame() {
		
		myCanvas.setVisible(true);

		// draw the ground
		myCanvas.drawLine(50, GROUNDLINE, 550, GROUNDLINE);

		// B�lle erzeugen
		var r = new Random();
		// Aufgabe 6: ein Ball ist nicht genug, oder?
		// Erweitern Sie die Erzeugung mit Random Zahlen und generieren Sie B�lle in einer Schleife
		balls.add( new BouncingBall(25, 35,  40,  new Color(r.nextInt(200)+56,r.nextInt(256),r.nextInt(256)), GROUNDLINE, myCanvas) );
		
						
		// draw every ball in the list
		for (BouncingBall ball : balls) {
			ball.draw();
		}		
		
		// make them bounce
		var finished = false;
		while (!finished) {
			myCanvas.wait(50); // small delay
			
			// move every ball in the list
			for (BouncingBall ball : balls) {
				ball.move();
			}		
			
			// stop if a ball has travelled a certain distance on x axis
			for (BouncingBall ball : balls) {				
				if (ball.getXPosition() >= 550) {
					finished = true;
					// Aufgabe 5: Gameover
					// ...
				}
			}		
			
			// Aufgabe 3: Alle B�lle getroffen?
			if (balls.size()==0) {
				finished = true;
				var image = new ImageIcon("src/app/images/gewonnen.jpg").getImage();
				myCanvas.drawImage(image, 340, 240);
				// Bild ausgeben, Gewonnen
			}
			
		}
		
		// erase every ball from the canvas
		for (var ball : balls) {
			ball.erase();
		}		
	}
	
	
	/* 
	 * findet heraus, ob der Dart (Click) auf dem Ball war oder nicht
	 */
	public BouncingBall dartOnBall(int x, int y) {		
		for (var ball : balls) {				
			// Aufgabe 2: Hier herausfinden ob ein Ball getroffen wurde
			// Achten Sie auf den R�ckgabewert. Alles klar?
			var ellipse = new Ellipse2D.Double(ball.getXPosition(), ball.getYPosition(), ball.getDiameter(), ball.getDiameter());
			if(ellipse.contains(new Point2D.Double(x, y))) {
				return ball;
			}
		}
		return null;
	}
	
	
	//
	// Aufgabe 1: Die Klasse ist schon teilweise bereitgestellt ...
	//
	class DartArrow implements MouseListener {
		
		public void mouseClicked(MouseEvent arg0) {
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseExited(MouseEvent arg0) {
		}
		public void mousePressed(MouseEvent arg0) {
			// Aufgabe 1: Hier die Position des Mauszeigers ausgeben
			//			
			System.out.println(String.format("Position %d/%d", arg0.getX(), arg0.getY()));
			
			BouncingBall ball = dartOnBall(arg0.getX(), arg0.getY()); 
			if (ball != null) {
				// Aufgabe 5: Ball l�schen
				ball.erase();
				balls.remove(ball);
				System.out.println("Balls remaining: " + balls.size());
			} else {
				Random r = new Random();
				// Aufgabe 5: Teil geschenkt :-)
				balls.add( new BouncingBall(r.nextInt(200)+20, r.nextInt(100)+20, r.nextInt(80)+20,
						                    new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)), 
						                    GROUNDLINE, myCanvas) );				
			}
		}
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	
	public static void main(String[] args) {
		var ballGame = new BallGame();
		ballGame.startGame();
	}
}
