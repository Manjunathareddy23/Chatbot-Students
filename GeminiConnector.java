import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/** Low‑level HTTP helper that hits Gemini REST endpoint. */
public class GeminiConnector {

    private static final String ENDPOINT =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    public static String sendPrompt(String prompt, String apiKey) throws Exception {

        // 1. Build URL
        URL url = new URL(ENDPOINT + apiKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 2. Headers & method
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // 3. Request body (escape prompt safely)
        String body = """
        {
          "contents": [{
            "parts": [{ "text": %s }]
          }]
        }
        """.formatted(JSONObject.quote(prompt));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
        }

        // 4. Read response
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }

        // 5. Parse JSON
        JSONObject json = new JSONObject(sb.toString());
        return json.getJSONArray("candidates")
                   .getJSONObject(0)
                   .getJSONObject("content")
                   .getJSONArray("parts")
                   .getJSONObject(0)
                   .getString("text");
    }
}
