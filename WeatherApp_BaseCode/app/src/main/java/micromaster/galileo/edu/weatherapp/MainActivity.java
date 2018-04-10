package micromaster.galileo.edu.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import micromaster.galileo.edu.weatherapp.API.WeatherInterface;
import micromaster.galileo.edu.weatherapp.model.WeatherData;
import micromaster.galileo.edu.weatherapp.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String BASE_URL = "http://api.wunderground.com/api/";
    private final static String API_KEY = "b6a20ca12c18724e";

    TextView tvPressure;
    TextView tvCountryName;
    TextView tvTemperature;
    TextView tvHumidity;
    TextView tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPressure = (TextView) findViewById(R.id.pressure);
        tvCountryName = (TextView) findViewById(R.id.countryName);
        tvTemperature = (TextView) findViewById(R.id.temperature);
        tvHumidity = (TextView) findViewById(R.id.humidity);
        tvWeather = (TextView) findViewById(R.id.weather);

        GetWeatherTask getWeatherTask = new GetWeatherTask();
        getWeatherTask.execute();

    }

    private class GetWeatherTask extends AsyncTask<Void, Integer, WeatherData> {

        @Override
        protected WeatherData doInBackground(Void... voids) {

            //*************** TODO: MOVE THIS BLOCK OF CODE TO AN ASYNC_TASK**********
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherInterface weatherInterface = retrofit.create(WeatherInterface.class);
            Call<WeatherResponse> call = weatherInterface.getWeatherFromSanFrancisco(API_KEY);
            WeatherResponse weatherResponse = null;
            try {
                weatherResponse = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //*************************************************************************

            return weatherResponse.getWeatherData();
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {
            super.onPostExecute(weatherData);

            tvPressure.setText(weatherData.getPressure().toString());
            tvCountryName.setText(weatherData.getDisplayLocation().getCityName());
            tvTemperature.setText(weatherData.getTemp());
            tvHumidity.setText(weatherData.getHumidity());
            tvWeather.setText(weatherData.getWeather());
        }
    }

}
