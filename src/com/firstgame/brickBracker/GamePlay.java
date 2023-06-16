package com.firstgame.brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 1;

    private int playerX = 310;

    private int ballposX = 350;
    private int ballposY = 350;
    private int balldirX = -1;
    private int balldirY = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // bachground
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D)graphics);

        // borders
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(680, 0, 3, 592);

        // scores
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(""+score, 590, 30);

        // paddle
        graphics.setColor(Color.GREEN);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.WHITE);
        graphics.fillOval(ballposX, ballposY, 20, 20);

        // victory
        if (totalBricks <= 0) {
            play = false;
            balldirX = 0;
            balldirY = 0;
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("You Won:", 262, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart ", 230, 350);
        }

        if (ballposY > 570) {
            play = false;
            balldirX = 0;
            balldirY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game Over!",270, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart ", 250, 350);
        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                balldirY = -balldirY;
            }

            A: for (int i = 0; i<map.map.length; i++) {
                for (int j = 0; j <map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 2;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += balldirX;
            ballposY += balldirY;
            if (ballposX < 0) {
                balldirX = -balldirX;
            }
            if (ballposY < 0) {
                balldirY = -balldirY;
            }
            if (ballposX > 670) {
                balldirX = -balldirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 578) {
                playerX = 578;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
