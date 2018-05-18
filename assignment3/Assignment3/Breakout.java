/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	// rectangle coordinate.
	private double rectCoordinateY;
	private double rectCoordinateX;
	// ball coordiante.
	private double ballCoordinateX;
	private double ballCoordinateY;
	// ball velocity.
	private double vx, vy;
	// number of bricks in game.
	int k = NBRICKS_PER_ROW * NBRICK_ROWS;

	private GRect rect;

	private GRect paddle;
	private GOval ball;

	// our random generator.
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// our audio clip.
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	/** Runs the Breakout program. */
	public void run() {
		setUp();
		playGame();
	}

	private void setUp() {
		board();
		drawPaddle();
		ball();
	}

	// void for setting up board.
	private void board() {
		bricks(1, Color.RED);
		bricks(2, Color.ORANGE);
		bricks(3, Color.YELLOW);
		bricks(4, Color.GREEN);
		bricks(5, Color.CYAN);
	}

	// our private GRect where we input integer s and color and it will draw 2
	// rows of colorful bricks.
	private GRect bricks(int s, Color color) {
		// with this loop we change rectangle's Y coordinate and draw other
		// rows.
		for (rectCoordinateY = BRICK_Y_OFFSET + (s - 1)
				* (2 * (BRICK_HEIGHT + BRICK_SEP)); rectCoordinateY <= BRICK_Y_OFFSET
				+ (s - 1)
				* (2 * (BRICK_HEIGHT + BRICK_SEP))
				+ BRICK_SEP
				+ BRICK_HEIGHT; rectCoordinateY += BRICK_SEP + BRICK_HEIGHT) {
			// with this loop we draw first row of rectangles.
			for (double x = 0; x <= NBRICKS_PER_ROW * BRICK_WIDTH + BRICK_SEP; x += BRICK_WIDTH
					+ BRICK_SEP) {
				// this is the x coordinate of our rectangle.
				rectCoordinateX = x
						+ WIDTH
						/ 2
						- ((BRICK_WIDTH + BRICK_SEP) * NBRICKS_PER_ROW - BRICK_SEP)
						/ 2;
				// this is our first rectangle.
				rect = new GRect(rectCoordinateX, rectCoordinateY, BRICK_WIDTH,
						BRICK_HEIGHT);
				rect.setFilled(true);
				rect.setColor(color);
				add(rect);
			}
		}
		return null;
	}

	// we draw paddle.
	private void drawPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT
				- PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		add(paddle);
		// we add mouse listeners.
		addMouseListeners();
	}

	// with this public void paddle will follow mouse.
	public void mouseMoved(MouseEvent e) {

		paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, HEIGHT
				- PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		// if our mouse is out of canvas it paddle will stop following it.
		if (e.getX() >= WIDTH - PADDLE_WIDTH / 2) {
			paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET
					- PADDLE_HEIGHT);
		}
		if (e.getX() <= PADDLE_WIDTH / 2) {
			paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	// we add our playing ball.
	private void ball() {
		ballCoordinateX = WIDTH / 2 - BALL_RADIUS;
		ballCoordinateY = HEIGHT / 2 - BALL_RADIUS;
		ball = new GOval(ballCoordinateX, ballCoordinateY, 2 * BALL_RADIUS,
				2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
	}

	private void playGame() {
		// with this loop we can play for number of turns.
		for (int i = 1; i <= NTURNS; i++) {
			moveBall();
			// if we go over last turn it means we lose.
			if (i == NTURNS) {
				gameOver();
			}
			// if ball falls down we set its location.
			if (ball.getY() > HEIGHT) {
				ball.setLocation(ballCoordinateX, ballCoordinateY);
			}
		}
	}

	// we randomly get vx and vy. (velocity)
	private void ballVelocity() {
		vx = rgen.nextDouble(1.0, 2.5);
		// we randomly get the direction of the ball.
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		vy = rgen.nextDouble(1.0, 2.5);
	}

	private void moveBall() {
		// everything will start after we click.
		waitForClick();
		ballVelocity();
		while (true) {
			// ball will move with vx and vy.
			ball.move(vx, vy);
			pause(20);
			// checking for walls.
			checkForColision();
			GObject collider = getCollidingObject();
			// if ball touches paddle we will change vy with -vy
			if (collider == paddle) {
				// this if statement secures us from stacking ball into paddle.
				if (ball.getY() >= HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - 2
						* BALL_RADIUS
						&& ball.getY() < HEIGHT - PADDLE_Y_OFFSET
								- PADDLE_HEIGHT - 2 * BALL_RADIUS + vy) {
					vy *= -1;
				}
				// if ball touches brick we will remove brick and change vy with
				// -vy.
			} else if (collider != null) {
				remove(collider);
				// number of bricks gets minus one.
				k--;
				vy *= -1;
			}
			// if no bricks left, you win.
			if (k == 0) {
				youWon();
				break;
			}
			if (ball.getY() > HEIGHT) {
				break;
			}
		}
	}

	// checking for walls.
	private void checkForColision() {
		if (ball.getX() < 0) {
			vx *= -1;
			// if ball gets out of application we manually bring it back.
			double diff = -ball.getX();
			ball.move(2 * diff, 0);
		}
		if (ball.getX() > WIDTH - 2 * BALL_RADIUS) {
			vx *= -1;
			// if ball gets out of application we manually bring it back.
			double diff = ball.getX() - (WIDTH - 2 * BALL_RADIUS);
			ball.move(-2 * diff, 0);
		}

		if (ball.getY() < 0) {
			vy *= -1;
		}
	}

	// removes everything and adds label witch tells you that you lost.
	private void gameOver() {
		removeAll();
		GLabel label = new GLabel("GAME OVER");
		add(label, WIDTH / 2 - label.getWidth() / 2,
				HEIGHT / 2 - label.getAscent() / 2);
	}

	// removes everything and adds label witch tells you that you won.
	private void youWon() {
		removeAll();
		GLabel label = new GLabel("CONGRATULATIONS! YOU WON!");
		add(label, WIDTH / 2 - label.getWidth() / 2,
				HEIGHT / 2 - label.getAscent() / 2);
	}

	// returns the object ball just touched. for this we use get element method
	// and check for all 4 dots.
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());
		}
		if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		}
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		}
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2
				* BALL_RADIUS) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2
					* BALL_RADIUS);
		} else {
			return null;
		}
	}
}