import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by dam on 15/12/16.
 */
public class RestSynchronous {
    private static Retrofit retrofit;

    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AthleteService athleteService = retrofit.create(AthleteService.class);
        Call<List<Athlete>> call = athleteService.getAllAthlete();
        Response<List<Athlete>> response = call.execute();

        if(response.isSuccessful()){
            List<Athlete> athleteList = response.body();
            System.out.println("Status code: " + response.code() + System.lineSeparator() +
                    "GET all athletes: " + athleteList);
        }else{
            System.out.println("Status code: " + response.code() +
                    "Message error: " + response.errorBody());
        }
    }

}
