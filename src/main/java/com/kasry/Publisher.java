package com.kasry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hamzadev on 26/09/17.
 */
public class Publisher extends JedisPubSub{

    private static final String SERVER_ADDR = "localhost";

    Jedis jedis;
    private ArrayList<String> messageContainer = new ArrayList<String>();


    void publish(String channel, String message) {
        jedis.publish(channel, message);

        System.out.println("Message : " + message + " has been published to the channel : " + channel);
        jedis.quit();

    }

    /**
     * In this funciton we insert a book, in the database
     * and then we publish its title in the
     * @param book
     */
    void publishBook(Book book) {

        String[] FRENCH_STOP_WORDS = new String[]{"a", "afin", "ai", "ainsi", "après", "attendu", "au", "aujourd", "auquel", "aussi", "autre", "autres", "aux", "auxquelles", "auxquels", "avait", "avant", "avec", "avoir", "c", "car", "ce", "ceci", "cela", "celle", "celles", "celui", "cependant", "certain", "certaine", "certaines", "certains", "ces", "cet", "cette", "ceux", "chez", "ci", "combien", "comme", "comment", "concernant", "contre", "d", "dans", "de", "debout", "dedans", "dehors", "delà", "depuis", "derrière", "des", "désormais", "desquelles", "desquels", "dessous", "dessus", "devant", "devers", "devra", "divers", "diverse", "diverses", "doit", "donc", "dont", "du", "duquel", "durant", "dès", "elle", "elles", "en", "entre", "environ", "est", "et", "etc", "etre", "eu", "eux", "excepté", "hormis", "hors", "hélas", "hui", "il", "ils", "j", "je", "jusqu", "jusque", "l", "la", "laquelle", "le", "lequel", "les", "lesquelles", "lesquels", "leur", "leurs", "lorsque", "lui", "là", "ma", "mais", "malgré", "me", "merci", "mes", "mien", "mienne", "miennes", "miens", "moi", "moins", "mon", "moyennant", "même", "mêmes", "n", "ne", "ni", "non", "nos", "notre", "nous", "néanmoins", "nôtre", "nôtres", "on", "ont", "ou", "outre", "où", "par", "parmi", "partant", "pas", "passé", "pendant", "plein", "plus", "plusieurs", "pour", "pourquoi", "proche", "près", "puisque", "qu", "quand", "que", "quel", "quelle", "quelles", "quels", "qui", "quoi", "quoique", "revoici", "revoilà", "s", "sa", "sans", "sauf", "se", "selon", "seront", "ses", "si", "sien", "sienne", "siennes", "siens", "sinon", "soi", "soit", "son", "sont", "sous", "suivant", "sur", "ta", "te", "tes", "tien", "tienne", "tiennes", "tiens", "toi", "ton", "tous", "tout", "toute", "toutes", "tu", "un", "une", "va", "vers", "voici", "voilà", "vos", "votre", "vous", "vu", "vôtre", "vôtres", "y", "à", "ça", "ès", "été", "être", "ô"};
        String[] ENGLISH_STOP_WORDS = new String[]{"a", "an", "and", "are", "as", "at", "be", "but", "by",
                "for", "if", "in", "into", "is", "it",
                "no", "not", "of", "on", "or", "such",
                "that", "the", "their", "then", "there", "these",
                "they", "this", "to", "was", "will", "with"};
        jedis.hmset(book.getIsbn(), book.toMap());
        //Splitting the description using the regEx, through characters not words
        List<String> channels = Arrays.asList(book.getDescription().split("[^\\p{L}\\d]+"));
        channels.removeAll(Arrays.asList(FRENCH_STOP_WORDS));
        channels.removeAll(Arrays.asList(ENGLISH_STOP_WORDS));
        for (String channel :
                channels) {
            jedis.subscribe();
            jedis.publish(channel, "The book : " + book.getIsbn() + " has been created");
        }
        jedis.publish("books", "The book : " + book.getIsbn() + " has been created");
    }

    Publisher() {
        this.jedis = new Jedis(SERVER_ADDR);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The book to be send :");
        String message = scanner.nextLine();
        Publisher publisher = new Publisher();
        Book book = new Book("ISBN1", "Artificial Intelligence",
                "Andrew Ng", "An introduction to artificial intelligence");
        publisher.publishBook(book);
        publisher.publish("books", message);
    }
}
