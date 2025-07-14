public static String sendPrompt(String prompt, String apiKey) throws Exception {

    URL url = new URL(ENDPOINT + apiKey);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    conn.setDoOutput(true);

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

    int responseCode = conn.getResponseCode();

    BufferedReader reader;
    if (responseCode == 200) {
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
        reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line);
    }
    reader.close();

    if (responseCode != 200) {
        // Print the actual error message from Google Gemini API
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
