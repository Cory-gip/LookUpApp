import java.util.ArrayList;

public class Pokemon extends ServiceCommunicator {
    public String name;
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    public Pokemon(String name) {
        super("https://pokeapi.co/api/v2/pokemon/" + name);
        this.name = name;
        this.keys = keys;
        this.values = values;
    }
}
