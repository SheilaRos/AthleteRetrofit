import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by dam on 15/12/16.
 */
public class RestAsync {
    private static Retrofit retrofit;
    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AthleteService athleteService = retrofit.create(AthleteService.class);

        Call<List<Athlete>> call = athleteService.getAllAthlete();
        call.enqueue(new Callback<List<Athlete>>() {
            @Override
            public void onResponse(Call<List<Athlete>> call, Response<List<Athlete>> response) {
                System.out.println("Statuc code: "+response.code() + System.lineSeparator() + "Get all athletes: "+ response.body());
            }

            @Override
            public void onFailure(Call<List<Athlete>> call, Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }
        });
    }
}
