package ua.com.goit.mapper;

import ua.com.goit.Formatter;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.dto.CreateDeveloperDto;
import ua.com.goit.entity.Company;
import ua.com.goit.entity.Developer;
import ua.com.goit.repository.CompanyRepository;
import ua.com.goit.repository.SessionManager;

import java.math.BigDecimal;
import java.util.Optional;

public class CreateDeveloperMapper implements Mapper<CreateDeveloperDto, Developer> {
    private static final CreateDeveloperMapper CREATE_DEVELOPER_MAPPER = new CreateDeveloperMapper();
    private final CompanyRepository companyRepository;

    private CreateDeveloperMapper() {
        companyRepository = new CompanyRepository(SessionManager.buildSessionFactory());
    }

    public static CreateDeveloperMapper getInstance() {
        return CREATE_DEVELOPER_MAPPER;
    }

    @Override
    public Developer mapFrom(CreateDeveloperDto developerDto) {
        Integer companyId = null;
        if (!developerDto.getCompanyId().isBlank()) {
            companyId = Integer.parseInt(developerDto.getCompanyId());
        }

        var company = companyRepository.findById(companyId);

        return Developer.builder()
                .firstName(Formatter.capitalize(developerDto.getFirstName()))
                .lastName(Formatter.capitalize(developerDto.getLastName()))
                .sex(developerDto.getSex())
                .company(company.orElse(null))
                .salary(BigDecimal.valueOf(Double.parseDouble(developerDto.getSalary())))
                .build();
    }

    @Override
    public CreateDeveloperDto mapTo(Developer developer) {
        return CreateDeveloperDto.builder()
                .firstName(developer.getFirstName())
                .lastName(developer.getLastName())
                .sex(developer.getSex())
                .build();
    }
}
