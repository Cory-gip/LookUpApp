import java.util.ArrayList;

public class ZipCode extends ServiceCommunicator {
    public String zipcode;
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    public ZipCode(String zipcode) {
        super("http://api.zippopotam.us/us/" + zipcode);
        this.zipcode = zipcode;
        this.keys = keys;
        this.values = values;
    }
}

