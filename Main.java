import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ✅ Load API key from .env file
        final String API_KEY = EnvLoader.get("GEMINI_API_KEY");

        if (API_KEY == null || API_KEY.isBlank()) {
            System.err.println("❌ Gemini API key is missing! Please set it in .env");
            return;
        }

        ChatBot bot = new GeminiChatBot("StudyBot", API_KEY);

        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("You: ");
            String q = sc.nextLine();
            if ("exit".equalsIgnoreCase(q)) break;

            new UserThread(bot, q).start();
        }
        sc.close();
        System.out.println("Chat ended.");
    }
}
