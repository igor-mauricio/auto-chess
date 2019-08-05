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
    public static boolean enemyMove = false;
    public static boolean ingame = true;
    public static boolean waiter = false;
    public static boolean competitive = false;
    public static char board[][];
    public static String bestMove="";
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
        if(!white){
            Board.board[0][3]= 'k';
            Board.board[0][4]= 'q';
            Board.board[7][3]= 'K';
            Board.board[7][4]= 'Q';
        } else{
             Board.board[0][3]= 'q';
            Board.board[0][4]= 'k';
            Board.board[7][3]= 'Q';
            Board.board[7][4]= 'K';
        }
    }
    public static void setCompetitive(Boolean competitive){
        Board.competitive = competitive;
    }
    public static void setBestMove(String bestMove){
        Board.bestMove = bestMove;
    }
    public static String getBestMove(){
        return Board.bestMove;
    }
    public static boolean getCompetitive(){
        return Board.competitive;
    }
    
    
    
    
    
}
