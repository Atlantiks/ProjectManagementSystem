package ua.com.goit.command.company;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeleteCompanyById implements Command {
    public static final String DEL_COMPANY_BY_ID = "delete company by id";
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return DEL_COMPANY_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter Company's id:");
        Integer companyId = Integer.parseInt(view.read());

        if (COMPANY_DAO.removeById(companyId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete Company with following Id = %d", companyId ));
        }
    }
}
