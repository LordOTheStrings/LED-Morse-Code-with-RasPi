/* Connor Morley
 * Mike Beradino
 * IB Computer Science SL
 * 17 April, 2019
 * All code snippets credited below
 */ 


/*importing various Pi4J libraries and methods to execute program, as well as other
 *Java methods.
 */

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.io.*;
import java.lang.System.*;

import java.time.*;


/**
 * This is based off of an example code demonstrating how to perform simple
 * blinking LED logic of a GPIO pin on the Raspberry Pi using the Pi4J library,
 * taken from the official Pi4J examples at 
 * https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/BlinkGpioExample.java.
 *
 * @author Robert Savage
 */
public class LEDMorseCode {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("<--Pi4J--> GPIO Blink Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 & #03 as an output pins and blink
        final GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
int count = 1;
int saved_counter = 1;
boolean run_count = false;
boolean once = false;
           
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    
                    if(event.getState().isHigh()){

                      led.high();
                      run_count = true; //begins counter

                    }
                      if(event.getState().isLow()){

                      led.low();
                      run_count = false; //does not begin counter
                      
                      try {
                            Thread.sleep(10);
                        }
                           catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                      }

                      
                      
                    }
                     
                    while (run_count == true){
                        count = count +1; //counts elapsed time in seconds when LED is on
                        
                         
                       // System.out.println("while " + count); // this slows it down
                        
                    }
                      if(count > 19000000 && run_count == false ) { /* if count is greater than a particular time frame and 
                      is low, print a dash.
                      */
                        try { 
                            FileWriter fw = new FileWriter("morsecode.txt", true);
                            PrintWriter pw = new PrintWriter(fw);
                            
                            pw.println("_");
                            System.out.println("time _ " + count);
                            
                            count = 0;

                           
                            pw.close();
                           
                        } catch (IOException e) {
                                System.out.println("ERROR!");
                      }
                      
                   } 
                   if (count > 2 && count < 19000000 && run_count == false) { /* If count was greater than two and less
                       than this certain time frame, print a dot.
                       */
                       try {
                          
                           FileWriter fw = new FileWriter("morsecode.txt", true);
                           PrintWriter pw = new PrintWriter(fw);
                           
                           pw.println(".");
                           
                           
                           System.out.println("time . " + count);
                           //saved_counter =0;
                           count = 0;
                           
                           pw.close();
                           
                        } catch (IOException e) {
                            System.out.println("ERROR!");
                      }
                       
            }
                   // otherwise, the LED remains off.
                   else {
                     led.low();
                    }
                }
            });

        System.out.println(" ... press the button quickly for a dot. Press longer for a dash.");
        System.out.println(" ... PRESS OPTIONS > CLOSE TO STOP THE PROGRAM.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}

