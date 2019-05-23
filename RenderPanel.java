import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.Dimension;
  // import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
public class RenderPanel extends JPanel{
   public JLabel label = new JLabel("");
   public JLabel message = new JLabel("");
        // public Dimension dim;
  //    public JFrame jframe;
   public JLabel appleImage ;
   public RenderPanel(int a){
      super();
    //  RedSnake yuh = new RedSnake(jframe);
      Snake yuh = new Snake(a);
   }
   public RenderPanel(){
      super();
       
   }
   public void paintComponent(Graphics g){
      setLayout(new BorderLayout());
      super.paintComponent(g);
      Snake snake = Snake.snake;
      label.setText("Score: " + snake.getScore() + ", Length: " + snake.getTailLength() + ", Time: " + snake.getTime() / 20);
      label.setFont((new Font("Comic Sans", Font.BOLD, 30)));
      label.setBorder(BorderFactory.createLineBorder(Color.black));
      add(label, BorderLayout.SOUTH);
      g.setColor(Color.GREEN);
      g.fillRect(0, 0, 800, 600);
      g.setColor(Color.ORANGE);
      g.fillRect(0, 600, 800, 700);
      g.setColor(Color.BLUE);
      ImageIcon icon = new ImageIcon("tongue.png");
      icon.paintIcon(this, g, (int)(snake.getX() * Snake.SCALE), (int)snake.getY()*Snake.SCALE);
      icon = new ImageIcon("apple.jpg");
      icon.paintIcon(this, g, (int)snake.appleGetX() * Snake.SCALE, (int)snake.appleGetY() * Snake.SCALE);
      for (Point point : snake.snakeParts){
         g.fillRect((int)point.getX() *Snake.SCALE, (int)point.getY() * Snake.SCALE, Snake.SCALE, Snake.SCALE);
      }
      g.fillRect((int)snake.head.getX() * Snake.SCALE, (int)snake.head.getY() * Snake.SCALE, Snake.SCALE, Snake.SCALE);
      message.setFont((new Font("Comic Sans", Font.BOLD, 30)));
      if (snake.over){
         message.setFont((new Font("Comic Sans", Font.BOLD, 70)));
         message.setHorizontalTextPosition(JLabel.CENTER);
         message.setText("Game Over!" );
         add(message, BorderLayout.CENTER);
      }
      if (snake.paused && !snake.over){
         message.setText("Paused!" );
         add(message, BorderLayout.NORTH);
      }
      if(!snake.paused&&!snake.over){
         message.setText("");
         add(message, BorderLayout.CENTER);
      }
     /* if(snake.score>snake.highscore&&snake.over){
       message.setFont((new Font("Comic Sans", Font.BOLD, 35)));
         message.setHorizontalTextPosition(JLabel.CENTER);
         message.setText("NEW HIGH SCORE!");
         add(message, BorderLayout.CENTER);
          icon = new ImageIcon("kd.jpg");
      icon.paintIcon(this, g, 0, 0);
      }*/
   }
}