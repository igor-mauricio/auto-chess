/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package autochess;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import static java.awt.event.InputEvent.BUTTON1_MASK;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    //public final Color black1 = new Color(86,83,82);
    
    Robot robot;
    public Automation() throws AWTException{
        this.robot= new Robot();
        
    }
    public void checkSide(){
        Color piece;
        
        if(Database.getCompetitive())
            piece = robot.getPixelColor(168,220);
        else
            piece = robot.getPixelColor(225,196);
        
        if(checkColor(piece)==1)
            Database.setSide(false);
        else if(checkColor(piece)==2)
            Database.setSide(true);
        
    }
    
    public int checkColor(Color c){
       // if(c.equals(white1)||c.equals(white2)||c.equals(white3))
       if(c.equals(whiteT))
            return 1;
        //else if(c.equals(black1))
        else if(c.equals(blackT)||c.equals(blackT1))
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
                int x, y;
                if(!Database.getCompetitive()){
                    x = 169 + j*111 + 55;
                    y = 101 + i*111 + 93;
                }else{
                    x = 117 + j*101 + 51;
                    y = 136 + i*101 + 84;
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
                Database.board[move[1][0]][move[1][1]]= Database.board[move[0][0]][move[0][1]];
                Database.board[move[0][0]][move[0][1]]= '-';
                if(Database.white)
                    Database.history+=" "+charMove(7-move[0][0],move[0][1],7-move[1][0],move[1][1]);
                else
                Database.history+=" "+charMove(move[0][0],move[0][1],move[1][0],move[1][1]);
                
                if((Database.board[move[1][0]][move[1][1]]=='p'||Database.board[move[1][0]][move[1][1]]=='P'||
                    Database.board[move[0][0]][move[0][1]]=='p'||Database.board[move[0][0]][move[0][1]]=='P')
                        
                        &&(move[0][0]==7||move[0][0]==0||move[1][0]==7||move[1][0]==0))
                    Database.history+="q";
                
            }
            Database.enemyMove=true;
            System.out.println(Database.history);
            
        } else if(Database.history.equals("")&&Database.white){
            Database.enemyMove=true;
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
        if(!Database.white){
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
            if(Database.white){
                y1=7-y1;
                y2=7-y2;
            }
            boolean promotioon=false; 
            if(Database.getBestMove().length()>4&&(Database.board[y2][x2]=='P'||Database.board[y2][x2]=='p'))
                if(Database.getBestMove().charAt(4)=='q'||Database.getBestMove().charAt(4)=='r'||Database.getBestMove().charAt(4)=='b'||Database.getBestMove().charAt(4)=='n')
                    promotioon=true;
            performMove(x1,y1,x2,y2,promotioon);
            Database.setBestMove("");
            boolean noteee=false;
            if(Database.board[y1][x1]=='K'){//4767
                System.out.println("ROOOOOUE! (ou movimento comum com o rei)"+x1+y1+x2+y2);
                
                if(Database.white){
                    if((x1==4&&x2 == 2)){
                        
                        Database.board[7][0]= '-';
                        Database.board[7][1]= '-';
                        Database.board[7][2]= 'K';
                        Database.board[7][3]= 'R';
                        Database.board[7][4]= '-';
                        Database.history+=" "+charMove(0,4,0,2);
                        noteee=true;
                    }else if (x1==4&&x2==6){
                        Database.board[7][4]= '-';
                        Database.board[7][5]= 'R';
                        Database.board[7][6]= 'K';
                        Database.board[7][7]= '-';
                        Database.history+=" "+charMove(0,4,0,6);
                        noteee=true;
                    } 
                }else{//3757
                    if(x1==3&&x2 == 1){
                        
                        Database.board[7][0]= '-';
                        Database.board[7][1]= 'K';
                        Database.board[7][2]= 'R';
                        Database.board[7][3]= '-';
                        
                        Database.history+=" "+charMove(7,3,7,1);
                        noteee=true;
                    }else if(x1==3&&x2==5){
                        Database.board[7][3]= '-';
                        Database.board[7][4]= 'R';
                        Database.board[7][5]= 'K';
                        Database.board[7][6]= '-';
                        Database.board[7][7]= '-';
                        
                        Database.history+=" "+charMove(7,3,7,5);
                        noteee=true;
                    }
                }
                
                
            }if(!noteee){
            Database.board[y2][x2]= Database.board[y1][x1];
            Database.board[y1][x1]= '-';}
            if(Database.white){
                y1=7-y1;
                y2=7-y2;
            }
            if(!noteee)
                Database.history+=" "+charMove(y1,x1,y2,x2);
            if(Database.getBestMove().length()>4&&(Database.board[y2][x2]=='P'||Database.board[y2][x2]=='p'))
                if(Database.getBestMove().charAt(4)=='q'||Database.getBestMove().charAt(4)=='r'||Database.getBestMove().charAt(4)=='b'||Database.getBestMove().charAt(4)=='n')
                    Database.history+="q";
            
            
        }
    }
    public void performMove(int x1,int y1, int x2, int y2, boolean promotion){
        System.out.println("BEST MOVE IN NUMBERS ->"+x1+y1+x2+y2);
        
        if(!Database.getCompetitive()){
            x1 = 169 + x1*111 + 55;
            y1 = 101 + y1*111 + 55;
            x2 = 169 + x2*111 + 55;
            y2 = 101 + y2*111 + 55;
            
        } else{
            x1 = 117 + x1*101 + 50;
            y1 = 136 + y1*101 + 50;
            x2 = 117 + x2*101 + 50;
            y2 = 136 + y2*101 + 50;
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
        if(!Database.white){
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
        }if(!Database.white){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        
        ret+=""+Integer.toString(x1+1);
        if(!Database.white){
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
        if(!Database.white){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        ret+=""+Integer.toString(x2+1);
        
        
//        if((Database.board[x2][y2]=='p'||Database.board[x1][y1]=='P')&&(x1==0||x2==7)){
//            ret+="q";
//            if(Database.board[x2][y2]=='p')
//                Database.board[x2][y2]='q';
//            else
//                Database.board[x2][y2]='Q';
//        }
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
/*
    BOT
    
    169,101 top-left
    1056,988 bottom-right
    
    888 x 888 board size
    
    111x111 square size 
    
    225x196 first pixel piece
    226 194   f p p Texture
    777,358 new game button
    
    COMP
    
    117,136 top-left
    924,943 bottom-rigt
    
    808 x 808 board size
    
    101x101 square size
    
    168,220 fist pixel piece
    
    508,520 new game button
    
    */

//