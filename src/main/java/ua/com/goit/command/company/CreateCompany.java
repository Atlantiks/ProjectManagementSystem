package ua.com.goit.command.company;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.Formatter;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.entity.Company;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateCompany implements Command {
    public static final String CREATE_COMP = "create company";
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_COMP.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("To create new Company you have to enter required company name and (optionally) non-required ones.");

        view.write("1. Please enter new Company's name:");
        String companyName = view.read();
        if (companyName.isBlank()) {
            throw new BlancFieldException();
        } else {
            companyName = Formatter.capitalize(companyName);
        }

        Company newCompany = new Company(companyName);

        view.write("Would you like to add non-required fields? Y/N  Press ENTER for default option(N)");

        String userAnswer = view.read();
        userAnswer = userAnswer.isBlank() ? "N" : userAnswer.substring(0, 1).toUpperCase();

        switch (userAnswer) {
            case "Y":
                view.write("2. Please enter new Company's location (country):");
                String country = Formatter.capitalize(view.read());

                newCompany.setCountry(country);
                break;
            case "N":
                break;
            default:
                view.write("Answer not recognised. Default parameters will be applied");
                break;
        }

        Company savedCompany = COMPANY_DAO.save(newCompany, view);

        if (Objects.nonNull(savedCompany.getId())) {
            view.write("\033[0;92mThe following Company was successfully added to database:\033[0m");
            view.write(savedCompany + "\n");
        }
    }
}
