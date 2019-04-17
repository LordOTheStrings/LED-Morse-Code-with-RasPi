/* Connor Morley
 * Mike Beradino
 * IB Computer Science SL
 * 17 April, 2019
 * All code snippets credited below
 */ 


//importing various libraries and methods to execute program

import java.io.*;
import static java.lang.System.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class JavaGui {
  public static void main(String[] args) {
  
  // creating the actual GUI itself    
  JFrame f = new JFrame("Morse Code GUI for LED and Button");
  f.setSize(500, 250); // Credit to Jorge's original GUI code for these |
  f.setLocation(300,200);                                             //V
  JTextArea textArea = new JTextArea(10, 40);
  textArea.setEditable(false);
  f.getContentPane().add(BorderLayout.CENTER, textArea);
  JButton button = new JButton("Get Your Morse Code!");
  f.getContentPane().add(BorderLayout.NORTH, button);
  button.addActionListener(new ActionListener()
  {
      public void actionPerformed(ActionEvent j) {
        
         /* code snippet lifed from Andy Wicks' YouTube video on how to 
          * read and write text files in Java.
          */ 
          try {
         FileReader fr = new FileReader("morsecode.txt");
         BufferedReader br = new BufferedReader(fr);
  
         String str;
         while ((str = br.readLine()) != null) {
             textArea.append(str + "\n"); 
        }
        br.close();
    } catch (IOException e) {
      textArea.append("File Not Found");  
     }
  };
});
// makes the GUI visible to the user
 f.setVisible(true);
 } 
}