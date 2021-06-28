import java.util.ArrayList;

public class Television extends ServiceCommunicator {
    public String name;
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    public Television(String name) {
        super("http://api.tvmaze.com/singlesearch/shows?q=" + name);
        this.name = name;
        this.keys = keys;
        this.values = values;
    }
}
