import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import sac.game.*;

public class Connect4 extends GameStateImpl{
    public static int rows = 6;
    public static int cols = 7;
    public byte[][] connectBoard;

    // stałe
    public byte playerX = 1; //X -> 1
    public byte playerO = 2; //O -> 2
    public byte empty = 0; //empty -> 0
    public byte whoPlays = 0;

    // Konstruktory
    public Connect4() {
        connectBoard =new byte[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                connectBoard[i][j] = empty;
            }
        }
    }

    public Connect4(Connect4 parent) {
        connectBoard = new byte[rows][cols];
        whoPlays = parent.whoPlays;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                connectBoard[i][j] = parent.connectBoard[i][j];
            }
        }
        setMaximizingTurnNow(parent.isMaximizingTurnNow());
    }

    public int GameOver(){

        // Sprawdzamy po wierszach
        for (int i = 0; i < rows; i++) {        //wiersze
            int playerXsum = 0;
            int playerOsum = 0;
            for (int j = 0; j < cols; j++) {     //kolumny w wierszu
                switch (connectBoard[i][j]){
                    case 0 -> {playerXsum = 0; playerOsum = 0;}
                    case 1 -> {playerXsum++; playerOsum = 0;}
                    case 2 -> {playerXsum = 0; playerOsum++;}
                }

                // Wygrywa gracz "X"
                if (playerXsum >= 4) {
                    return 1;
                }
                // Wygrywa gracz "Y"
                if (playerOsum >= 4) {
                    return 2;
                }
            }
        }

        // Sprawdzamy po kolumnach
        for (int j = 0; j < cols; j++) {        //wiersze
            int playerXsum = 0;
            int playerOsum = 0;
            for (int i = 0; i < rows; i++) {     //kolumny w wierszu
                switch (connectBoard[i][j]){
                    case 0 -> {playerXsum = 0; playerOsum = 0;}
                    case 1 -> {playerXsum++; playerOsum = 0;}
                    case 2 -> {playerXsum = 0; playerOsum++;}
                }

                // Wygrywa gracz "X"
                if (playerXsum >= 4) {
                    return 1;
                }
                // Wygrywa gracz "Y"
                if (playerOsum >= 4) {
                    return 2;
                }
            }
        }

        // Sprawdzamy skosy od gory
        int slants = rows + cols -1;     //linie skosne gora
        for (int i=0; i<slants;i++){
            int x = i;
            int y = 0;
            int playerXsum = 0;
            int playerOsum = 0;
            while(x >= 0){
                if ( x > rows-1 || y > cols-1){
                    x--;
                    y++;
                    continue;

                }
                switch (connectBoard[x][y]){
                    case 0 -> {playerXsum = 0; playerOsum = 0;}
                    case 1 -> {playerXsum++; playerOsum = 0;}
                    case 2 -> {playerXsum = 0; playerOsum++;}
                }

                if (playerXsum >= 4)
                    return 1;
                if (playerOsum >= 4)
                    return 2;

                x--;
                y++;
            }
        }

        for (int i=0; i<slants;i++) {
            int x = rows - 1 - i;
            int y = 0;
            int playerXsum = 0;
            int playerOsum = 0;
            while (x < rows) {
                if (x < 0 || y > cols - 1) {
                    x++;
                    y++;
                    continue;

                }
                switch (connectBoard[x][y]){
                    case 0 -> {playerXsum = 0; playerOsum = 0;}
                    case 1 -> {playerXsum++; playerOsum = 0;}
                    case 2 -> {playerXsum = 0; playerOsum++;}
                }

                if (playerXsum >= 4)
                    return 1;
                if (playerOsum >= 4)
                    return 2;
                //
                //
                x++;
                y++;
            }
        }

        // Czy dotyka sufitu
        for (int j = 0; j < cols; j++) {     //kolumny w wierszu
            int playerXsum = 0;
            int playerOsum = 0;
            // jesli pusto nikt nie dostaje "punktu"
            switch (connectBoard[0][j]){
                case 0 -> {playerXsum = 0; playerOsum = 0;}
                case 1 -> {playerXsum = 4; playerOsum = 0;}
                case 2 -> {playerXsum = 0; playerOsum = 4;}
            }

            if(playerXsum >= 4){
                return 1;
            }

            if(playerOsum >= 4){
                return 2;
            }
        }

        return 0;
    }

    public boolean makeMove(boolean player, int move) {
        // Sprawdzamy kto gra
        byte playerWhoMoves;

        if (player){
            playerWhoMoves = playerX;
        } else {
            playerWhoMoves = playerO;
        }
        // Talbica zaczyna sie od zera
        move--;

        // Sprawdzamy czy wybrana kolumna jest mozliwa
        if(move < 0 || move >= cols) {
            return false;
        } else {
            for(int i = rows-1; i >=0; i--) {
                //sprawdzamy czy miejsce jest puste
                if (connectBoard[i][move] == 0) {
                    connectBoard[i][move] = playerWhoMoves;
                    break;
                }
            }
            setMaximizingTurnNow(!isMaximizingTurnNow());
            return true;
        }
    }

    public boolean isSolution(){
        int player;
        if(isMaximizingTurnNow()){
            player = 1;
        } else {
            player = 2;
        }
        // Sprawdzanie pion
        for (int i = 0; i < rows - 3; i++) {        //wiersze
            for (int j = 0; j < cols; j++) {     //kolumny w wierszu
                if(connectBoard[i][j] == player && connectBoard[i+1][j] == player && connectBoard[i+2][j] == player && connectBoard[i+3][j] == player){
                    return true;
                }
            }
        }

        // Sprawdzanie poziom
        for (int j = 0; j < cols - 3; j++) {        //wiersze
            for (int i = 0; i < rows; i++) {     //kolumny w wierszu
                if(connectBoard[i][j] == player && connectBoard[i][j+1] == player && connectBoard[i][j + 2] == player && connectBoard[i][j + 3] == player){
                    return true;
                }
            }
        }

        // sprawdzmy skosy w dol
        for (int i=3; i<rows; i++){
            for (int j=3; j<cols; j++){
                if (connectBoard[i][j] == player && connectBoard[i-1][j-1] == player && connectBoard[i-2][j-2] == player && connectBoard[i-3][j-3] == player){
                    return true;
                }
            }
        }

        // Sprawdzanie skosy w gore
        for (int i=3; i<rows; i++){
            for (int j=0; j<cols-3; j++){
                if (connectBoard[i][j] == player && connectBoard[i-1][j+1] == player && connectBoard[i-2][j+2] == player && connectBoard[i-3][j+3] == player){
                    return true;
                }
            }
        }

        // Czy dotyka sufitu
        for (int j = 0; j < cols; j++) {     //kolumny w wierszu
            if(connectBoard[0][j] == player && connectBoard[0][j+1] == player && connectBoard[0][j + 2] == player && connectBoard[0][j + 3] == player){
                return true;
            }
        }

        return false;
    }

    public int myRating(){
        int playerX = 0;
        int playerO = 0;
        int playerXsum = 0;
        int playerOsum = 0;
        int result = 0;

        if(isMaximizingTurnNow()){
            playerX = 1;
        } else {
            playerO = 2;
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols-3; j++){
                if(connectBoard[i][j] == playerO && connectBoard[i][j+1] == playerO && connectBoard[i][j+2] == playerO && connectBoard[i][j+3] == playerO){
                    playerOsum++;
                }else if(connectBoard[i][j] == playerX && connectBoard[i][j+1] == playerX && connectBoard[i][j+2] == playerX && connectBoard[i][j+3] == playerX){
                    playerXsum++;
                }
                result = (playerXsum - playerOsum) * 3; // Suma ciągów X - Suma ciągów O * waga
            }
        }

        for (int j = 0; j < cols - 3; j++) {        //wiersze
            for (int i = 0; i < rows; i++) {     //kolumny w wierszu
                if(connectBoard[i][j] == playerO && connectBoard[i][j+1] == playerO && connectBoard[i][j+2] == playerO && connectBoard[i][j+3] == playerO){
                    playerOsum++;
                }else if(connectBoard[i][j] == playerX && connectBoard[i][j+1] == playerX && connectBoard[i][j+2] == playerX && connectBoard[i][j+3] == playerX){
                    playerXsum++;
                }
                result = (playerXsum - playerOsum) * 3;
            }
        }

        for (int i=3; i<rows; i++){
            for (int j=3; j<cols; j++){
                if (connectBoard[i][j] == playerO && connectBoard[i-1][j-1] == playerO && connectBoard[i-2][j-2] == playerO && connectBoard[i-3][j-3] == playerO){
                    playerOsum++;
                }else if(connectBoard[i][j] == playerX && connectBoard[i-1][j-1] == playerX && connectBoard[i-2][j-2] == playerX && connectBoard[i-3][j-3] == playerX){
                    playerXsum++;
                }
                result = (playerXsum - playerOsum) * 2;
            }
        }

        for (int i=3; i<rows; i++){
            for (int j=0; j<cols-3; j++){
                if (connectBoard[i][j] == playerO && connectBoard[i-1][j+1] == playerO && connectBoard[i-2][j+2] == playerO && connectBoard[i-3][j+3] == playerO){
                    playerOsum++;
                } else if (connectBoard[i][j] == playerX && connectBoard[i-1][j+1] == playerX && connectBoard[i-2][j+2] == playerX && connectBoard[i-3][j+3] == playerX){
                    playerXsum++;
                }
                result = (playerXsum - playerOsum) * 2;
            }
        }

        return result;
    }

    public static void main(String[] argv) {
        boolean startingPlayer = true;
        Connect4 game = new Connect4(); //1
        System.out.println(game); //2

        game.setMaximizingTurnNow(startingPlayer);


         while (game.GameOver() == 0){
            //3

            if(game.isMaximizingTurnNow()){
                Scanner myInput = new Scanner(System.in);
                System.out.println("Make move");

                int myMove = myInput.nextInt();
                game.makeMove(startingPlayer, myMove); // 4
                System.out.println(game); //5
            }else {
                Connect4.setHFunction(new ratingFunc());
                GameSearchAlgorithm algo = new AlphaBetaPruning(game); // 6
                algo.execute(); // 7
                System.out.println(algo.getMovesScores()); // 8
                int aiMove;
                aiMove =Integer.parseInt(algo.getFirstBestMove());

                game.makeMove(!startingPlayer, aiMove);
                System.out.println(game);
            }
        }

        if(!game.isMaximizingTurnNow()){
            System.out.println("Wygral Player X");
        } else {
            System.out.println("Wygrala Player O");
        }
    }

    @Override
    public String toString() {
        String txt = "";
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                txt += connectBoard[i][j] + "|";
            }
            txt += "\n";
        }

        return txt;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public List<GameState> generateChildren() {
        List<GameState> children = new LinkedList<GameState>();

        for(int i = 0; i < cols; i++){
            Connect4 child = new Connect4(this);

            // Sprawdzamy czy dziecko moze wykonac ruch jesli tak to go wykonujemy
            if(child.makeMove(isMaximizingTurnNow(), i)){
                // Ruch zostaje wykonany zmiana gracza na przeciwnego
                child.setMaximizingTurnNow(!isMaximizingTurnNow());

                // Nazwa ruchu inaczej wybrana kolumna
                child.setMoveName(Integer.toString(i));

                //Dodanie ruchu do listy ruchów które mogą zostać wykonane
                children.add(child);
            }
        }
        return children;
    }


}

//    public int myRating(){
//        // Test
//        int result = 0;
//        for(int i = 0; i < rows; i++){
//            for(int j = 0; j < cols-3; j++){
//                if(connectBoard[i][j] != 2 && connectBoard[i][j+1] != 2 && connectBoard[i][j+2] != 2 && connectBoard[i][j+3] != 2){
//                    result++;
//                }
//                if(connectBoard[i][j] != 1 && connectBoard[i][j+1] != 1 && connectBoard[i][j+2] != 1 && connectBoard[i][j+3] != 1){
//                    result--;
//                }
//            }
//        }
//
//        for(int j = 0; j < cols; j++){
//            for(int i = 0; i < rows-3; i++){
//                if(connectBoard[i][j] != 2 && connectBoard[i+1][j] != 2 && connectBoard[i+2][j] != 2 && connectBoard[i+3][j] != 2){
//                    result++;
//                }
//                if(connectBoard[i][j] != 1 && connectBoard[i+1][j] != 1 && connectBoard[i+2][j] != 1 && connectBoard[i+3][j] != 1){
//                    result--;
//                }
//            }
//        }
//
//
//        for(int k = 0; k <= rows + cols - 2; k++){
//            for(int j = 0; j <= k; j++){
//                int i = k - j;
//                int counter = 0;
//                if(i < rows && j < cols){
//                    counter++;
//                    if(counter>=4){
//                        if(connectBoard[i][j] != 2 && connectBoard[i-1][j+1] != 2 && connectBoard[i-2][j+2] != 2 && connectBoard[i-3][j+3] != 2){
//                            result++;
//                        }
//                        if(connectBoard[i][j] != 1 && connectBoard[i-1][j+1] != 1 && connectBoard[i-2][j+2] != 1 && connectBoard[i-3][j+3] != 1){
//                            result--;
//                        }
//                    }
//                }
//            }
//        }
//        return result;
//    }

//    public int GameOver(){
//
//        // Sprawdzamy po wierszach
//        for (int i = 0; i < rows; i++) {        //wiersze
//            int playerXsum = 0;
//            int playerOsum = 0;
//            for (int j = 0; j < cols; j++) {     //kolumny w wierszu
//                switch (connectBoard[i][j]){
//                    case 0 -> {playerXsum = 0; playerOsum = 0;}
//                    case 1 -> {playerXsum++; playerOsum = 0;}
//                    case 2 -> {playerXsum = 0; playerOsum++;}
//                }
//
//                // Wygrywa gracz "X"
//                if (playerXsum >= 4) {
//                    return 1;
//                }
//                // Wygrywa gracz "Y"
//                if (playerOsum >= 4) {
//                    return 2;
//                }
//            }
//        }
//
//        // Sprawdzamy po kolumnach
//        for (int j = 0; j < cols; j++) {        //wiersze
//            int playerXsum = 0;
//            int playerOsum = 0;
//            for (int i = 0; i < rows; i++) {     //kolumny w wierszu
//                switch (connectBoard[i][j]){
//                    case 0 -> {playerXsum = 0; playerOsum = 0;}
//                    case 1 -> {playerXsum++; playerOsum = 0;}
//                    case 2 -> {playerXsum = 0; playerOsum++;}
//                }
//
//                // Wygrywa gracz "X"
//                if (playerXsum >= 4) {
//                    return 1;
//                }
//                // Wygrywa gracz "Y"
//                if (playerOsum >= 4) {
//                    return 2;
//                }
//            }
//        }
//
//        // Sprawdzamy skosy od gory
//        int slants = rows + cols -1;     //linie skosne gora
//        for (int i=0; i<slants;i++){
//            int x = i;
//            int y = 0;
//            int playerXsum = 0;
//            int playerOsum = 0;
//            while(x >= 0){
//                if ( x > rows-1 || y > cols-1){
//                    x--;
//                    y++;
//                    continue;
//
//                }
//                switch (connectBoard[x][y]){
//                    case 0 -> {playerXsum = 0; playerOsum = 0;}
//                    case 1 -> {playerXsum++; playerOsum = 0;}
//                    case 2 -> {playerXsum = 0; playerOsum++;}
//                }
//
//                if (playerXsum >= 4)
//                    return 1;
//                if (playerOsum >= 4)
//                    return 2;
//
//                x--;
//                y++;
//            }
//        }
//
//        for (int i=0; i<slants;i++) {
//            int x = rows - 1 - i;
//            int y = 0;
//            int playerXsum = 0;
//            int playerOsum = 0;
//            while (x < rows) {
//                if (x < 0 || y > cols - 1) {
//                    x++;
//                    y++;
//                    continue;
//
//                }
//                switch (connectBoard[x][y]){
//                    case 0 -> {playerXsum = 0; playerOsum = 0;}
//                    case 1 -> {playerXsum++; playerOsum = 0;}
//                    case 2 -> {playerXsum = 0; playerOsum++;}
//                }
//
//                if (playerXsum >= 4)
//                    return 1;
//                if (playerOsum >= 4)
//                    return 2;
//                //
//                //
//                x++;
//                y++;
//            }
//        }
//
//        // Czy dotyka sufitu
//        for (int j = 0; j < cols; j++) {     //kolumny w wierszu
//            int playerXsum = 0;
//            int playerOsum = 0;
//            // jesli pusto nikt nie dostaje "punktu"
//            switch (connectBoard[0][j]){
//                case 0 -> {playerXsum = 0; playerOsum = 0;}
//                case 1 -> {playerXsum = 4; playerOsum = 0;}
//                case 2 -> {playerXsum = 0; playerOsum = 4;}
//            }
//
//            if(playerXsum >= 4){
//                return 1;
//            }
//
//            if(playerOsum >= 4){
//                return 2;
//            }
//        }
//
//        return 0;
//    }
