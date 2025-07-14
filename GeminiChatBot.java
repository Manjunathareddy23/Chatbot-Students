/** Concrete ChatBot that calls Gemini API for answers. */
public class GeminiChatBot extends ChatBot {

    private final GeminiAPI api;

    public GeminiChatBot(String name, String apiKey) {
        super(name);
        this.api = new GeminiAPI(apiKey);
    }

    @Override
    public void respond(String userInput) {
        String reply = api.getResponse(userInput);
        System.out.println(name + ": " + reply);
    }
}
