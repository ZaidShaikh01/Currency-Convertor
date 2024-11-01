import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type currency to convert from");
        String from = sc.next().toUpperCase();
        System.out.println("Type currency to convert to");
        String to = sc.next().toUpperCase();
        System.out.println("Type quantity to convert");
        double amount = sc.nextDouble();

        try {
            // Formulate the request URL
            String urlStr = "https://api.frankfurter.app/latest?amount=" + amount + "&from=" + from + "&to=" + to;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Check if the response starts with '{' (JSON format)
            String responseStr = response.toString();
            if (responseStr.startsWith("{")) {
                // Parse JSON
                JSONObject jsonResponse = new JSONObject(responseStr);
                double convertedAmount = jsonResponse.getJSONObject("rates").getDouble(to);
                System.out.println("Converted Amount: " + convertedAmount);
            } else {
                System.out.println("Error: Received unexpected response format.");
                System.out.println("Response: " + responseStr);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
