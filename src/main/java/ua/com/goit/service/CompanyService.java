package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.dto.CompanyDto;
import ua.com.goit.entity.Company;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.view.View;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompanyService {
    private static final CompanyService COMPANY_SERVICE = new CompanyService();
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    @Getter
    @Setter
    private View view;

    private CompanyService() {
    }

    public static CompanyService getInstance() {
        return COMPANY_SERVICE;
    }


    public void createCompany() {
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

    public void deleteCompanyById() {
        view.write("Please enter Company's id:");
        Integer companyId = Integer.parseInt(view.read());

        if (COMPANY_DAO.removeById(companyId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete Company with following Id = %d", companyId ));
        }
    }

    public Company findCompanyById() {
        view.write("Please enter Company's id:");
        Integer companyId = Integer.parseInt(view.read());

        Company company = COMPANY_DAO.findById(companyId).orElseThrow(
                () -> new NotFoundException(
                        String.format("\033[0;91mCompany with Id = %d wasn't found\033[0m", companyId)));

        view.write(company.toString());

        return company;
    }

    public List<CompanyDto> findAllCompanies() {
        return COMPANY_DAO.findAll().stream()
                .map(company -> new CompanyDto(company.getName(),company.getId()))
                .collect(Collectors.toList());
    }
}
