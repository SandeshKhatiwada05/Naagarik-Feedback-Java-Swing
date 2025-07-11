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

    private JLabel lblIcon, lblCity, lblTemp, lblCondition, lblHumidity, lblWind;

    public WeatherPanel(String cityName) {
        setLayout(new BorderLayout(10, 0));
        setBackground(new Color(255, 255, 255, 220));
        setBorder(BorderFactory.createTitledBorder("Current Weather"));
        setPreferredSize(new Dimension(250, 110));
        setMaximumSize(new Dimension(250, 110));

        // Load and resize icon to 50x50
        ImageIcon icon = new ImageIcon("src/main/resources/WeatherImage.png");
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        lblIcon = new JLabel(new ImageIcon(img));
        lblIcon.setPreferredSize(new Dimension(50, 50));
        lblIcon.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(lblIcon, BorderLayout.WEST);

        // Info panel with weather details
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new GridLayout(5, 1, 4, 2));

        lblCity = new JLabel("Loading...");
        lblCity.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTemp = new JLabel();
        lblCondition = new JLabel();
        lblHumidity = new JLabel();
        lblWind = new JLabel();

        infoPanel.add(lblCity);
        infoPanel.add(lblTemp);
        infoPanel.add(lblCondition);
        infoPanel.add(lblHumidity);
        infoPanel.add(lblWind);

        add(infoPanel, BorderLayout.CENTER);

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

                    SwingUtilities.invokeLater(() -> {
                        lblCity.setText("📍 " + location.getString("name") + ", " + location.getString("country"));
                        lblTemp.setText("🌡 Temp: " + current.getDouble("temp_c") + " °C");
                        lblCondition.setText("Condition: " + condition.getString("text"));
                        lblHumidity.setText("💧 Humidity: " + current.getInt("humidity") + "%");
                        lblWind.setText("Wind: " + current.getDouble("wind_kph") + " kph");
                    });

                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        lblCity.setText("Failed to load weather data.");
                        lblTemp.setText("");
                        lblCondition.setText("");
                        lblHumidity.setText("");
                        lblWind.setText("");
                    });
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
}
