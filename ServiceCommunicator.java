import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.json.*;


/*
 Simplistic utility class to connect to a web server and retrieve data.
 Works for typical websites (but not very useful) and works for service calls that don't require authentication
 -fdg
 */
public class ServiceCommunicator {
    private HttpURLConnection conn;
    public JSONObject json;
    public JSONObject keyjson = new JSONObject("{}");
    public ArrayList<Object> keys = new ArrayList<>();
    public ArrayList<Object> values = new ArrayList<>();

    /*
     Constructor - supply the target URL and try to make the connection
     */
    public ServiceCommunicator(String serviceURL) {
        this.keys = keys;
        this.values = values;
        try {
            URL url = new URL(serviceURL);
            URLConnection urlConnection = url.openConnection();
            conn = (HttpURLConnection) urlConnection;
        } catch (Exception ex) {
            System.err.println("**Error in constructor - Cannot create the URL or make the connection.");
            System.exit(1);
        }
    }


    /*
     get() - read the reply from the webserver specifed in the constructor.
            returns a string that captured the output from that service.
     */
    public JSONObject get() {
        String urlString = "";
        String current;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((current = in.readLine()) != null) {
                urlString += current;
            }
            try {
                json = new JSONObject(urlString);
                System.out.println(json);
            } catch (Exception JSONException) {
                System.out.println("Unable to format the contents of the URL. " +
                        "See below for unformatted results:");
                System.out.println(urlString);
            }
        } catch (IOException iox) {
            System.err.println("**Error in get().  Cannot read from URL");
            System.exit(1);
        }

        return json;
    }

    public void printJsonObject(JSONObject jsonObj) {
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String) key;
            Object keyvalue = jsonObj.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                System.out.println(keyStr + ": {");
                printJsonObject((JSONObject) keyvalue);
                System.out.println("}");
            } else if (keyvalue instanceof JSONArray) {
                System.out.println(keyStr + ": [");
                JSONArray array = (JSONArray) keyvalue;
                for (int i = 0; i < array.length(); i++) {
                    try {
                        printJsonObject((JSONObject) array.get(i));
                    } catch (Exception e) {
                        System.out.println(key + ": " + array.get(i));
                    }
                }

                System.out.println("]");
            } else {
                System.out.println(keyStr + ": " + keyvalue);
            }
        }
    }

    public void printByKey(Scanner userinput, String input, JSONObject jsonObj, JSONObject keyjson) {
            //storeKeys(jsonObj);
            System.out.println("Please enter the key you would like to search for. Key names are case sensitive. \nType 'help' to see full list of keys:");
            userinput = new Scanner(System.in);
            input = userinput.nextLine();
            System.out.println(input);
            if (!(keyjson.has(input) || input.equals("quit") || input.equals("all") || input.equals("") || input.equals("help"))) {
                while (!(keyjson.has(input))) {
                    System.out.println(input + " is not a valid response. Type 'help' to see all valid responses.");
                    input = userinput.next();
                }
            } else if (input.equals("help")) {
                    while (input.equals("help")) {
                        System.out.println("Please see below all keys for your search:");
                        printKeys(this.keys);
                        System.out.println("\nPlease enter the key you would like to search for:");
                        input = userinput.next();
                    }
                }

            for (Object key : this.keys) {
                String keyStr = (String) key;
                Object keyvalue = keyjson.get(keyStr);
                if (input.equals(keyStr) && keyvalue instanceof JSONObject) {
                    printJsonObject((JSONObject) keyvalue);
                    //System.out.println(keyStr + ": " + keyvalue);
                    break;
                } else if (input.equals(keyStr) && keyvalue instanceof JSONArray) {
                    System.out.println(keyStr + ": " + keyvalue);
                    break;
                } else if (input.equals(keyStr)) {
                    System.out.println(keyStr + ": " + keyvalue);
                    break;
                } else if (input.equals("all") || input.equalsIgnoreCase("")) {
                    printJsonObject(jsonObj);
                    break;
                } else if (input.equals("quit")) {
                    System.exit(0);
                }
            }
        }



    public ArrayList<Object> storeKeys(JSONObject jsonObj) {
        //System.out.println(jsonObj.keySet());
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String) key;
            Object keyvalue = jsonObj.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                //System.out.println(keyStr + ": {");
                //storeKeys((JSONObject) key);
                keys.add(key);
                storeKeys((JSONObject) keyvalue);
                //keys.add(keyvalue);
                //System.out.println("}");
            } else if (keyvalue instanceof JSONArray) {
                //storeKeys((JSONObject) key);
                keys.add(key);
                //System.out.println(keyStr + ": [");
                JSONArray array = (JSONArray) keyvalue;
                for (int i = 0; i < array.length(); i++) {
                    try {
                       storeKeys((JSONObject) array.get(i));
                       //keys.add((array.get(i)));
                    } catch (Exception e) {
                        //System.out.println(key + ": " /*+ array.get(i)*/);
                        //storeKeys((JSONObject) key);
                        keys.add(key);
                    }
                }
                // System.out.println("]");
            } else {
                //System.out.println(keyStr + ": " /*+ keyvalue*/);
                //storeKeys(key);
                keys.add(key);
            }
        }
        return keys;
    }

    private static void printKeys(ArrayList<Object> keys) {
        for (Object key : keys) {
            System.out.println(key);
        }
    }

    public static void clearKeys(ArrayList<Object> keys) {
        for (Object key : keys) {
            keys.remove(key);
        }
    }

    public ArrayList<Object> storeValues(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            String keyStr = (String) key;
            Object keyvalue = jsonObject.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                //System.out.println(keyStr + ": {");
                //storeKeys((JSONObject) key);
                //keys.add(key);
                values.add(keyvalue);
                storeValues((JSONObject) keyvalue);
                //System.out.println("}");
            } else if (keyvalue instanceof JSONArray) {
                //storeKeys((JSONObject) key);
                values.add(keyvalue);
                //System.out.println(keyStr + ": [");
                JSONArray array = (JSONArray) keyvalue;
                for (int i = 0; i < array.length(); i++) {
                    try {
                        storeValues((JSONObject) array.get(i));
                        //keys.add((array.get(i)));
                    } catch (Exception e) {
                        //System.out.println(key + ": " /*+ array.get(i)*/);
                        //storeKeys((JSONObject) key);
                        values.add(keyvalue);
                    }
                }
                // System.out.println("]");
            } else {
                //System.out.println(keyStr + ": " /*+ keyvalue*/);
                //storeKeys(key);
                values.add(keyvalue);
            }
        }
        return values;
    }


    public JSONObject createFullKeySet(ArrayList<Object> keys, ArrayList<Object> values, JSONObject jsonObject) {
        jsonObject.clear();
        for (int i = 0; i < keys.size(); i++) {
                jsonObject.accumulate((String) keys.get(i), values.get(i));
            } return jsonObject;
        }

    }


    /*

     main method used just to test this class in isolation.  You will learn more about testing in another course
     */
   /* public static void main(String[] argv) {
        ZipCode wash_heights = new ZipCode("10033");
        Music thriller = new Music("Thriller");
        Television survivor = new Television("Survivor");
        //new  ServiceCommunicator("http://api.zippopotam.us/us/" + wash_heights.zipcode).get();
        //ServiceCommunicator.printJsonObject(json);
        System.out.println("==================================");
        //new ServiceCommunicator("https://itunes.apple.com/search?term=" + thriller.name + "\\&limit=1").get();
       // ServiceCommunicator.printJsonObject(json);
        System.out.println("==================================");
        //System.out.println(new ServiceCommunicator("http://www.google.com").get());
        //ServiceCommunicator.printJsonObject(json);
        System.out.println("==================================");
        //new ServiceCommunicator("http://api.tvmaze.com/singlesearch/shows?q=" + survivor.name).get();
        //ServiceCommunicator.printJsonObject(json);
    } */


