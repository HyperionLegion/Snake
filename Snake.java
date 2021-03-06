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
import java.util.concurrent.LinkedBlockingQueue;
//To Do:
//change the code for efficiency
//Highscore
//Apple class
//input images
//highscore with kd picture
public class Snake extends Object implements ActionListener, KeyListener {
   public static Snake snake;
   public JFrame jframe;
   public RenderPanel renderPanel;
   public Timer timer = new Timer(20, this);
   public ArrayList<Point> snakeParts = new ArrayList<Point>();
   public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
   public int ticks = 0, direction = DOWN, score, tailLength = 10, time = 0, choice, width = 805, length = 700, highscore;
   public Point head;
   public Apple apple;
   public boolean over = false, paused;
   public Dimension dim;
   public Queue<Integer> moveBuffer; // buffer movements and process only one per tick
   public Snake(){
      dim = Toolkit.getDefaultToolkit().getScreenSize();
      moveBuffer = new LinkedBlockingQueue<Integer>();
      jframe = new JFrame("Snake");
      jframe.setVisible(true);
      jframe.setSize(805, 700);
      jframe.setResizable(false);
      jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
      jframe.add(renderPanel = new RenderPanel());
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.addKeyListener(this);
      startGame();
   }
   public Snake(int a ){
      snake = new Snake();
   }
   public Snake(JFrame jframe){
      dim = Toolkit.getDefaultToolkit().getScreenSize();
      jframe = new JFrame("Snake");
      jframe.setVisible(true);
      jframe.setSize(805, 700);
      jframe.setResizable(false);
      jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
      jframe.add(renderPanel = new RenderPanel());
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.addKeyListener(this);
      startGame();
   }
   public void startGame(){
      Scanner infile = null;
      try{
         infile = new Scanner(new File("scoreboard.txt"));
      }
      catch(FileNotFoundException e){
         JOptionPane.showMessageDialog(null,"The file could not be found.");
         System.exit(0);
      }
      highscore = infile.nextInt();
      over =  paused = false;
      time = 0;
      score = 0;
      tailLength = 5;
      ticks = 0;
      head = new Point((int)(Math.random()*79), (int)(Math.random()*60));
      if(head.getX()>head.getY()){ //random spawn and fair direction according to position
         if(head.getX()>=30){
            direction = LEFT;
         }
         else if(head.getX()<30){
            direction = RIGHT;
         }
      }
      else{
         if(head.getY()>=30)
            direction = UP;
         if(head.getY()<30)
            direction = DOWN;
      }
      snakeParts.clear();
      apple = new Apple();
      timer.start();
   }
   public void actionPerformed(ActionEvent arg){
      renderPanel.repaint();
      ticks++;
      if (ticks % 2 == 0 && !over && !paused){
         time++;
         snakeParts.add(new Point(head.x, head.y));
         // process any buffered movements
         if (moveBuffer.peek() != null)
         {
            int move = moveBuffer.remove();
            if (move == UP && direction != DOWN ||
                move == DOWN && direction != UP ||
                move == RIGHT && direction != LEFT ||
                move == LEFT && direction != RIGHT)
                direction = move;
         }
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
         if (snakeParts.size() > tailLength){
            snakeParts.remove(0);
         }
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
    public boolean noTailAt(int x, int y){
      for (Point point : snakeParts){
         if (point.equals(new Point(x, y)))
            return false;
      }
      return true;
   } 
   public void keyPressed(KeyEvent e){
      int i = e.getKeyCode();
      if (i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT)
         moveBuffer.add(LEFT);
      if (i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT)
         moveBuffer.add(RIGHT);
      if (i == KeyEvent.VK_W || i == KeyEvent.VK_UP)
         moveBuffer.add(UP);
      if (i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN)
         moveBuffer.add(DOWN);
   
      if (i == KeyEvent.VK_SPACE){
         if (over)
            startGame();
         else{
            paused = !paused;
         }
      }
   }
   public int appleGetX(){
      return apple.getX();
   }
   public int appleGetY(){
      return apple.getY();
   }
   public int getX(){
      return (int)head.getX();
   }
   public int getY(){
      return (int)head.getY();
   }
   public int getScore(){
      return score;
   }
   public int getTime(){
      return time;
   }
   public int getTailLength(){
      return tailLength;
   }
   public void keyReleased(KeyEvent e){
   }
   public void keyTyped(KeyEvent e){
   }
}