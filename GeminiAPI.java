/** Adds validation & exception handling around raw HTTP call. */
public class GeminiAPI {
    private final String apiKey;

    public GeminiAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getResponse(String prompt) {
        try {
            if (prompt == null || prompt.isBlank())
                throw new IllegalArgumentException("Prompt must not be empty.");
            return GeminiConnector.sendPrompt(prompt, apiKey);
        } catch (Exception e) {
            return "⚠️  Error: " + e.getMessage();
        }
    }
}
