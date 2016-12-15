import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface AthleteService {
    @GET("/athletes")
    Call<List<Athlete>> getAllAthlete();

    @GET("/athletes/{id}")
    Call<Athlete> getAthlete(@Path("id") Long id);

    @GET("/athleteError")
    Call<List<Athlete>> getError();

    @POST("/athletes")
    Call<Athlete> createAthlete(@Body Athlete athlete);


    @PUT("/athletes")
    Call<Athlete> updateAthlete(@Body Athlete athlete);

    @DELETE("/athletes/{id}")
    Call<Void> deleteAthlete(@Path("id") Long id);
}
