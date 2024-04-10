import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    public static void populateCurrencies(ComboBox<String> fromCurrency, ComboBox<String> toCurrency) {
        fromCurrency.getItems().addAll("USD", "EUR", "GBP");
        toCurrency.getItems().addAll("USD", "EUR", "GBP");
    }

    public static void convert(ComboBox<String> fromCurrency, ComboBox<String> toCurrency, TextField amountField, TextField resultField) {
        String apiKey = "Your API"; // Add your API key here (not added from our side because of security/privacy concerns)
        String from = fromCurrency.getValue();
        String to = toCurrency.getValue();
        String amount = amountField.getText().trim();

        if (amount.isEmpty()) {
            resultField.setText("Please enter an amount");
            return;
        }

        try {
            double amountNum = Double.parseDouble(amount);
            double rate = getExchangeRate(from, to, apiKey);
            double result = amountNum * rate;
            resultField.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
            resultField.setText("Invalid amount: Please enter a valid number");
        } catch (Exception e) {
            resultField.setText("Error fetching rate: " + e.getMessage());
        }
    }

    private static double getExchangeRate(String from, String to, String apiKey) throws IOException, URISyntaxException, InterruptedException {
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, from); // Add your APIExchange link (not added from our side because of security/privacy concerns)
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseData = response.body();

        int startIndex = responseData.indexOf("\"" + to + "\":");
        int endIndex = responseData.indexOf(",", startIndex);
        String toRateStr = responseData.substring(startIndex + to.length() + 3, endIndex);

        return Double.parseDouble(toRateStr);
    }
}
