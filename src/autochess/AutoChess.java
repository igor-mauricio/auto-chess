/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autochess;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author Godofga
 */
public class AutoChess extends Thread {

    /**
     */
    Observer obs;
    Random rand = new Random();
    
    @Override
    public void run()  {
        try{
        obs= new Observer();
        
        Process processo = Runtime.getRuntime().exec("C:\\Users\\Godofga\\Desktop\\stockfish-10-win\\Windows\\stockfish_10_x64.exe");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(processo.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(processo.getInputStream()));
        String input;
        out.write("uci\n");
        System.out.println("->\nuci\n<-\n");
        out.flush();
        
            while((input = in.readLine()) !=null){
                System.out.println(input);
                System.out.println("-----");
                if(input.equals("uciok")){

                    String options = 
    //                        "setoption name Contempt value 24\n"+
    //                        "setoption name Analysis Contempt value Both\n"+
    //                        "setoption name Threads value 1\n"+
                            "setoption name Hash value 128\n"
    //                      + "setoption name Ponder value false\n"+
    //                        "setoption name MultiPV value 1\n"+
    //                        "setoption name Skill Level value 20\n"+
    //                        "setoption name Move Overhead value 30\n"+
    //                        "setoption name Minimum Thinking Time value 20\n"+
    //                        "setoption name Slow Mover value 84\n"+
    //                        "setoption name nodestime value 0\n"+
    //                        "setoption name UCI_Chess960 value false\n"+
    //                        "setoption name UCI_AnalyseMode value false\n"+
    //                        "setoption name SyzygyPath value <empty>\n"+
    //                        "setoption name SyzygyProbeDepth value 1\n"+
    //                        "setoption name Slow Mover value 84\n"+
    //                        "setoption name Syzygy50MoveRule value true\n"+
    //                        "setoption name SyzygyProbeLimit value 7\n"+
                            //"isready\n"
                            ;

                    out.write(options);
                    System.out.println("->\n"+options+"<-\n");

                    //Start game
                    String newGame = "ucinewgame\n"
                            + "setoption name UCI_AnalyseMode value true\n";
                    out.write(newGame);
                    System.out.println("->\n"+newGame+"<-\n");
                     
                     System.out.println("Esperando movimentos inimigos");
                    while(true){
                        if(Board.enemyMove){
                            String message = "position startpos moves"+Board.history+"\n"
                            + "go movetime 100\n" ;//+((rand.nextInt(10)+6)*1000)+"\n";
                            System.out.println("Movimento Inimigo! Procurando bestmove");
                            Board.enemyMove=false;
                            System.out.println(message);
                            out.write(message);
                            break;
                        }
                        Thread.sleep(1000);
                    }
                    out.flush();
                    System.out.println("endloop");
                }
                if(input.contains("bestmove")){
                        if(input.charAt(13)!=' ')
                            Board.setBestMove(""+input.charAt(9)+input.charAt(10)+input.charAt(11)+input.charAt(12)+input.charAt(13));
                        else{
                            Board.setBestMove(""+input.charAt(9)+input.charAt(10)+input.charAt(11)+input.charAt(12));
                        }
                        
                        while(true){
                            if(Board.enemyMove){
                                String message = "position startpos moves"+Board.history+"\n"
                                + "go movetime 100\n";// +((rand.nextInt(7)+6)*1000)+"\n";
                                System.out.println("Movimento Inimigo! Procurando bestmove");
                                Board.enemyMove=false;
                                System.out.println(message);
                                out.write(message);
                                break;
                            }
                            Thread.sleep(1000);
                        }
                    out.flush();
                        
                    }
                
                
             }
            System.out.println("Looop finished");
            
            
        
                    
                
            
        
        } catch (AWTException ex) {
            Logger.getLogger(AutoChess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AutoChess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AutoChess.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
    
//                out.write("quit\n");
//                processo.destroy();
    
    
}
