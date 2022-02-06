import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Hangman game
 * @author James Mills
 * @version uses Java 11
 */
public class Hangman {

    public static Scanner kb = new Scanner(System.in);
    private String chosenWord;
    private int[] chosenWordIndices;
    private int guess;
    private int lettersLeft;
    private String choose;
    private String[] placeholder;
    private String guesses;
    private final String playerName;
    private static int gamesWon;
    private static int gamesLost;

    public Hangman(String playerName){
        randomWord();
        this.playerName = playerName;
        this.choose = "";
    }

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    /**
     * Plays hangman game
     * @param args command line args
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the game of hangman.\nGuess the word.\nChoose new word at anytime and" +
                " it will be considered a loss.\nGood luck!!\n");
        String localGuess;
        System.out.println("What is your name?");  // gets name of player
        String name = kb.nextLine();
        boolean keepPlaying = true;
        while(keepPlaying){  // checks if keep playing
            Hangman h1 = new Hangman(name);  // creates new game
            boolean currentGame = true;
            while (currentGame){  // checks if current game is still running
                System.out.println("\n" + h1.playGame());
                localGuess = kb.nextLine();
                boolean bln = h1.checkGuess(localGuess);
                if(!bln){
                    System.out.println("Do you want to play another game?\nEnter Y or N: ");
                    char answer = kb.nextLine().toLowerCase(Locale.ROOT).charAt(0);
                    if(answer == 'n'){
                        currentGame = false;
                        keepPlaying = false;
                    }
                    else
                        currentGame = false;
                }
                if(h1.lettersLeft == 0){
                    gamesWon++;
                    System.out.println("You got it right!!!\nThe word was: "+h1.chosenWord);
                    System.out.println("Do you want to play another game?\nEnter Y or N: ");
                    char answer = kb.nextLine().toLowerCase(Locale.ROOT).charAt(0);
                    if(answer == 'n'){
                        currentGame = false;
                        keepPlaying = false;
                    }
                    else {
                        currentGame = false;
                    }
                }
            }
        }
        System.out.println("\nThanks for playing: "+ name);
        System.out.println("Your score was: Games Won: "+gamesWon+" \nGames Lost: "+gamesLost);
    }

    /**
     * Picks random word from words array
     */
    private void randomWord(){

        int choice = (int) Math.ceil(Math.random() * words.length);
        this.chosenWord = words[choice];
        this.chosenWordIndices = new int[this.chosenWord.length()];
        this.guess = 0;
        this.placeholder = new String[this.chosenWord.length()];
        this.guesses = "";
        this.lettersLeft = this.chosenWord.length();
        for(int i = 0; i< this.chosenWord.length(); i++){
            this.placeholder[i] = " _ ";
            this.chosenWordIndices[i] = -1;
        }
    }

    /**
     * Checks if entry is part of current word
     * @param guess entry
     * @return If guess is part of word or already has been guessed returns true, else returns false.
     */
    private boolean checkGuess(String guess){
        return (this.chosenWord.contains(guess.toLowerCase(Locale.ROOT)))
                ? (this.updateGuesses(guess, true))
                : (this.updateGuesses(guess, false));
    }

    /**
     * Updates hangman if needed and reprints diagram
     * @param letter user guess
     * @param correct if guess is part of word
     * @return returns false only if game is over
     */
    private boolean updateGuesses(String letter, boolean correct){
        if( correct){
            this.choose = letter;
            int location = this.chosenWord.indexOf(letter);
            this.placeholder[location] = letter;
            if(this.chosenWordIndices[location] == -1){
                this.chosenWordIndices[location] = 0;
                this.lettersLeft--;
            }
            else{
                System.out.println("You already guessed that letter!!\n");
                System.out.println("Press enter to continue .....");
                kb.nextLine();
            }
            return true;
        }
        else {
            this.choose = letter;
            if(this.guesses.contains(letter)){
                System.out.println("You already guessed that letter!!\n");
                System.out.println("Press enter to continue .....");
                kb.nextLine();
            }
            else {
                this.guesses += " "+letter;
                return this.updatePlaceholder();
            }
            return true;
        }
    }

    /**
     * Updates diagram
     * @return returns false only if game is over
     */
    private boolean updatePlaceholder(){
        if(this.guess < 5){
            this.guess++;
            if(this.guess == 5){
                System.out.println("Last Guess!!!! ");
                System.out.println("Press enter to continue .....");
                kb.nextLine();
            }
            return true;
        }
        else{
            System.out.println("\n\n The word was: "+this.chosenWord);
            System.out.println("Press enter to continue .....");
            kb.nextLine();
            gamesLost++;
            return false;
        }
    }

    /**
     * Displays current game info
     * @return Current game info
     */
    private String playGame(){
        return  "Games won: "
                + gamesWon
                + "\nGames lost: "
                + gamesLost
                + "\n\nPlayer Name: "
                + this.playerName
                + "\nLast Guess: "
                + this.choose
                + "\n\n"
                + gallows[guess]
                + "\nWord: "
                + Arrays.toString(this.placeholder)
                + "\nMisses:\t"
                + this.guesses
                + "\n\nGuess: ";
    }

}





