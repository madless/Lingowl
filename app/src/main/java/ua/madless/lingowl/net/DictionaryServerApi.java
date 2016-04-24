package ua.madless.lingowl.net;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import ua.madless.lingowl.core.model.server_response.ServerResponse;

/**
 * Created by User on 19.01.2016.
 */
public interface DictionaryServerApi {
    @GET("/api/v1/dicservice.json/lookup")
    public Call<ServerResponse> getWordLookup(@Query("key") String apiKey, @Query("lang") String language, @Query("text") String text);
}
