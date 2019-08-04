/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package autochess;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Godofga
 */
public class Observer extends Thread {
    
    public boolean started = false;
    public boolean paused = false;
    public int[][] move = new int[4][2];
    public int[][] cache = new int[8][8];
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
    public void initializeCache(){
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++){
                if(Board.board[i][j]=='R'||Board.board[i][j]=='N'||Board.board[i][j]=='B'||Board.board[i][j]=='Q'||Board.board[i][j]=='K')
                    if(Board.white)
                        cache[i][j]=1;
                    else
                        cache[i][j]=2;
                else if(Board.board[i][j]=='r'||Board.board[i][j]=='n'||Board.board[i][j]=='b'||Board.board[i][j]=='q'||Board.board[i][j]=='k')
                    if(Board.white)
                        cache[i][j]=2;
                    else
                        cache[i][j]=1;
                else
                    cache[i][j]=0;
            }
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
                    int cCache = cache[i][j];
                    int board;
                    if(Board.board[i][j]=='R'||Board.board[i][j]=='N'||Board.board[i][j]=='B'||Board.board[i][j]=='Q'||Board.board[i][j]=='K')
                        if(Board.white)
                            board=1;
                        else
                            board=2;
                    else if(Board.board[i][j]=='r'||Board.board[i][j]=='n'||Board.board[i][j]=='b'||Board.board[i][j]=='q'||Board.board[i][j]=='k')
                        if(Board.white)
                            board=2;
                        else
                            board=1;
                    else
                        board=0;
                    
                    if(pixel!=cCache){
                        
                        cache[i][j]=pixel;
                    } else if(pixel==cCache&&cCache!=board){
                        if(pixel==0)
                            Board.board[i][j]='-';
                            if(move[0][0]==-1){
                                move[0][0]=i;
                                move[0][1]=j;
                            }else if(move[2][0]==-1){
                                move[2][0]=i;
                                move[2][1]=j;
                            }
                        if(pixel==1||pixel==2){
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
                
                
                //ROQUE --------------------------------------------------
                
                
                
                
            }else{
                Board.board[move[1][0]][move[1][1]]= Board.board[move[0][0]][move[0][1]];
                Board.board[move[0][0]][move[0][1]]= '-';
                Board.history.concat(charMove(move[0][0],move[0][1],move[1][0],move[1][1]));
            }
            
        }
        
        
    }
    public String charMove(int x1,int y1, int x2, int y2){
        String ret="";
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
        }
        
        ret+=""+Integer.toString(x1+1);
        
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
        ret+=""+Integer.toString(x2+1);
        if((Board.board[x2][y2]=='p'||Board.board[x1][y1]=='P')&&(y2==0||y2==7)){
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
                if(!paused){
                    
                
                checkBoard();

                Thread.sleep(1000);
                }
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