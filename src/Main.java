import com.firstgame.brickBracker.GamePlay;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame object = new JFrame();
        GamePlay gamePlay = new GamePlay();
        object.setBounds(10, 10, 700, 600);
        object.setTitle("Breakout ball");
        object.setResizable(false);
        object.setVisible(true);
        object.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        object.add(gamePlay);



    }
}