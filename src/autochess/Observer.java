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
public class Observer extends Thread {
    
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
    public Observer() throws AWTException{
        this.robot= new Robot();
        
    }
    public void checkSide(){
        Color piece;
        
        if(Board.competitive)
            piece = robot.getPixelColor(168,220);
        else
            piece = robot.getPixelColor(225,196);
        
        if(checkColor(piece)==1)
            Board.setSide(false);
        else if(checkColor(piece)==2)
            Board.setSide(true);
        
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
                if(!Board.competitive){
                    x = 169 + j*111 + 55;
                    y = 101 + i*111 + 93;
                }else{
                    x = 117 + j*101 + 51;
                    y = 136 + i*101 + 84;
                }
                
                    int pixel = checkColor(robot.getPixelColor(x, y));
                    int board;
                    switch (Board.board[i][j]) {
                        case 'R':
                        case 'N':
                        case 'B':
                        case 'Q':
                        case 'K':
                        case 'P':
                            if(Board.white)
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
                            if(Board.white)
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
//                            if((Board.white&&pixel==2)||(!Board.white&&pixel==1)){
//                                Board.enemyMove=true;
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
                if(Board.white){
                    if(move[0][0]==0 || move[2][0]==0){
                        
                        Board.board[0][0]= '-';
                        Board.board[0][1]= '-';
                        Board.board[0][2]= 'k';
                        Board.board[0][3]= 'r';
                        Board.board[0][4]= '-';
                        Board.history+=" "+charMove(0,4,0,4);
                    }else{
                        Board.board[0][4]= '-';
                        Board.board[0][5]= 'r';
                        Board.board[0][6]= 'k';
                        Board.board[0][7]= '-';
                        Board.history+=" "+charMove(0,4,0,6);
                    }
                }else{
                    if(move[0][0]==0 || move[2][0]==0){
                        
                        Board.board[0][0]= '-';
                        Board.board[0][1]= 'k';
                        Board.board[0][2]= 'r';
                        Board.board[0][3]= '-';
                        
                        Board.history+=" "+charMove(0,3,0,1);
                    }else{
                        Board.board[0][3]= '-';
                        Board.board[0][4]= 'r';
                        Board.board[0][5]= 'k';
                        Board.board[0][6]= '-';
                        Board.board[0][7]= '-';
                        
                        Board.history+=" "+charMove(0,3,0,5);
                    }
                }
                
                
                
            }else{
                System.out.println("\n move1: \n"+move[0][0]+"x"+move[0][1]+" - "+move[1][0]+"x"+move[1][1]);
                System.out.println("\n move2: \n"+move[2][0]+"x"+move[2][1]+" - "+move[3][0]+"x"+move[3][1]);
                Board.board[move[1][0]][move[1][1]]= Board.board[move[0][0]][move[0][1]];
                Board.board[move[0][0]][move[0][1]]= '-';
                if(Board.white)
                    Board.history+=" "+charMove(7-move[0][0],move[0][1],7-move[1][0],move[1][1]);
                else
                Board.history+=" "+charMove(move[0][0],move[0][1],move[1][0],move[1][1]);
                
            }
            Board.enemyMove=true;
            System.out.println(Board.history);
            
        } else if(Board.getBestMove().equals("")&&Board.white&&!Board.waiter){
            Board.enemyMove=true;
            Board.waiter=true;
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
        if(!Board.white){
            number=7-number;
        }
        return number;
    }
    //bestmove e7e5 ponder g1e2
    public void checkBestMove(){
        if(!Board.getBestMove().equals("")){
            int x1=charToNumber(Board.getBestMove().charAt(0));
            int y1=Integer.parseInt(""+Board.getBestMove().charAt(1))-1;
            int x2=charToNumber(Board.getBestMove().charAt(2));
            int y2= Integer.parseInt(""+Board.getBestMove().charAt(3))-1;
            if(Board.white){
                y1=7-y1;
                y2=7-y2;
            }
            performMove(x1,y1,x2,y2,(Board.getBestMove().length()>4));
            Board.setBestMove("");
            if(Board.board[y1][x1]=='K'){
                
                
                if(Board.white){
                    if((y2 == 5)){
                        
                        Board.board[7][0]= '-';
                        Board.board[7][1]= '-';
                        Board.board[7][2]= 'k';
                        Board.board[7][3]= 'r';
                        Board.board[7][4]= '-';
                        Board.history+=" "+charMove(7,4,7,4);
                    }else if (y2==1){
                        Board.board[7][4]= '-';
                        Board.board[7][5]= 'r';
                        Board.board[7][6]= 'k';
                        Board.board[7][7]= '-';
                        Board.history+=" "+charMove(7,4,7,6);
                    }
                }else{
                    if(y2 == 7){
                        
                        Board.board[7][0]= '-';
                        Board.board[7][1]= 'k';
                        Board.board[7][2]= 'r';
                        Board.board[7][3]= '-';
                        
                        Board.history+=" "+charMove(7,3,7,1);
                    }else if(y2==2){
                        Board.board[7][3]= '-';
                        Board.board[7][4]= 'r';
                        Board.board[7][5]= 'k';
                        Board.board[7][6]= '-';
                        Board.board[7][7]= '-';
                        
                        Board.history+=" "+charMove(7,3,7,5);
                    }
                }
                
                
            }
            Board.board[y2][x2]= Board.board[y1][x1];
            Board.board[y1][x1]= '-';
            if(Board.white){
                y1=7-y1;
                y2=7-y2;
            }
            Board.history+=" "+charMove(y1,x1,y2,x2);
            if(Board.getBestMove().length()>4)
                 Board.history+="q";
            
            Board.waiter=false;
            
        }
    }
    public void performMove(int x1,int y1, int x2, int y2, boolean promotion){
        System.out.println("BEST MOVE IN NUMBERS ->"+x1+y1+x2+y2);
        
        if(!Board.getCompetitive()){
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
        if(!Board.white){
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
        }if(!Board.white){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        
        ret+=""+Integer.toString(x1+1);
        if(!Board.white){
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
        if(!Board.white){
            x1=7-x1;
            y1=7-y1;
            x2=7-x2;
            y2=7-y2;
        }
        ret+=""+Integer.toString(x2+1);
        
        
        if((Board.board[x2][y2]=='p'||Board.board[x1][y1]=='P')&&(x1==0||x2==7)){
            ret+="q";
            if(Board.board[x2][y2]=='p')
                Board.board[x2][y2]='q';
            else
                Board.board[x2][y2]='Q';
        }
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
                Logger.getLogger(Observer.class.getName()).log(Level.SEVERE, null, ex);
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