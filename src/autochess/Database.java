package autochess;

/**
 *
 * @author Godofga
 */
public class Database {
    
    //Histórico de movimentos
    private static String history= "";
    //Lado do jogador
    private static boolean white = true;
    //Movimento inimigo
    private static boolean enemyMove = false;
    //Modo de jogo
    private static boolean competitive = false;
    //Aguardar o próprio movimento antes de requerir uma nova predição
    private static boolean waiter = false;
    //Array do tabuleiro virtual
    private static char board[][];
    //Melhor jogada
    private static String bestMove="";
    //Resolucao
    /*
        0-1680x1050
        1-1920x1080
        2-1600x900
    */
    
    private static int resolucao=0;
    
    
    //Tabuleiro inicial
    private static char initialBoard[][] = {{'r','n','b','q','k','b','n','r'}
            ,{'p','p','p','p','p','p','p','p'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'-','-','-','-','-','-','-','-'}
            ,{'P','P','P','P','P','P','P','P'}
            ,{'R','N','B','Q','K','B','N','R'}
            };
    //Resetando tabuleiro
    public static void resetBoard(){
        board = initialBoard.clone();
    }
    
    //Estabelecendo lado
    public static void setSide(Boolean white){
        Database.white=white;
        if(!white){
            Database.board[0][3]= 'k';
            Database.board[0][4]= 'q';
            Database.board[7][3]= 'K';
            Database.board[7][4]= 'Q';
        } else{
            Database.board[0][3]= 'q';
            Database.board[0][4]= 'k';
            Database.board[7][3]= 'Q';
            Database.board[7][4]= 'K';
        }
    }
    
    public static char getBoardCharAt(int x, int y){
        return Database.board[x][y];
    }
    public static void setBoardCharAt(int x, int y, char character){
        Database.board[x][y] = character;
    }
    public static String getBestMove(){
        return Database.bestMove;
    }
    public static void setBestMove(String bestMove){
        Database.bestMove = bestMove;
    }
    
    public static boolean getEnemyMove(){
        return Database.enemyMove;
    }
    public static void setEnemyMove(boolean enemyMove){
        Database.enemyMove = enemyMove;
    }
    
    public static boolean getCompetitive(){
        return Database.competitive;
    }
    public static void setCompetitive(boolean competitive){
        Database.competitive= competitive;
    }
    
    public static boolean getWhite(){
        return Database.white;
    }
    public static void setWhite(boolean white){
        Database.white= white;
    }
 
    public static String getHistory(){
        return Database.history;
    }
    public static void setHistory(String history){
        Database.history = history;
    }
    public static void addHistory(String history){
        Database.history += " "+history;
    }
    
    public static void resetHistory(){
        Database.bestMove = "";
    }
    
    public static int getResolucao(){
        return Database.resolucao;
    }
    public static void setResolucao(int resolucao){
         Database.resolucao = resolucao;
    }
    public static boolean getWaiter(){
        return Database.waiter;
    }
    public static void setWaiter(boolean newWaiter){
        Database.waiter= newWaiter;
    }
    
    
    
    
    
    
}
