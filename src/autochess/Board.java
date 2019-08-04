/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autochess;

/**
 *
 * @author Godofga
 */
public class Board {
    
    public static String history= "";
    public static boolean white = true;
    public static boolean aiTurn = false;
    public static boolean ingame = true;
    public static boolean competitive = false;
    public static char board[][];
    public static char initialBoard[][] = {{'r','n','b','q','k','b','n','r'}
            ,{'p','p','p','p','p','p','p','p'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'P','P','P','P','P','P','P','P'}
            ,{'R','N','B','Q','K','B','N','R'}
            };
    
    public static void resetBoard(){
        board = initialBoard.clone();
    }
    public static void setSide(Boolean white){
        Board.white=white;
        aiTurn= white;
    }
    public static void setCompetitive(Boolean competitive){
        Board.competitive = competitive;
    }
    
    
    
    
    
}
