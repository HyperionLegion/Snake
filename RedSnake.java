import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.*;   		   //the Scanner class
import java.io.*;             //the File class
import javax.swing.*;         //the JOptionPane class
//To Do:
//change the code for efficiency
//Highscore
//highscore with kd picture
public class RedSnake extends Snake{
   public RenderPanel renderPanel;
   public Timer timer = new Timer(20, this);
   public ArrayList<Point> snakeParts = new ArrayList<Point>();
   public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
   public int ticks = 0, direction = DOWN, score, tailLength = 10, time = 0, choice, width = 805, length = 700, highscore;
   public Point head;
   public Apple apple;
   public final boolean RED = false;
   public boolean over = false, paused;
   public Dimension dim;
   public RedSnake(JFrame jframe){
      super();
   }
   public void actionPerformed(ActionEvent arg){
      renderPanel.repaint();
      ticks++;
      if (ticks % 2 == 0 && !over && !paused){
         time++;
         snakeParts.add(new Point(head.x, head.y));
      
         if (direction == UP){
            if (head.getY() - 1 >= 0 && noTailAt((int)head.getX(), (int)head.getY() - 1))
               head = new Point((int)head.getX(), (int)head.getY() - 1);
            else
               over = true;
         }
         if (direction == DOWN){
            if (head.getY() + 1 < 60 && noTailAt((int)head.getX(), (int)head.getY() + 1))
               head = new Point((int)head.getX(), (int)head.getY() + 1);
            else
               over = true;
         }
         if (direction == LEFT){
            if (head.getX() - 1 >= 0 && noTailAt((int)head.getX() - 1, (int)head.getY()))
               head = new Point((int)head.getX()- 1, (int)head.getY());
            else
               over = true;
         }
         if (direction == RIGHT){
         
            if (head.getX() + 1 < 80 && noTailAt((int)head.getX() + 1, (int)head.getY()))
               head = new Point((int)head.getX() + 1, (int)head.getY());
            else
               over = true;
         }
         if (snakeParts.size() > tailLength)
            snakeParts.remove(0);
            if (head.getX()==apple.getX()&&head.getY()==apple.getY()){
               score += 10;
               tailLength = tailLength + 3;
               apple.setLocation((int)(Math.random()*79), (int)(Math.random()*60));
         }
      }
       if(over){
         if(score>highscore){
            PrintStream outfile = null;
         try{
            outfile = new PrintStream(new FileOutputStream("scoreboard.txt"));
         }
             catch(FileNotFoundException e){
               JOptionPane.showMessageDialog(null,"The file could not be created.");
            } 
         String name = JOptionPane.showInputDialog("New High Score! \n Name: ");
         String date = JOptionPane.showInputDialog("Date: ");
         highscore = score;
         outfile.println(score + " " + name + " " + date);
         }
      }

   }      
   public void keyPressed(KeyEvent e)
   {
      int i = e.getKeyCode();
      if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
         direction = LEFT;
      if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
         direction = RIGHT;
      if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
         direction = UP;
      if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
         direction = DOWN;
   
      if (i == KeyEvent.VK_SPACE){
         if (over)
            startGame();
         else
            paused = !paused;
      }
   }

   public void keyReleased(KeyEvent e)
   {
      //abstract bruh
   }

   public void keyTyped(KeyEvent e)
   {
      //abstract bruh
   }
}