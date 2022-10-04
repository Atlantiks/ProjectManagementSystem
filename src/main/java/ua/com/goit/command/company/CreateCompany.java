package ua.com.goit.command.company;

import ua.com.goit.command.Command;
import ua.com.goit.service.CompanyService;

public class CreateCompany implements Command {
    public static final String CREATE_COMP = "create company";
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_COMP.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        COMPANY_SERVICE.createCompany();
    }
}
