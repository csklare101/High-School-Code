import java.util.*;
/**
 * The board class for Connect Four, terminal based
 *
 * @author Carson Sklare
 */
public class Board
{
    private int width;
    private int height;
    private char[][] board;
    private char currentPlayer;
    private int moves;

    public Board(int w, int h){
        width = w;
        height = h;
        board = new char[height][width];
        currentPlayer = 'R';
        moves = 0;
        this.makeBoard();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setBoard(int x, int y, char c){
        this.board[x][y] = c;
    }

    public char getSpace(int x, int y){
        return this.board[x][y];
    }

    public int getMoves(){
        return moves;
    }

    public void makeBoard(){
        for(int i = 0; i < this.getWidth(); i++){
            for(int x = 0; x < this.getHeight(); x++){
                this.setBoard(i,x,'.');
            }
        }
    }

    public char lastPlayer(){
        if(currentPlayer == 'R'){
            return 'Y';
        }
        else{
            return 'R';
        }
    }

    public char getPlayer(){
        return currentPlayer;
    }

    public void changePlayer(){
        if(currentPlayer == 'R'){
            currentPlayer = 'Y';
        }
        else{
            currentPlayer = 'R';
        }
    }

    public boolean dropDisk(int row){
        int i = 0; 
        for(i = 0; i < this.getHeight(); i++){
            if(board[i][row] == this.getPlayer() || board[i][row] == this.lastPlayer()){
                board[i-1][row] = this.getPlayer();
                break;
            }
        }
        if(i == this.getHeight()){
            board[i-1][row] = this.getPlayer();
        }
        moves++;
        return isConnected(i-1,row);
    }

    public int randomMove(){
        Random rand = new Random();
        return rand.nextInt(8);
    }

    public boolean canPlay(int col){
        return board[0][col] != this.getPlayer();
    }

    public boolean isConnected(int x, int y){
        int num=board[x][y];
        int count=0;
        int i=y;

        while(i<this.getWidth() && board[x][i] == num){
            count++;
            i++;
        }
        i=y-1;
        while(i>=0 && board[x][i] == num){
            count++;
            i--;
        }
        if(count == 4)
            return true;

        count=0;
        int j=x;
        while(j<this.getHeight() && board[j][y] == num){
            count++;
            j++;
        }
        if(count == 4)
            return true;

        count=0;
        i=x;
        j=y;
        while(i<this.getWidth() && j<this.getHeight() && board[i][j] == num){
            count++;
            i++;
            j++;
        }
        i=x-1;
        j=y-1;
        while(i>=0 && j>=0 && board[i][j] == num){
            count++;
            i--;
            j--;
        }
        if(count == 4)
            return true;

        count=0;
        i=x;
        j=y;
        while(i<this.getWidth() && j>=0 && board[i][j] == num){
            count++;
            i++;
            j--;
        }
        i=x-1;
        j=y+1;
        while(i>=0 && j<this.getHeight() && board[i][j] == num){
            count++;
            i--;
            j++;
        }
        if(count == 4)
            return true;

        return false;
    }

    public String toString(){
        String board = "";
        int r = 0;
        for(int i = 0; i < this.getWidth(); i++){
            for(int x = 0; x < this.getHeight(); x++){
                board += this.getSpace(i,x) + " ";
            }
            board += "\n";
        }
        for(int i = 0; i < this.getWidth(); i++){
            board += i + " ";
        }
        board += "\n";
        return board;
    }

    public boolean isFull(){
        return moves == this.getHeight() * this.getWidth();
    }

    public int getInt(){
        Scanner s = new Scanner(System.in);
        int r = -1;
        try{
            r = s.nextInt();
            return r;
        }
        catch(InputMismatchException ex){
            System.out.println("Not an int");
        }
        return -1;
    }


    public static void main(String[] args){
        Board b = new Board(8,8);
        Scanner s = new Scanner(System.in);
        boolean gamePlay = true;
        boolean singlePlayer = false;
        while(gamePlay){
            System.out.println("Welcome to Connect Four");

            System.out.println("\nWould you like to play single player against a random AI or multiplayer?");
            System.out.println("Type s to play single player, type m to play multiplayer, the default is multiplayer");
            String mode = s.nextLine();            
            if(mode.equalsIgnoreCase("s")){
                singlePlayer = true;
                System.out.println("SIGNLE PLAYER");
            }
            else{
                singlePlayer = false;
                System.out.println("MULTI PLAYER");
            }   

            System.out.println("\nWho wants to go first, red or yellow?\n(type r or y, default r goes first)");
            String first = s.nextLine();
            if(first.equalsIgnoreCase("y") && b.getPlayer() == 'R'){
                System.out.println("\nYellow goes first");
                b.changePlayer();
            }  
            else if(first.equalsIgnoreCase("y") && b.getPlayer() == 'Y'){
                System.out.println("\nYellow goes first");
            }
            else if(b.getPlayer() == 'Y'){
                System.out.println("\nRed goes first");
                b.changePlayer();
            }
            else{
                System.out.println("\nRed goes first");
            }
            b.makeBoard();
            outer:
            while(true){
                int column=0;
                while(true){
                    System.out.println("\n\nPlayer " + b.getPlayer() + ":");

                    column = b.getInt();

                    if(column >= 0 && column < 8 && b.canPlay(column)){//&& s.hasNextInt()){
                        if(b.dropDisk(column)){
                            System.out.println(b + "\n\nPlayer " + b.getPlayer() + " wins!");
                            break outer;
                        }
                        b.changePlayer();
                        break;
                    }
                    else
                        System.out.println("Sorry that moved cannot be played, try again.");
                }
                System.out.println(b);

                while(true){
                    System.out.println("\n\nPlayer " + b.getPlayer() + ":");
                    if(singlePlayer){
                        if(b.canPlay(b.randomMove())){
                            if(b.dropDisk(b.randomMove())){
                                System.out.println(b + "\n\nPlayer " + b.getPlayer() + " wins!");
                                break outer;
                            }
                            b.changePlayer();
                            System.out.println(b);
                            break;
                        }
                        else{
                            System.out.println("Sorry that moved cannot be played, try again.");
                        }
                    }
                    else{
                        column = b.getInt();
                        if(column >= 0 && column < 8 && b.canPlay(column)){//&& s.hasNextInt()){
                            if(b.dropDisk(column)){
                                System.out.println(b + "\n\nPlayer " + b.getPlayer() + " wins!");
                                break outer;
                            }
                            b.changePlayer();
                            break;
                        }
                        else
                            System.out.println("Sorry that moved cannot be played, try again.");
                    }
                } 
                System.out.println(b);
                if(b.isFull()){
                    System.out.print("Game draw");
                    break;
                }
            }

            System.out.println("\n\nWould you like to play again? y/n");
            String choice = s.nextLine();
            if(choice.equalsIgnoreCase("y")){
                for(int i = 0; i < 1000; i++) {
                    System.out.println();
                }
            }
            else{
                System.out.println("Bye");
                gamePlay = false;
                break;
            }
        } 
    }
}
