import java.util.ArrayList;

public class Music extends ServiceCommunicator {
    public String name;
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    public Music(String name) {
        super("https://itunes.apple.com/search?term=" + name + "&limit=1");
        this.name = name;
        this.keys = keys;
        this.values = values;
    }
}
