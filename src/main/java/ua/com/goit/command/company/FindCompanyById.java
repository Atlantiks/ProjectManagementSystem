package ua.com.goit.command.company;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class FindCompanyById implements Command {
    public static final String FIND_COMPANY_BY_ID = "find company by id";
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return FIND_COMPANY_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter Company's id:");
        Integer companyId = Integer.parseInt(view.read());

        COMPANY_DAO.findById(companyId).ifPresentOrElse(
                dev -> view.write(dev.toString()),
                () -> view.write(String.format("\033[0;91mCompany with Id = %d wasn't found\033[0m", companyId)));
    }
}
