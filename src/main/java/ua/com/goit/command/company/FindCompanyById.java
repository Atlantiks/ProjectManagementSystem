package ua.com.goit.command.company;

import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.CompanyService;

@RequiredArgsConstructor
public class FindCompanyById implements Command {
    public static final String FIND_COMPANY_BY_ID = "find company by id";
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return FIND_COMPANY_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        COMPANY_SERVICE.findCompanyById();
    }
}
