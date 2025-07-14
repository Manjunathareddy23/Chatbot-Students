import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/** Gemini API HTTP Helper */
public class GeminiConnector {

    private static final String ENDPOINT =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public static String sendPrompt(String prompt, String apiKey) throws Exception {
        URL url = new URL(ENDPOINT + apiKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        JSONObject body = new JSONObject()
            .put("contents", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("parts", new org.json.JSONArray()
                        .put(new JSONObject().put("text", prompt))
                    )
                )
            );

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()
        ));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        if (responseCode != 200) {
            throw new RuntimeException("HTTP " + responseCode + " → " + sb.toString());
        }

        JSONObject json = new JSONObject(sb.toString());
        return json.getJSONArray("candidates")
                   .getJSONObject(0)
                   .getJSONObject("content")
                   .getJSONArray("parts")
                   .getJSONObject(0)
                   .getString("text");
    }
}
