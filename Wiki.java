import java.util.ArrayList;

public class Wiki extends ServiceCommunicator{
    public String term;
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    public Wiki(String term) {
        super("https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + term + "&format=json");
        this.term = term;
        this.keys = keys;
        this.values = values;
    }
}
