import java.awt.Dimension;
import java.awt.Point;

//To Do:
//change the code for efficiency
//Highscore
//Apple class
//input images
//highscore with kd picture
public class Apple extends Object{
   public Point apple;
   public boolean over = false, paused;
   public Dimension dim;
   public int myX = 0, myY = 0;
   public Apple(int a, int b){
      apple = new Point(a, b);
      myX = a;
      myY = b;
   }
   public Apple(){
      int a= (int)(Math.random()*79);
      int b = (int)(Math.random()*60);
      apple = new Point(a, b);
      myX = a;
      myY = b;
   }
   public void setLocation(int a, int b){
      apple.setLocation(a, b);
       myX = a;
       myY = b;
   }
   public int getX(){
      return myX;
   }
   public int getY(){
      return myY;
   }
  }