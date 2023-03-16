package board;

import cards.Cards;
import cities.City;
import players.Players;
import players.humanPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {

    private final String FILENAME = "cities.csv";
    private final File PATH = new File(new File(FILENAME).getAbsolutePath());
    private final HashMap<String, City> CITIES;

    private final Players[] PLAYERS;

    private ArrayList<Cards> playersDeck;
    private ArrayList<Cards> epidemicsDeck;
    private ArrayList<Cards> playersCardsOut;
    private ArrayList<Cards> epidemicsCardsOut;

    // Constructor
    public Board() throws FileNotFoundException {
        this.CITIES = loadCities();
        loadAdjacentCities(CITIES);
        this.PLAYERS = initialisePlayers();
    }

    /*
    Loading cities into hashmap
    Reads through the CSV file (structure: city name, city color, adjacent cities)
    Takes only city name and city color to create City object for every city
    These objects are saved into hashmap, with keys being names of the cities
    */
    private HashMap<String, City> loadCities() throws FileNotFoundException {
        HashMap<String, City> cities = new HashMap<>();
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] reading = scan.nextLine().split(",");
            String cityName = reading[0];
            String cityColor = reading[1];
            City cityObject = new City(cityName, cityColor);
            cities.put(cityName, cityObject);
        }
        return cities;
    }

    /*
    Loading adjacent cities from hashmap and csv file
    CSV structure - city name, city color, adjacent cities
    Takes hashmap of cities as parameter. Takes name of the adjacent city (key), looks it up
    in the hashmap and adds City object associated with the key into array. Once all adjacent
    cities for one particular city are added, the array sent into City object setter of adjacent
    cities. Every city is associated with another City objects and these object are created only
    once.
    */
    private void loadAdjacentCities(HashMap<String, City> loadedCities) throws FileNotFoundException {
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] reading = scan.nextLine().split(",");
            int NUM_ADJACENT_CITIES = reading.length - 2;
            City[] adjacentCities = new City[NUM_ADJACENT_CITIES];
            int indexInNewArray = 0;
            String cityName = reading[0];
            for (int adjacentCity = 2; adjacentCity < reading.length; adjacentCity++) {
                adjacentCities[indexInNewArray] = loadedCities.get(reading[adjacentCity]);
                indexInNewArray += 1;
            }
            loadedCities.get(cityName).setAdjacentCities(adjacentCities);
        }
    }

    /*
    Initialise players on the board. All players are initially in Atlanta.
    */
    private Players[] initialisePlayers() {
        String INITIAL_CITY = "Atlanta";
        humanPlayer human = new humanPlayer("Red", "Dispatcher");
        humanPlayer anotherHuman = new humanPlayer("Blue", "Medic");
        Players[] players = {human, anotherHuman};
        for (Players player : players) {
            player.setCurrentCity(this.CITIES.get(INITIAL_CITY));
        }
        return players;
    }

    // Here I will make method to create the card deck
    private ArrayList<Cards> initialiseCardDeck() {
        ArrayList<Cards> cards = new ArrayList<>();
        return cards;
    }

    public HashMap<String, City> getCITIES() {
        return CITIES;
    }

    public Players[] getPLAYERS() {
        return PLAYERS;
    }
}
