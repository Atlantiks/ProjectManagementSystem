package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.company.CreateCompany;
import ua.com.goit.command.company.DeleteCompanyById;
import ua.com.goit.command.developer.CreateDeveloper;
import ua.com.goit.command.developer.DeleteDeveloperById;
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
        view.write("===========COMPANY SERVICE MENU===========");
        view.write(String.format("Type %s to add new company", "\033[0;93m" + CreateCompany.CREATE_COMP + "\033[0m"));
        view.write(String.format("Type %s to remove company with particular Id", "\033[0;93m" + DeleteCompanyById.DEL_COMPANY_BY_ID + "\033[0m"));

        view.write(String.format("\n .. or type %s to see all available commands","\033[0;93m" + "help" + "\033[0m"));
    }
}
