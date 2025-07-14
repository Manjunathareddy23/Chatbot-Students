/** Abstract base defining chatbot behaviour (OOP polymorphism). */
public abstract class ChatBot {
    protected final String name;

    public ChatBot(String name) { this.name = name; }

    /** Respond to user input (to be overridden). */
    public abstract void respond(String userInput);
}
