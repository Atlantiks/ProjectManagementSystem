package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.dto.CompanyDto;
import ua.com.goit.dto.CreateCompanyDto;
import ua.com.goit.dto.UpdateCompanyDto;
import ua.com.goit.entity.Company;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.mapper.CreateCompanyMapper;
import ua.com.goit.mapper.UpdateCompanyMapper;
import ua.com.goit.repository.CompanyRepository;
import ua.com.goit.repository.SessionManager;
import ua.com.goit.validation.CreateCompanyValidator;
import ua.com.goit.validation.UpdateCompanyValidator;
import ua.com.goit.view.View;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompanyService {
    private static final CompanyService COMPANY_SERVICE = new CompanyService();
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    private static final CreateCompanyValidator COMPANY_VALIDATOR = CreateCompanyValidator.getInstance();
    private static final UpdateCompanyValidator UPDATE_COMPANY_VALIDATOR = UpdateCompanyValidator.getInstance();
    private static final CreateCompanyMapper COMPANY_MAPPER = CreateCompanyMapper.getInstance();
    private static final UpdateCompanyMapper UPDATE_COMPANY_MAPPER = UpdateCompanyMapper.getInstance();
    @Getter
    @Setter
    private View view;

    private final CompanyRepository companyRepository;

    private CompanyService() {
        companyRepository = new CompanyRepository(SessionManager.buildSessionFactory());
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

        Company savedCompany = companyRepository.save(newCompany);

        if (Objects.nonNull(savedCompany.getId())) {
            view.write("\033[0;92mThe following Company was successfully added to database:\033[0m");
            view.write(savedCompany + "\n");
        }
    }

    public void createCompany(CreateCompanyDto companyDto) {
        if (COMPANY_VALIDATOR.isValid(companyDto)) {
            companyRepository.save(COMPANY_MAPPER.mapFrom(companyDto));
        } else {
            throw new ValidationException("Couldn't pass validation test for new company");
        }
    }

    public void deleteCompanyById() {
        view.write("Please enter Company's id:");
        Integer companyId;
        try {
            companyId = Integer.parseInt(view.read());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid id provided");
        }


        companyRepository.delete(companyId);
        view.write(companyRepository.findById(companyId).isPresent() ?
                String.format("Couldn't delete company with following Id = %d", companyId )
                : "Success!");

/*        if (COMPANY_DAO.removeById(companyId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete Company with following Id = %d", companyId ));
        }*/
    }

    public void deleteCompanyById(String id) {
        Integer companyId;
        try {
            companyId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        try {
            companyRepository.delete(companyId);
        } catch (IllegalArgumentException e) {
            throw new DataBaseOperationException(
                            String.format("Couldn't delete Company with following Id = %d", companyId ));
        }
    }

    public Company findCompanyById() {
        view.write("Please enter Company's id:");
        Integer companyId = Integer.parseInt(view.read());

        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new NotFoundException(
                        String.format("\033[0;91mCompany with Id = %d wasn't found\033[0m", companyId)));

        view.write(company.toString());

        return company;
    }

    public Company findCompanyById(String id) {
        Integer companyId;
        try {
            companyId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NotFoundException("Incorrect company id provided");
        }

        return companyRepository.findById(companyId).orElseThrow(
                () -> new NotFoundException(
                        String.format("Company with Id = %d wasn't found", companyId)));
    }

    public void update(UpdateCompanyDto companyDto) {
        if (!UPDATE_COMPANY_VALIDATOR.isValid(companyDto)) throw new ValidationException("Validation failed");
        companyRepository.update(UPDATE_COMPANY_MAPPER.mapFrom(companyDto));
    }

    public UpdateCompanyDto getCompanyForUpdateById(String id) {
        Integer companyId;
        try {
            companyId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        Company company =  companyRepository.findById(companyId).orElseThrow(() ->
                new NotFoundException(
                        String.format("Company with Id = %d wasn't found", companyId)));


        return UPDATE_COMPANY_MAPPER.mapTo(company);
    }

    public List<CompanyDto> findAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> CompanyDto.builder()
                        .id(company.getId())
                        .name(company.getName())
                        .country(company.getCountry())
                        .build())
                .collect(Collectors.toList());
    }
}
