import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


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

        /*creamos la llamada a la función de obtener todos los atletas
        Call<List<Athlete>> callAllAthletes = athleteService.getAllAthlete();
        Obtenemos la respuesta a la llamada (ejecutamos una respuesta)
        Response<List<Athlete>> response = callAllAthletes.execute();
        Simplificado: */
        //Bloquea a la espera de que el servidor conteste

        Response<List<Athlete>> responseAllAthlete = athleteService.getAllAthlete().execute();
        if (responseAllAthlete.isSuccessful()) {
            List<Athlete> athleteList = responseAllAthlete.body();
            System.out.println("Status code: " + responseAllAthlete.code() + System.lineSeparator() +
                    "GET all athletes: " + athleteList);
        } else { //si no obtiene una respuesta satisfactoria
            System.out.println("Status code: " + responseAllAthlete.code() +
                    "Message error: " + responseAllAthlete.errorBody());
        }


        //creamos la llamada a una opcion de url que sabemos que dará error ya que no existe
        Response<List<Athlete>> responseUrlError = athleteService.getError().execute();
        //como sabemos que no será satisfactoria solo creamos el mensaje de insatisfactorio
        if (!responseUrlError.isSuccessful()) {
            System.out.println("Status code: " + responseUrlError.code() + " Message error: " + responseUrlError.raw());
        }


        //Post
        Athlete athlete = new Athlete();
        athlete.setName("John");
        athlete.setSurname("Whick");
        athlete.setNacionality("Alola");
        athlete.setBirthday(LocalDate.of(1994, 05, 16));
        Response<Athlete> postAthletes = athleteService.createAthlete(athlete).execute();

        if (postAthletes.isSuccessful()) {
            Athlete athleteResp = postAthletes.body();
            System.out.println("Status code: " + postAthletes.code() + System.lineSeparator() +
                    "POST player: " + athleteResp);

            //creamos una llamada para llamar a un atleta en concreto
            Response<Athlete> responseOneAthlete = athleteService.getAthlete(athleteResp.getId()).execute();
            if (responseOneAthlete.isSuccessful()) {
                System.out.println("GET ONE->Status code: " + responseOneAthlete.code() + " Athlete: " + responseOneAthlete.body());
            } else {
                System.out.println("Status code: " + responseOneAthlete.code() + "Message error: " + responseOneAthlete.errorBody());
            }

            //Put
            athleteResp.setNacionality("MICASA");
            Response<Athlete> putAthlete = athleteService.updateAthlete(athleteResp).execute();

            if (responseOneAthlete.isSuccessful()) {
                System.out.println("Status code: " + putAthlete.code() + System.lineSeparator() +
                        "PUT player: " + putAthlete.body());
            } else {
                System.out.println("Status code: " + putAthlete.code() + "Message error: " + putAthlete.errorBody());
            }

            //eliminar athleta
            Response<Void> athleteDelete = athleteService.deleteAthlete(athleteResp.getId()).execute();

            System.out.println("DELETE status code: " + athleteDelete.code());

            //Comprobamos que se ha borrado
            responseAllAthlete = athleteService.getAllAthlete().execute();

            System.out.println("Comprobación del delete " +
                    "Status code: " + responseAllAthlete.code() + System.lineSeparator() +
                    "GET players: " + responseAllAthlete.body());

        }
    }

}
