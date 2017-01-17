import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

import static sun.audio.AudioPlayer.player;


public class RestSynchronous {
    //creamos un atributo de tipo retrofit
    private static Retrofit retrofit;

    public static void main(String[] args) throws IOException {
        //le pasamos la url
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AthleteService athleteService = retrofit.create(AthleteService.class);

        //creamos la llamada a la función de obtener todos los atletas
        Call<List<Athlete>> callAllAthletes = athleteService.getAllAthlete();
        //Obtenemos la respuesta a la llamada (ejecutamos una respuesta)
        Response<List<Athlete>> response = callAllAthletes.execute();
        //si obtiene una respuesta satisfactoria
        if(response.isSuccessful()){
            List<Athlete> athleteList = response.body();
            System.out.println("Status code: " + response.code() + System.lineSeparator() +
                    "GET all athletes: " + athleteList);
        }else{ //si no obtiene una respuesta satisfactoria
            System.out.println("Status code: " + response.code() +
                    "Message error: " + response.errorBody());
        }

        //creamos la llamada a una opcion de url que sabemos que dará error ya que no existe
        Call<List<Athlete>> callUrlError = athleteService.getError();
        response = callUrlError.execute();
        //como sabemos que no será satisfactoria solo creamos el mensaje de insatisfactorio
        if(!response.isSuccessful()){
            System.out.println("Status code: "+response.code() + " Message error: "+response.raw());
        }

        //creamos una llamada para llamar a un atleta en concreto
        Call<Athlete> callAthlete = athleteService.getAthlete(3L);
        Response<Athlete> responseAthlete = callAthlete.execute();

        if(responseAthlete.isSuccessful()) {
            System.out.println("GET ONE->Status code: "+responseAthlete.code()+ " Athlete: "+responseAthlete.body());
        }else{
            System.out.println("Status code: "+responseAthlete.code() + "Message error: "+responseAthlete.errorBody());
        }

        //eliminar athleta
        Call<Void> callAthleteDelete = athleteService.deleteAthlete();
        Response<Void> responseDelete = callAthleteDelete.execute();
        System.out.println("DELETE status code: " + responseDelete.code());

        callAllAthletes = athleteService.getAllAthlete();
        response = callAllAthletes.execute();

        System.out.println("Comprobación del delete " +
                "Status code: " + response.code() + System.lineSeparator() +
                "GET players: " + response.body());



        //Post
        Athlete athlete = new Athlete();
        athlete.setName("John");

        Call<Athlete> callAthletes = athleteService.createAthlete(athlete);
        Response<Athlete> responseAthlet= callAthletes.execute();

        if(responseAthlet.isSuccessful()) {
            Athlete athleteResp = responseAthlet.body();
            System.out.println("Status code: " + responseAthlet.code() + System.lineSeparator() +
                    "POST player: " + athleteResp);

            //Put
            athleteResp.setNacionality("MICASA");
            callAllAthletes= athleteService.updateAthlete(athleteResp);
            responseAthlet= callAllAthletes.execute();

            System.out.println("Status code: " + responseAthlet.code() + System.lineSeparator() +
                    "PUT player: " + responseAthlet.body());

        }

}
