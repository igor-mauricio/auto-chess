/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autochess;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godofga
 */
public class PingThread extends Thread {
    BufferedWriter out= null;
    @Override
    public void run(){
        while(true){
            try {
                if(out!=null){
                    out.write("isready\n");
                }
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public void setBufferedWriter(BufferedWriter bw)
    {
        this.out= bw;
    }
}
