import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> fromCurrency = new ComboBox<>();
        ComboBox<String> toCurrency = new ComboBox<>();
        TextField amountField = new TextField();
        TextField resultField = new TextField();

        CurrencyConverter.populateCurrencies(fromCurrency, toCurrency);

        amountField.setPromptText("Enter amount");
        resultField.setEditable(false);

        fromCurrency.setOnAction(event -> CurrencyConverter.convert(fromCurrency, toCurrency, amountField, resultField));
        toCurrency.setOnAction(event -> CurrencyConverter.convert(fromCurrency, toCurrency, amountField, resultField));
        amountField.textProperty().addListener((observable, oldValue, newValue) -> CurrencyConverter.convert(fromCurrency, toCurrency, amountField, resultField));

        VBox root = new VBox(10, fromCurrency, toCurrency, amountField, resultField);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Currency Converter");
        primaryStage.show();
    }
}
