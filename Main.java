public class Main {
    public static void main(String[] args) {
        String apiKey = "AIzaSyAHKXabencMizAeR3GzuqYfg9IJjYDYHos";

        ChatBot myBot = new GeminiChatBot("StudyBot", apiKey);

        // Simulate multiple users using threads
        String[] questions = {
            "What is multithreading in Java?",
            "Explain OOPs with examples.",
            "How does exception handling work?",
            "Give me a Java project idea."
        };

        for (String question : questions) {
            UserThread user = new UserThread(myBot, question);
            user.start();
        }
    }
}
