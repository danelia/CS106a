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

public class BreakoutExtensions extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 650;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT - 50;

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

	// Label and Button for button that changes background color.
	private static G3DRect COLOR_BUTTON;
	private static GLabel COLOR_LABEL;

	// Label and Button for button that mutes sound and boolean for knowing if
	// sound is muted or not. the sounds will only play when the mute status is
	// false.
	private static G3DRect MUTE_BUTTON;
	private static GLabel MUTE_LABEL;
	private static boolean MUTE_STATUS = false;

	// Label and Button for button that pauses game and boolean for knowing if
	// game is paused or not.
	private static G3DRect PAUSE_BUTTON;
	private static GLabel PAUSE_LABEL;
	private static boolean PAUSE_STATUS = false;

	// int for number of turns and score.
	private int x = NTURNS;
	private int score;
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

	// our image of heart.
	private GImage heart;

	// array list for image.
	private ArrayList<GImage> ar = new ArrayList<GImage>();

	private GLabel scoreLabel;

	private GRect rect;

	private GRect paddle;
	private GOval ball;

	// our random generator.
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// our audio clip.
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		// draws objects on canvas.
		setUp();
		// playing game.
		playGame();
	}

	// initializes everything.
	private void setUp() {
		score();
		board();
		drawPaddle();
		ball();
		lifeBar();
		setMuteButton();
		setPauseButton();
		colorButton();
	}

	// adds pause button.
	public void setPauseButton() {
		PAUSE_BUTTON = new G3DRect(0, 0);
		PAUSE_BUTTON.setFillColor(Color.LIGHT_GRAY);
		PAUSE_BUTTON.setFilled(true);
		PAUSE_LABEL = new GLabel("Pause   ", WIDTH / 2 - 25, HEIGHT);
		PAUSE_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
		double width = PAUSE_LABEL.getWidth() + 10;
		double height = PAUSE_LABEL.getHeight() + 10;
		PAUSE_BUTTON.setBounds(WIDTH / 2 - 5 - 25, HEIGHT - height + 5, width,
				height);
		PAUSE_BUTTON.setRaised(true);
		PAUSE_LABEL.setColor(Color.black);
		add(PAUSE_BUTTON);
		add(PAUSE_LABEL);
	}

	// adds button to change background color.
	public void colorButton() {
		COLOR_BUTTON = new G3DRect(0, 0);
		COLOR_BUTTON.setFillColor(Color.LIGHT_GRAY);
		COLOR_BUTTON.setFilled(true);
		COLOR_LABEL = new GLabel("Change Color ", 5, HEIGHT);
		COLOR_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
		double width = COLOR_LABEL.getWidth() + 10;
		double height = COLOR_LABEL.getHeight() + 10;
		COLOR_BUTTON.setBounds(0, HEIGHT - height + 5, width, height);
		COLOR_BUTTON.setRaised(true);
		PAUSE_LABEL.setColor(Color.black);
		add(COLOR_BUTTON);
		add(COLOR_LABEL);
	}

	// adds button to mute sound.
	public void setMuteButton() {
		MUTE_BUTTON = new G3DRect(0, 0);
		MUTE_BUTTON.setFillColor(Color.LIGHT_GRAY);
		MUTE_BUTTON.setFilled(true);
		MUTE_LABEL = new GLabel("Turn Mute On", 3 * WIDTH / 4, HEIGHT);
		MUTE_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
		double width = MUTE_LABEL.getWidth() + 10;
		double height = MUTE_LABEL.getHeight() + 10;
		MUTE_BUTTON.setBounds(3 * WIDTH / 4 - 5, HEIGHT - height + 5,
				width + 10, height + 10);
		MUTE_BUTTON.setRaised(true);
		MUTE_LABEL.setColor(Color.black);
		add(MUTE_BUTTON);
		add(MUTE_LABEL);
	}

	// our void for adding heart. we also add it into arraylist.
	void health(int n) {
		heart = new GImage("heart.png");
		heart.scale(0.15);
		add(heart, WIDTH - heart.getWidth() * n, heart.getHeight());
		ar.add(heart);

	}

	// using for loop we draw so many hearts how much turn we have.
	private void lifeBar() {

		for (int i = 1; i <= x; i++) {
			health(i);
		}
	}

	// adds label at the top left corner of the canvas.
	private void score() {
		score = 0;
		scoreLabel = new GLabel("score: " + score);
		add(scoreLabel, 0, scoreLabel.getAscent());
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
		// we add key listeners.
		addKeyListeners();

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

	public void mouseClicked(MouseEvent e) {
		// we get object where the mouse is clicked.
		GObject coll = getElementAt(e.getX(), e.getY());
		// if mouse is clicked on muting button mute status and label in it will
		// change.
		if (coll == MUTE_BUTTON || coll == MUTE_LABEL) {
			if (MUTE_STATUS == false) {
				MUTE_STATUS = true;
				MUTE_LABEL.setLabel("Turn Mute Off");
				MUTE_BUTTON.setRaised(false);
			} else if (MUTE_STATUS == true) {
				MUTE_STATUS = false;
				MUTE_LABEL.setLabel("Turn Mute On");
				MUTE_BUTTON.setRaised(true);
			}
		}
		// if mouse is clicked on pause button pause status and label in it will
		// change.
		if (coll == PAUSE_BUTTON || coll == PAUSE_LABEL) {
			if (PAUSE_STATUS == false) {
				PAUSE_STATUS = true;
				PAUSE_LABEL.setLabel("Resume");
				PAUSE_BUTTON.setRaised(false);
			} else if (PAUSE_STATUS == true) {
				PAUSE_STATUS = false;
				PAUSE_LABEL.setLabel("Pause");
				PAUSE_BUTTON.setRaised(true);
			}
		}
		// if mouse is clicked on color changer button it will randomly get
		// color and set it.
		if (coll == COLOR_BUTTON || coll == COLOR_LABEL) {
			setBackground(rgen.nextColor());
		}
	}

	// mouse will change its position if we press left or right buttons.
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			paddle.move(10, 0);
			// it will stop at right edge.
			if (paddle.getX() >= WIDTH - PADDLE_WIDTH) {
				paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT
						- PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			}
			break;
		case KeyEvent.VK_LEFT:
			paddle.move(-10, 0);
			// it will stop at left edge too.
			if (paddle.getX() <= 0) {
				paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			}
			break;
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

	// PLAYING GAME!
	private void playGame() {
		// we need this while(true) loop for playing again of we lost.
		while (true) {
			// with this loop we can play for number of turns.
			for (int i = 1; i <= x; i++) {
				moveBall();
				// if number of bricks will be 0 than we want our loop to break.
				if (k == 0) {
					break;
				}
			}
		}
	}

	// removes everything and add label that u lost. also tell final score. if
	// we click it will remove this labels and the game will start again. for
	// this we needed while(loop) in playGame();
	private void gameOver() {
		removeAll();
		GLabel label = new GLabel("GAME OVER! CLICK TO PLAY AGAIN!");
		add(label, WIDTH / 2 - label.getWidth() / 2,
				HEIGHT / 2 - label.getAscent() / 2);
		GLabel finalScore = new GLabel("Your score is " + score);
		add(finalScore, (WIDTH - finalScore.getWidth()) / 2,
				finalScore.getAscent());
		waitForClick();
		removeAll();
		setUp();
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
			// while pause status is true we want our game to be paused. after
			// we click the button again pause status will change to false and
			// ball will start moving again.
			while (PAUSE_STATUS == true) {
				pause(1);
			}
			checkForColision();
			GObject collider = getCollidingObject();
			// if ball touches paddle.
			if (collider == paddle) {
				// this if statement secures us from stacking ball into paddle.
				if (ball.getY() >= HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - 2
						* BALL_RADIUS
						&& ball.getY() < HEIGHT - PADDLE_Y_OFFSET
								- PADDLE_HEIGHT - 2 * BALL_RADIUS + vy) {
					double ballWidth = ball.getWidth();
					double ballCenterX = ball.getX() + ballWidth / 2;
					double paddleWidth = paddle.getWidth();
					double paddleCenterX = paddle.getX() + paddleWidth / 2;
					double speedX = vx;
					double speedY = vy;

					// Applying the Pythagorean theorem, calculate the ball's
					// overall
					// speed from its X and Y components. This will always be a
					// positive value.
					double speedXY = Math.sqrt(speedX * speedX + speedY
							* speedY);

					// Calculate the position of the ball relative to the center
					// of
					// the paddle, and express this as a number between -1 and
					// +1.
					// (Note: collisions at the ends of the paddle may exceed
					// this
					// range, but that is fine.)
					double posX = (ballCenterX - paddleCenterX)
							/ (paddleWidth / 2);

					// Define an empirical value (tweak as needed) for
					// controlling
					// the amount of influence the ball's position against the
					// paddle
					// has on the X speed. This number must be between 0 and 1.
					final double influenceX = 0.75;

					// Let the new X speed be proportional to the ball position
					// on
					// the paddle. Also make it relative to the original speed
					// and
					// limit it by the influence factor defined above.
					speedX = speedXY * posX * influenceX;
					vx = speedX;

					// Finally, based on the new X speed, calculate the new Y
					// speed
					// such that the new overall speed is the same as the old.
					// This
					// is another application of the Pythagorean theorem. The
					// new
					// Y speed will always be nonzero as long as the X speed is
					// less
					// than the original overall speed.
					speedY = Math.sqrt(speedXY * speedXY - speedX * speedX)
							* (speedY > 0 ? -1 : 1);
					vy = speedY;
					if (MUTE_STATUS == false) {
						bounceClip.play();
					}
				}
				// if ball touched brick we will remove that brick, change vy ,
				// set new score.
			} else if (collider != null && collider != scoreLabel
					&& collider.getY() > heart.getHeight()
					&& collider != MUTE_BUTTON && collider != MUTE_LABEL
					&& collider != COLOR_BUTTON && collider != COLOR_LABEL
					&& collider != PAUSE_BUTTON && collider != PAUSE_LABEL) {
				remove(collider);
				// number of bricks gets minus one.
				k--;
				vy *= -1;
				score += 100;
				gameGetsInteresting();
				scoreLabel.setLabel("score: " + score);
				// the sound will only be when the mute status is false. when we
				// press the button we change mute status from true to false or
				// from
				// false to true.
				if (MUTE_STATUS == false) {
					bounceClip.play();
				}
			}
			// if ball falls down.
			if (ball.getY() > HEIGHT - 2 * BALL_RADIUS) {
				// we will set its location.
				ball.setLocation(ballCoordinateX, ballCoordinateY);
				// we will set score.
				setScore();
				// we will remove heart. and get array list size - 1.
				remove(heart);
				ar.remove(ar.size() - 1);

				// if array list size will be 0, it means that there will be no
				// more hearts. it means it was our last turn and so we lost the
				// game.
				if (ar.size() == 0) {
					gameOver();
				}
				heart = ar.get(ar.size() - 1);
				scoreLabel.setLabel("score: " + score);
				break;
			}
			// if no bricks left, u win.
			if (k == 0) {
				youWon();
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
			// the sound will only be when the mute status is false. when we
			// press the button we change mute status from true to false or from
			// false to true.
			if (MUTE_STATUS == false) {
				bounceClip.play();
			}
		}
		if (ball.getX() > WIDTH - 2 * BALL_RADIUS) {
			vx *= -1;
			// if ball gets out of application we manually bring it back.
			double diff = ball.getX() - (WIDTH - 2 * BALL_RADIUS);
			ball.move(-2 * diff, 0);
			// the sound will only be when the mute status is false. when we
			// press the button we change mute status from true to false or from
			// false to true.
			if (MUTE_STATUS == false) {
				bounceClip.play();
			}
		}

		if (ball.getY() < 0) {
			vy *= -1;
			// the sound will only be when the mute status is false. when we
			// press the button we change mute status from true to false or from
			// false to true.
			if (MUTE_STATUS == false) {
				bounceClip.play();
			}
		}
	}

	// setting score.
	private void setScore() {
		if (score > 200) {
			score -= 300;
		}
		if (score > 400) {
			score -= 500;
		}
		if (score > 800) {
			score -= 900;
		}
		if (score > 1600) {
			score -= 1700;
		}
		if (score > 3200) {
			score -= 3300;
		}
		if (score > 6400) {
			score -= 6500;
		}
	}

	// ball will go faster and faster when u have more and more score.
	private void gameGetsInteresting() {
		if (score == 200) {
			vy *= 1.1;
		}
		if (score == 400) {
			vy *= 1.2;
		}
		if (score == 800) {
			vy *= 1.4;
		}
		if (score == 1600) {
			vy *= 1.8;
		}
	}

	// removes everything and adds label that u won.
	private void youWon() {
		removeAll();
		GLabel label = new GLabel("CONGRATULATIONS! YOU WON!");
		add(label, WIDTH / 2 - label.getWidth() / 2,
				HEIGHT / 2 - label.getAscent() / 2);
		GLabel finalScore = new GLabel("Your score is " + score);
		add(finalScore, (WIDTH - finalScore.getWidth()) / 2,
				finalScore.getAscent());
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
