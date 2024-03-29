package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import infra.persistance.Database;
/**
 *
 * @author Godofga
 */
public class Automation extends Thread {
    
    public boolean started = false;
    public int[][] move = new int[4][2];
    //Whites
    public final Color whiteT = new Color(255,255,255);
//    public final Color white1 = new Color(248,248,248);
    //public final Color white2 = new Color(249,249,249);
   // public final Color white3 = new Color(243,243,243);
    //Blacks
    public final Color blackT = new Color(4,8,8);
    public final Color blackT1 = new Color(3,7,7);
    public final Color blackT2 = new Color(3,8,8);
    public final Color blackT3 = new Color(4,7,7);
    
    //public final Color black1 = new Color(86,83,82);
    
    Robot robot;
    public Automation() throws AWTException{
        this.robot= new Robot();
        
    }
    public void checkSide(){
        Color piece= null;
        
        if(Database.getCompetitive()){
            switch(Database.getResolucao()){
                case 0:
                    piece = robot.getPixelColor(168,220);
                break;
                case 1:
                    piece = robot.getPixelColor(277,220);
                break;
                case 2:
                    //Colocar
                break;
            }
                
        }
            
        else{
            switch(Database.getResolucao()){
                case 0:
                    piece = robot.getPixelColor(225,196);
                break;
                case 1:
                    piece = robot.getPixelColor(338,190);
                break;
                case 2:
                    piece = robot.getPixelColor(260,177);
                break;
            }
        }
            
        
        if(checkColor(piece)==1)
            Database.setSide(false);
        else if(checkColor(piece)==2)
            Database.setSide(true);
        
    }
    
    public int checkColor(Color c){
        
       if(c.equals(whiteT))
            return 1;
        else if(c.equals(blackT)||c.equals(blackT1)||c.equals(blackT2)||c.equals(blackT3))
            return 2;
        else
            return 0;
    }
    
    public void checkBoard(){
        
        for(int i=0;i<4;i++)
            for(int j=0;j<2;j++)
                move[i][j]=-1;
        
        System.out.println("\n\n\n");
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                int x=0, y=0;
                if(!Database.getCompetitive()){
                    
                    switch(Database.getResolucao()){
                        case 0:
                            x = 169 + j*111 + 55;
                            y = 101 + i*111 + 93;
                        break;
                        case 1:
                            x = 297 + j*109 + 56;
                            y = 134 + i*109 + 90;
                        break;
                        case 2:
                            x = 209 + j*91 + 52;
                            y = 101 + i*91 + 75;
                        break;
                    }
                    
                    
                    
                    
                }else{
                    
                    switch(Database.getResolucao()){
                        case 0:
                            x = 117 + j*101 + 51;
                            y = 136 + i*101 + 84;
                        break;
                        case 1:
                            x = 225 + j*104 + 52;
                            y = 136 + i*104 + 85;
                        break;
                        case 2:
                           //Adicionar
                        break;
                    }
                    
                }
                
                    int pixel = checkColor(robot.getPixelColor(x, y));
                    int board;
                    switch (Database.getBoardCharAt(i,j)) {
                        case 'R':
                        case 'N':
                        case 'B':
                        case 'Q':
                        case 'K':
                        case 'P':
                            if(Database.getWhite())
                                board=1;
                            else
                                board=2;
                            break;
                        case 'r':
                        case 'n':
                        case 'b':
                        case 'q':
                        case 'k':
                        case 'p':
                            if(Database.getWhite())
                                board=2;
                            else
                                board = 1;
                            
                            break;
                        default:
                            board=0;
                            break;
                }
                    if(pixel!=board){
                        System.out.print("|"+pixel+"-"+board+"|");
                        if(pixel==0){
                            //Board.board[i][j]='-';
                            if(move[0][0]==-1){
                                    move[0][0]=i;
                                    move[0][1]=j;
                            }else if(move[2][0]==-1){
                                    move[2][0]=i;
                                  move[2][1]=j;
                            }
                        }
                        else if(pixel==1||pixel==2){
//                            if((Database.white&&pixel==2)||(!Database.white&&pixel==1)){
//                                Database.enemyMove=true;
//                                
//                            }
                            if(move[1][0]==-1){
                                move[1][0]=i;
                                move[1][1]=j;
                            }else if(move[3][0]==-1){
                                move[3][0]=i;
                                move[3][1]=j;
                            }
                        }
                        
                    }
                    System.out.print("_"+ pixel);
            }
            System.out.print("_\n");
        }
        
        if(move[0][0]!=-1){
            
            
            
            if(move[2][0]!=-1){
                System.out.println("ROQUE INIMIGO!"+move[0][0]+move[0][1]+" - "+move[1][0]+move[1][1]+"|"+move[2][0]+move[2][1]+" - "+move[3][0]+move[3][1]);
                if(Database.getWhite()){
                    if(move[0][1]==6 || move[2][1]==6|| move[2][1]==6 || move[3][1]==6){
                        Database.setBoardCharAt(0,4,'-');
                        Database.setBoardCharAt(0,5,'r');
                        Database.setBoardCharAt(0,6,'k');
                        Database.setBoardCharAt(0,7,'-');
                        Database.addHistory(charMove(7,4,7,6));
                    }else{
                        
                        Database.setBoardCharAt(0,0,'-');
                        Database.setBoardCharAt(0,1,'-');
                        Database.setBoardCharAt(0,2,'k');
                        Database.setBoardCharAt(0,3,'r');
                        Database.setBoardCharAt(0,4,'-');
                        Database.addHistory(charMove(7,4,7,2));
                    }
                }else{
                    if(move[0][1]==0 || move[2][1]==0){
                        
                        Database.setBoardCharAt(0,0,'-');
                        Database.setBoardCharAt(0,1,'k');
                        Database.setBoardCharAt(0,2,'r');
                        Database.setBoardCharAt(0,3,'-');
                        Database.addHistory(charMove(0,3,0,1));
                        
                    }else{
                        
                        Database.setBoardCharAt(0,3,'-');
                        Database.setBoardCharAt(0,4,'r');
                        Database.setBoardCharAt(0,5,'k');
                        Database.setBoardCharAt(0,6,'-');
                        Database.setBoardCharAt(0,7,'-');
                        Database.addHistory(charMove(0,3,0,5));
                        
                    }
                }
                
                
                
            }else{
                System.out.println("\n move1: \n"+move[0][0]+"x"+move[0][1]+" - "+move[1][0]+"x"+move[1][1]);
                System.out.println("\n move2: \n"+move[2][0]+"x"+move[2][1]+" - "+move[3][0]+"x"+move[3][1]);
                Database.setBoardCharAt(move[1][0], move[1][1],Database.getBoardCharAt(move[0][0],move[0][1]));
                Database.setBoardCharAt(move[0][0],move[0][1],'-');
                if(Database.getWhite())
                    Database.addHistory(charMove(7-move[0][0],move[0][1],7-move[1][0],move[1][1]));
                else
                Database.addHistory(charMove(move[0][0],move[0][1],move[1][0],move[1][1]));
                
                if((Database.getBoardCharAt(move[1][0],move[1][1])=='p'||Database.getBoardCharAt(move[1][0],move[1][1])=='P'||
                    Database.getBoardCharAt(move[0][0],move[0][1])=='p'||Database.getBoardCharAt(move[0][0],move[0][1])=='P')
                        
                        &&(move[0][0]==7||move[0][0]==0||move[1][0]==7||move[1][0]==0))
                    Database.setHistory(Database.getHistory()+"q");
                
            }
            Database.setEnemyMove(true);
            System.out.println(Database.getHistory());
            
        } else if(Database.getHistory().equals("")&&Database.getWhite()&&Database.getWaiter()){
            Database.setEnemyMove(true);
        }
        
            
        
        
    }
    
    public int charToNumber(char u){
        int number = 0;
        switch(u){
            case 'a':
                number = 0;
                break;
            case 'b':
                number = 1;
                break;
            case 'c':
                number = 2;
                break;
            case 'd':
                number = 3;
                break;
            case 'e':
                number = 4;
                break;
            case 'f':
                number = 5;
                break;
            case 'g':
               number = 6;
               break;
            case 'h':
               number = 7;
               break;
               
        }
        if(!Database.getWhite()){
            number=7-number;
        }
        return number;
    }
    //bestmove e7e5 ponder g1e2
    public void checkBestMove(){
        if(!Database.getBestMove().equals("")){
            int x1=charToNumber(Database.getBestMove().charAt(0));
            int y1=Integer.parseInt(""+Database.getBestMove().charAt(1))-1;
            int x2=charToNumber(Database.getBestMove().charAt(2));
            int y2= Integer.parseInt(""+Database.getBestMove().charAt(3))-1;
            if(Database.getWhite()){
                y1=7-y1;
                y2=7-y2;
            }
            boolean promotioon=false; 
            if(Database.getBestMove().length()>4&&(Database.getBoardCharAt(y2,x2)=='P'||Database.getBoardCharAt(y2,x2)=='p'))
                if(Database.getBestMove().charAt(4)=='q'||Database.getBestMove().charAt(4)=='r'||Database.getBestMove().charAt(4)=='b'||Database.getBestMove().charAt(4)=='n')
                    promotioon=true;
            performMove(x1,y1,x2,y2,promotioon);
            Database.setBestMove("");
            boolean noteee=false;
            if(Database.getBoardCharAt(y1,x1)=='K'){//4767
                System.out.println("ROOOOOUE! (ou movimento comum com o rei)"+x1+y1+x2+y2);
                
                if(Database.getWhite()){
                    if((x1==4&&x2 == 2)){
                        
                        Database.setBoardCharAt(7,0,'-');
                        Database.setBoardCharAt(7,1,'-');
                        Database.setBoardCharAt(7,2,'K');
                        Database.setBoardCharAt(7,3,'R');
                        Database.setBoardCharAt(7,4,'-');
                        Database.addHistory(charMove(0,4,0,2));
                        
                        noteee=true;
                    }else if (x1==4&&x2==6){
                        
                        Database.setBoardCharAt(7,4,'-');
                        Database.setBoardCharAt(7,5,'R');
                        Database.setBoardCharAt(7,6,'K');
                        Database.setBoardCharAt(7,7,'-');
                        Database.addHistory(charMove(0,4,0,6));
                        
                        noteee=true;
                    } 
                }else{//3757
                    if(x1==3&&x2 == 1){
                        
                        Database.setBoardCharAt(7,0,'-');
                        Database.setBoardCharAt(7,1,'K');
                        Database.setBoardCharAt(7,2,'R');
                        Database.setBoardCharAt(7,3,'-');
                        Database.addHistory(charMove(7,3,7,1));
                        
                       
                        noteee=true;
                    }else if(x1==3&&x2==5){
                        
                        Database.setBoardCharAt(7,3,'-');
                        Database.setBoardCharAt(7,4,'R');
                        Database.setBoardCharAt(7,5,'K');
                        Database.setBoardCharAt(7,6,'-');
                        Database.setBoardCharAt(7,7,'-');
                        Database.addHistory(charMove(7,3,7,5));
                        noteee=true;
                    }
                }
                
                
            }if(!noteee){
                Database.setBoardCharAt(y2,x2,Database.getBoardCharAt(y1,x1));
                Database.setBoardCharAt(y1,x1,'-');
            }
            if(Database.getWhite()){
                y1=7-y1;
                y2=7-y2;
            }
            if(!noteee)
                Database.addHistory(charMove(y1,x1,y2,x2));
            if(Database.getBestMove().length()>4&&(Database.getBoardCharAt(y2,x2)=='P'||Database.getBoardCharAt(y2,x2)=='p'))
                if(Database.getBestMove().charAt(4)=='q'||Database.getBestMove().charAt(4)=='r'||Database.getBestMove().charAt(4)=='b'||Database.getBestMove().charAt(4)=='n')
                    Database.setHistory(Database.getHistory()+"q");
            
            Database.setWaiter(false);
        }
    }
    public void performMove(int x1,int y1, int x2, int y2, boolean promotion){
        System.out.println("BEST MOVE IN NUMBERS ->"+x1+y1+x2+y2);
        
        if(!Database.getCompetitive()){
            
            switch(Database.getResolucao()){
                        case 0:
                            x1 = 169 + x1*111 + 55;
                            y1 = 101 + y1*111 + 55;
                            x2 = 169 + x2*111 + 55;
                            y2 = 101 + y2*111 + 55;
                        break;
                        case 1:
                            x1 = 297 + x1*109 + 56;
                            y1 = 134 + y1*109 + 56;
                            x2 = 297 + x2*109 + 56;
                            y2 = 134 + y2*109 + 56;
                        break;
                        case 2:
                            x1 = 209 + x1*91 + 45;
                            y1 = 101 + y1*91 + 45;
                            x2 = 209 + x2*91 + 45;
                            y2 = 101 + y2*91 + 45;
                        break;
                    }
            
            
            
            
            
            
        } else{
            
            switch(Database.getResolucao()){
                        case 0:
                            x1 = 117 + x1*101 + 50;
                            y1 = 136 + y1*101 + 50;
                            x2 = 117 + x2*101 + 50;
                            y2 = 136 + y2*101 + 50;
                        break;
                        case 1:
                            x1 = 225 + x1*104 + 52;
                            y1 = 136 + y1*104 + 52;
                            x2 = 225 + x2*104 + 52;
                            y2 = 136 + y2*104 + 52;
                        break;
                        case 2:
                            //Adicionar
                        break;
                    }
            
            
            
            
        }
        System.out.println("COORDINATES:"+x1+"x"+y1+" - "+x2+"x"+y2);
        
        robot.mouseMove(x1, y1);
        robot.mouseMove(x1, y1);
        robot.mouseMove(x1, y1);
        robot.mouseMove(x1, y1);
        
        robot.delay(300);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(300);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        robot.mouseMove(x2, y2);
        robot.mouseMove(x2, y2);
        robot.mouseMove(x2, y2);
        robot.mouseMove(x2, y2);
        robot.delay(300);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(300);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        if(promotion){
            robot.delay(300);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(300);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        
        
        
    }
    public String charMove(int x1,int y1, int x2, int y2){
        String ret="";
        if(!Database.getWhite()){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        switch(y1){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
        }if(!Database.getWhite()){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        
        ret+=""+Integer.toString(x1+1);
        if(!Database.getWhite()){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        switch(y2){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
        }
        if(!Database.getWhite()){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        ret+=""+Integer.toString(x2+1);
        
        return ret;
        
    }
    
    @Override
    public void run(){
        try {
            
            while(true){
                    
                
                checkBoard();
                checkBestMove();
                Thread.sleep(1000);
                
            }
        
        
        } catch (InterruptedException ex) {
                Logger.getLogger(Automation.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
   }