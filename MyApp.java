import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class MyApp {
    private static Scanner userinput = new Scanner(System.in);
    private static String cmd;


    static void run() {
        cmd = selectSearchType();
        search();
    }

    private static String selectSearchType() {
        System.out.println("\nPlease type in whether you are searching for music, television, a wikipedia page, a pokemon, or a zip code. \nEnter 'help' for more information, or 'quit' to exit the program.");
        System.out.print("Command> \n");
        String cmd = userinput.next();
        System.out.println("You selected " + cmd + ".\n");
        return cmd;
    }

    private static void search() {
        cmd = cmd.toLowerCase();
        if (cmd.equals("music")) {
            songSearch();
            askForContinue();
            checkForContinue();
        } else if (cmd.equals("television") || cmd.equals("tv")) {
            showSearch();
            askForContinue();
            checkForContinue();
        } else if (cmd.equals("zip") || cmd.equals("zipcode") || cmd.equals("zip code")) {
            zipSearch();
            askForContinue();
            checkForContinue();
        } else if (cmd.equals("wiki") || cmd.equals("wikipedia")) {
            wikiSearch();
            askForContinue();
            checkForContinue();
        } else if (cmd.equals("pokemon") || cmd.equals("poke")) {
            pokeSearch();
            askForContinue();
            checkForContinue();
        } else if (cmd.equals("help")) {
            printHelp();
        } else if (cmd.equals("quit")) {
            quit();
        } else {
            System.out.println(cmd + " is not a valid response. Type 'help' to see all valid responses.");
        }
    }

    private static void songSearch() {
        System.out.println("Please enter the name of the song or artist you would like information on:");
        String input = userinput.next();
        checkForQuit(input);
        Music song = new Music(input);
        System.out.println("Searching for " + input + "...");
        song.get();
        song.keys = song.storeKeys(song.json);
        System.out.println(song.keys);
        song.values = song.storeValues(song.json);
        System.out.println(song.values);
        song.json = song.createFullKeySet(song.keys, song.values, song.json);
        System.out.println(song.json);
        checkForKeySearch(input, song);
        //System.out.println(input);
        //song.printByKey(userinput, input, song.json);
        //System.out.println("Here is the information obtained on " + input + ":\n");
        //song.printJsonObject(song.json);
    }

    private static void showSearch() {
        System.out.println("Please enter the name of the television show you would like information on:");
        String input = userinput.next();
        checkForQuit(input);
        Television show = new Television(input);
        System.out.println("Searching for " + input + "...");
        show.get();
//        show.keys = show.storeKeys(show.json);
//        System.out.println(show.keys);
//        show.values = show.storeValues(show.json);
//        System.out.println(show.values);
//        show.json = show.createFullKeySet(show.keys, show.values, show.json);
//        System.out.println(show.json);
        checkForKeySearch(input, show);
        //System.out.println(input);
        //show.printByKey(userinput, input, show.json);
        //System.out.println("Here is the information obtained on " + input + ":\n");
        //show.printJsonObject(show.json);
    }

    private static void zipSearch() {
        System.out.println("Please enter the zip code you would like information on:");
        String input = userinput.next();
        checkForQuit(input);
        ZipCode zip = new ZipCode(input);
        System.out.println("Searching for " + input + "...");
        zip.get();
        checkForKeySearch(input, zip);
//        zip.keys = zip.storeKeys(zip.json);
//        System.out.println(zip.keys);
//        zip.values = zip.storeValues(zip.json);
//        System.out.println(zip.values);
//        zip.json = zip.createFullKeySet(zip.keys, zip.values, zip.json);
//        System.out.println(zip.json);
        //System.out.println(zip.json.keySet());
        //askForKeySearch(input);

        //System.out.println(input);
        //zip.printByKey(userinput, input, zip.json);
        //zip.clearKeys(zip.keys);
            //System.out.println("Here is the information obtained on " + input + ":\n");
           // zip.printJsonObject(zip.json);
            //zip.storeKeys(zip.json);
            //printKeys(zip.keys);
        }


    private static void wikiSearch() {
        System.out.println("Please enter the search term that you would like information on:");
        String input = userinput.next();
        checkForQuit(input);
        Wiki wiki = new Wiki(input);
        System.out.println("Searching for " + input + "...");
        wiki.get();
        checkForKeySearch(input, wiki);
//        System.out.println("Here is the information obtained on " + input + ":\n");
//        wiki.printJsonObject(wiki.json);
    }


    private static void pokeSearch() {
        System.out.println("Please enter the pokemon that you would like information on:");
        String input = userinput.next();
        checkForQuit(input);
        Pokemon pokemon = new Pokemon(input);
        System.out.println("Searching for " + input + "...");
        pokemon.get();
        checkForKeySearch(input, pokemon);
//        System.out.println("Here is the information obtained on " + input + ":\n");
//        pokemon.printJsonObject(pokemon.json);
    }


    private static void printHelp() {
        System.out.println("These are the valid commands which can be given, all of which are case insensitive:\n");
        System.out.println("--If you wish to search for a zip code, 'zip', 'zipcode', and 'zip code' are all valid inputs.");
        System.out.println("--If you wish to search for either a song or an artist, 'music' is the only valid input.");
        System.out.println("--If you wish to search for a tv show, 'tv' and 'television' are each valid inputs.");
        System.out.println("--If you wish to search for a Wikipedia article, 'wiki' and 'wikipedia' are each valid inputs.");
        System.out.println("--If you wish to search for a Pokemon, 'poke' and 'pokemon' are each valid inputs.\n");
        System.out.println("Once you have selected what type of search you would like, you will be prompted to enter the name of your search. " +
                "\nIf your search is made up of multiple words, all words in your search must be joined by hyphens \n(michael-jackson, not michael jackson) " +
                "or else only the first word of your search will be read.");
        System.out.println("If you would like to quit the program, simply enter the term 'quit' at any time and the program will exit.\n");
    }

    private static void checkForContinue() {
        String input = userinput.next();
        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("quit")) {
            quit();
        } else if (input.equalsIgnoreCase("y")) {
            return;
        } else {
            System.out.println("Please enter y or n: ");
            checkForContinue();
        }
    }

    private static void askForContinue() {
        System.out.println("\nWould you like to search for anything else? (y/n)");
    }

    private static void checkForQuit(String input) {
        if (input.equalsIgnoreCase("quit")) {
            quit();
        } else {
            return;
        }
    }

    private static void quit() {
        System.out.println("Thank you for using this program!");
        System.exit(1);
    }

    private static void checkForKeySearch(String input, ServiceCommunicator sc) {
        System.out.println("\nSearch found. Would you like a specific piece of info on " + input + "? (y/n)");
        String keyinput = userinput.next();
        checkForQuit(keyinput);
        while(!(keyinput.equals("y") || keyinput.equals("n"))) {
                System.out.println("Please enter y or n: ");
                keyinput = userinput.next();
            }
        if (keyinput.equalsIgnoreCase("n")) {
            sc.printJsonObject(sc.json);
        } else if (keyinput.equalsIgnoreCase("y")) {
            sc.keys = sc.storeKeys(sc.json);
            System.out.println(sc.keys);
            sc.values = sc.storeValues(sc.json);
            System.out.println(sc.values);
            sc.keyjson = sc.createFullKeySet(sc.keys, sc.values, sc.keyjson);
            System.out.println(sc.keyjson);
            sc.printByKey(userinput, input, sc.json, sc.keyjson);
            //System.out.println("Please enter which key you would like to search for:");
        }
            input = keyinput;
            //return sc.json;
        }
}
