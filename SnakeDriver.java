import javax.swing.JOptionPane;
import java.io.*;       		 //the File class
import java.util.*;     		 //the Scanner class
import javax.swing.JLabel;
public class SnakeDriver{
   public static RenderPanel yuh;
   public static void main(String[] args) {
      int choice = 0;
      boolean play = false;
      while(!play){
         String message = "";
         message = message + "\n1. Play";
         message = message + "\n2. About.";
         message = message + "\n3. Quit.";
         message = message + "\n4. GitHub.";
         choice = Integer.parseInt(JOptionPane.showInputDialog(message));
         if(choice == 1){
            yuh = new RenderPanel(1);
            play = true;
         }
         if(choice==2){
            Scanner infile = null;
            try{
               infile = new Scanner(new File("readme.txt") );
            }
            catch(FileNotFoundException e){
               JOptionPane.showMessageDialog(null, "Error: File not found.");
               System.exit(0);
            }
            while(infile.hasNextLine()){
               System.out.println(infile.nextLine());
            }
         }
         if(choice==3){
            System.out.println("Come again next time!");
            System.exit(0);
         }
         if(choice==4){
            System.out.println("GitHub Link: https://github.com/HyperionLegion/Snake");
         }
      }
   }
}   
