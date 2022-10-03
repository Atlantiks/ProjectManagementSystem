package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class CompanyMenu implements Command {
    public static final String COMPANY_COMMANDS = "company service";
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return COMPANY_COMMANDS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("In company menu..");
    }
}
