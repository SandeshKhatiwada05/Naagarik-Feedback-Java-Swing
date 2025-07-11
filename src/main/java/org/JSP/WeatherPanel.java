package org.JSP;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherPanel extends JPanel {
    private static final String API_KEY = "c770ccefac4b4feeb4d44251251107";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    private JLabel lblCity, lblTemp, lblCondition, lblHumidity, lblWind;

    public WeatherPanel(String cityName) {
        setLayout(new GridLayout(5, 1, 5, 2));
        setBackground(new Color(255, 255, 255, 180));
        setBorder(BorderFactory.createTitledBorder("Current Weather"));

        lblCity = new JLabel();
        lblTemp = new JLabel();
        lblCondition = new JLabel();
        lblHumidity = new JLabel();
        lblWind = new JLabel();

        add(lblCity);
        add(lblTemp);
        add(lblCondition);
        add(lblHumidity);
        add(lblWind);

        fetchWeather(cityName);
    }

    private void fetchWeather(String city) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String apiUrl = BASE_URL + "?key=" + API_KEY + "&q=" + city + "&aqi=no";
                    HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(response.toString());
                    JSONObject location = json.getJSONObject("location");
                    JSONObject current = json.getJSONObject("current");
                    JSONObject condition = current.getJSONObject("condition");

                    lblCity.setText("📍 " + location.getString("name") + ", " + location.getString("country"));
                    lblTemp.setText("🌡 Temp: " + current.getDouble("temp_c") + " °C");
                    lblCondition.setText("Condition: " + condition.getString("text"));
                    lblHumidity.setText("💧 Humidity: " + current.getInt("humidity") + "%");
                    lblWind.setText("Wind: " + current.getDouble("wind_kph") + " kph");

                } catch (Exception e) {
                    lblCity.setText("Failed to load weather data.");
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
}
