package ua.com.goit.command.company;

import ua.com.goit.command.Command;
import ua.com.goit.service.CompanyService;

public class DeleteCompanyById implements Command {
    public static final String DEL_COMPANY_BY_ID = "delete company by id";
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return DEL_COMPANY_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        COMPANY_SERVICE.deleteCompanyById();
    }
}
