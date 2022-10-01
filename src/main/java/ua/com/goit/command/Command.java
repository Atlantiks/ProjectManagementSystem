package ua.com.goit.command;

public interface Command {
    boolean canBeExecuted(String input);
    void execute();
}
