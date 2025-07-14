/** Runs a single request in its own thread (multithreading demo). */
public class UserThread extends Thread {
    private final ChatBot bot;
    private final String message;

    public UserThread(ChatBot bot, String message) {
        this.bot = bot;
        this.message = message;
    }

    @Override
    public void run() {
        bot.respond(message);
    }
}
