package oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

class BasicAuthInterceptor implements Interceptor {

    private final String credentials;

    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}

public class NumberDiscoverer {

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient httpClient = client.build();

        String credential = "session=53616c7465645f5f8fa3fab555aa04757bb1674e41de7a15fb75ffc977bf2bf4dc233b03860b23fcf4032da68357d904";
        Request request = new Request.Builder()
                .url("https://adventofcode.com/2020/day/1/input").header("Authorization", credential)
                .build(); // defaults to GET

        Response response = httpClient.newCall(request).execute();
        InputStream in = response.body().byteStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());

        /*CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://adventofcode.com/2020/day/1/input");
        CloseableHttpResponse response = client.execute(request);*/

        /*URL url = new URL("https://adventofcode.com/2020/day/1/input");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("accept", "text/plain");
        urlConnection.setRequestProperty("accept-encoding", "gzip, deflate, br");
//        HttpRequest request = new DefaultHttpRequestFactory().newHttpRequest("GET", url.getPath());
//            InputStream in = url.openStream();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());*/
    }
}
