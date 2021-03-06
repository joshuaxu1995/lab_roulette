package roulette;

import util.ConsoleReader;


/**
 * Plays a game of roulette.
 * 
 * @author Robert C. Duvall
 */
public class Game {
    // name of the game
    private static final String DEFAULT_NAME = "Roulette";
    private Factory factory;
    // add new bet subclasses here
//    private Bet[] myPossibleBets = {
//        new RedBlack("Red or Black", 1),
//        new OddEven("Odd or Even", 1),
//        new ThreeConsecutive("Three in a Row", 11),
//    };
    private Wheel myWheel;

    /**
     * Construct the game.
     */
    public Game () {
        myWheel = new Wheel();
        factory = new Factory();
    }

    /**
     * @return name of the game
     */
    public String getName () {
        return DEFAULT_NAME;
    }

    /**
     * Play a round of roulette.
     *
     * Prompt player to make a bet, then spin the roulette wheel, and then verify 
     * that the bet is won or lost.
     *
     * @param player one that wants to play a round of the game
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public void play (Gambler player) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        int amount = ConsoleReader.promptRange("How much do you want to bet",
                                               0, player.getBankroll());
        
        String response = ConsoleReader.promptString("Please pick your bet");
        
        Bet b = getNewBet(response);
        String betChoice = b.place();

        System.out.print("Spinning ...");
        myWheel.spin();
        System.out.println(String.format("Dropped into %s %d", myWheel.getColor(), myWheel.getNumber()));
        if (b.isMade(betChoice, myWheel)) {
            System.out.println("*** Congratulations :) You win ***");
            amount *= b.getOdds();
        }
        else {
            System.out.println("*** Sorry :( You lose ***");
            amount *= -1;
        }
        player.updateBankroll(amount);
    }

    /**
     * Prompt the user to make a bet from a menu of choices.
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    	public Bet getNewBet(String description) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    	{
    		return factory.createReflection(description);
    	}
    
}
