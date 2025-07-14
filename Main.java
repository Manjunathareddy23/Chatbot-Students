import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /* 1. Read key from .env */
        final String API_KEY = EnvLoader.get("GEMINI_API_KEY");
        if (API_KEY == null || API_KEY.isBlank()) {
            System.err.println("❌ Gemini API key missing in .env");
            return;
        }

        /* 2. Instantiate chatbot */
        ChatBot bot = new GeminiChatBot("StudyBot", API_KEY);

        /* 3. CLI loop with multithreading demo */
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'exit' to quit.\n");
        while (true) {
            System.out.print("You: ");
            String q = sc.nextLine();
            if ("exit".equalsIgnoreCase(q)) break;
            new UserThread(bot, q).start();   // each request on separate thread
        }
        sc.close();
        System.out.println("Chat ended.");
    }
}
