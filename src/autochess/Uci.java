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
public class Uci extends Thread {

    Automation obs;
    Random rand = new Random();
    
    @Override
    public void run()  {
        
        try{
            obs= new Automation();
            
            //Diretório da engine que usa protocolo UCI
            Process processo = Runtime.getRuntime().exec("src/Engines/komodo-10-64bit.exe");
            
            //Buffers de entrada e saída
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(processo.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(processo.getInputStream()));
            
            String input;
            
            //Iniciando protocolo UCI
            out.write("uci\n");
            
            System.out.println("->\nuci\n<-\n");
            
            out.flush();
            
            //Aguardando as saídas da engine e repetindo
            while((input = in.readLine()) !=null){
                
                //Resposta ao comando 'uci'
                if(input.equals("uciok")){
                    
                    //Inserindo configurações da engine
                    String options = "setoption name Hash value 128\n"
    //                      + "setoption name Contempt value 24\n"+
    //                        "setoption name Analysis Contempt value Both\n"+
    //                        "setoption name Threads value 1\n"+
    //                        "setoption name Ponder value false\n"+
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
    //                        "setoption name SyzygyProbeLimit value 7\n"
                        ;
                    out.write(options);
                    System.out.println("->\n"+options+"<-\n");
                    
                    //Iniciando partida
                    String newGame = "ucinewgame\n"
                            + "setoption name UCI_AnalyseMode value true\n";
                    out.write(newGame);
                    System.out.println("->\n"+newGame+"<-\n");
                     
                    System.out.println("Waiting for enemy moves");
                    while(true){
                        //Verificando se houve movimento inimigo
                        if(Database.getEnemyMove()&&!Database.getWaiter()){
                            
                            System.out.println("Enemy move! Searching a good response");
                            
                            //Buscando melhor movimento
                            String message = "position startpos moves"+Database.getHistory()+"\n"
                            + "go movetime " +((rand.nextInt(1)+6)*1000)+"\n";
                            out.write(message);
                            System.out.println(message);
                            
                            Database.setWaiter(true);
                            Database.setEnemyMove(false); 
                            break;
                            
                        }
                        //Tempo de espera para uma nova verificação
                        Thread.sleep(1000);
                    }
                    out.flush();
                }
                if(input.contains("bestmove")){
                    System.out.println(input);
                    
                        //Pondo o dígito q na jogada caso haja
                        if(input.charAt(13)=='q')
                            Database.setBestMove(""+input.charAt(9)+input.charAt(10)+input.charAt(11)+input.charAt(12)+input.charAt(13));
                        else{
                            Database.setBestMove(""+input.charAt(9)+input.charAt(10)+input.charAt(11)+input.charAt(12));
                        }
                        
                        while(true){
                            
                            //Verificando se houve movimento inimigo
                            if(Database.getEnemyMove()&&!Database.getWaiter()){
                                
                                //Buscando melhor movimento
                                String message = "position startpos moves"+Database.getHistory()+"\n"
                                + "go movetime " +((rand.nextInt(10)+3)*1000)+"\n";
                                System.out.println("Movimento Inimigo! Procurando bestmove");
                                Database.setEnemyMove(false);
                                Database.setWaiter(true);
                                System.out.println(message);
                                out.write(message);
                                break;
                            }
                            //Tempo de espera para uma nova verificação
                            Thread.sleep(1000);
                        }
                    out.flush();
                }
            }
            
        } catch (AWTException ex) {
            Logger.getLogger(Uci.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Uci.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Uci.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
