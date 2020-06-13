import java.util.Random;
import java.util.Scanner;

public class Mastermind {

    public static void main(String[] args) {
        Game game = new Game();
        game.runGame();
    }
}

class CodeGenerator{
    char[] letters = {'a','b','c','d','e','f'};

    public String generateCode(){
        String code = "";
        Random random = new Random();

        for(int i = 0; i < 4; i++) {
            int index = random.nextInt(5);
            code += letters[index];
        }
        return code;
    }
}

class Game {
    boolean gameRuns = true;

    public void runGame(){
        CodeGenerator generator = new CodeGenerator();
        String code = generator.generateCode();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Mastermind!");
        System.out.println("A 4 letter code has been generated from the letters a, b, c, d, e or f.");
        System.out.println("Try to crack the code by inputting your 4 letter combinations!");
        System.out.println("The game will let you know about correct letters in correct or incorrect places.");

        while(gameRuns){
            System.out.println();
            System.out.println("Type your attempt to crack the 4 letter code:");
            String antwoord = sc.nextLine().toLowerCase();

            int correctGoedePositie = 0;
            int correctOnjuistePositie = 0;

            //if player input and generated code are the exact same, the game is won and ended
            if(code.equals(antwoord)){
                System.out.println();
                System.out.println("You've cracked the code! Congratulations!");
                System.out.println("Exiting the game, thanks for playing!");
                gameRuns = false;
            //if player input is 'q' the game is ended
            }else if(antwoord.equals("q")) {
                gameRuns = false;
                System.out.println("You've quit the game, the correct code was " + code);
                System.out.println("Exiting the game, thanks for playing!");

            //Using the method validInput, if returns false, the game asks for a new try.
            }else if(!validInput(antwoord)){
                System.out.println("Input invalid, please create a 4 letter code from the letters a, b, c, d, e or f.");

            //if all previous checks do not fire, we start checking and responding to player input accordingly
            }else {
                for (int i = 0; i < antwoord.length(); i++) {
                    //Looking if a letter in player's input is present in generated code
                    if (code.contains(String.valueOf(antwoord.charAt(i)))) {
                        //if letter is present, if it's in the right position, increment correctGoedePositie
                        if (code.charAt(i) == antwoord.charAt(i)) {
                            correctGoedePositie++;
                            //Check whenever a letter is at the right position to see if a previous (double) correct letter in a wrong position is now no longer valid
                            //if it the current correct positioned letter invalidates a previous letter, substract 1 from correctOnjuistePositie
                            if(charCount(antwoord, antwoord.charAt(i)) > charCount(code, antwoord.charAt(i)) && correctOnjuistePositie > 0){
                                correctOnjuistePositie--;
                            }

                        /*
                        Check if a letter has doubles in other positions of the player input
                        If yes, and there's an equal amount of that letter in the player input and in the generated code
                        it's a valid letter and we increment correctOnjuistePositie, if not it's invalid and we do nothing
                        */
                        }else if((i > 0 && antwoord.charAt(i) == antwoord.charAt(i-1)) ||
                                (i > 1 && antwoord.charAt(i) == antwoord.charAt(i-2)) ||
                                (i > 2 && antwoord.charAt(i) == antwoord.charAt(i-3))){

                            if(charCount(antwoord, antwoord.charAt(i)) == charCount(code, antwoord.charAt(i))){
                                correctOnjuistePositie++;
                            }
                       //if neither of the previous checks happen, the letter is valid and in a wrong spot
                       //we increment correctOnjuistePositie
                        }else {
                            correctOnjuistePositie++;
                        }
                    }
                }
                //printing the feedback
                System.out.println("Your guess: " + antwoord);
                System.out.println("Correcte letters op de juiste plek: " + correctGoedePositie);
                System.out.println("Correcte letters op een onjuiste plek: " + correctOnjuistePositie);
            }
        }
    }

    //method to check if the player input is valid using a regular expression
    public boolean validInput(String input){
        return input.matches("[a-f]{4}");
    }

    //method that counts the chars in a string, used to determine amount of doubles in codes
    public int charCount(String input, char charToCount){
        int count = 0;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == charToCount){
                count++;
            }
        }
        return count;
    }
}
