package ua.com.goit.mapper;

import ua.com.goit.dto.UpdateDeveloperDto;
import ua.com.goit.entity.Developer;
import ua.com.goit.service.CompanyService;

import java.math.BigDecimal;
import java.util.Objects;

public class UpdateDeveloperMapper implements Mapper<UpdateDeveloperDto, Developer> {
    private static final UpdateDeveloperMapper UPDATE_DEVELOPER_MAPPER = new UpdateDeveloperMapper();
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    private UpdateDeveloperMapper() {}

    public static UpdateDeveloperMapper getInstance() {
        return UPDATE_DEVELOPER_MAPPER;
    }

    @Override
    public Developer mapFrom(UpdateDeveloperDto developerDto) {
        Integer companyId = null;
        if (!developerDto.getCompanyName().isBlank()) {
            var company = COMPANY_SERVICE.findAllCompanies().stream()
                    .filter(companyDto -> companyDto.getName().equalsIgnoreCase(developerDto.getCompanyName()))
                    .findFirst().orElse(null);
            System.out.println(company);
            if (Objects.nonNull(company)) companyId = company.getId();
        }
        return Developer.builder()
                .id(Integer.parseInt(developerDto.getId()))
                .firstName(developerDto.getName())
                .lastName(developerDto.getSurname())
                .sex(developerDto.getSex())
                .companyId(companyId)
                .salary(BigDecimal.valueOf(Double.parseDouble(developerDto.getSalary())))
                .build();
    }

    @Override
    public UpdateDeveloperDto mapTo(Developer developer) {
        String devCompanyName = "";
        try {
            var company = COMPANY_SERVICE.findCompanyById(developer.getCompanyId().toString());
            devCompanyName = company.getName();
        } catch (NullPointerException e) {
            devCompanyName = "none";
        }
        return UpdateDeveloperDto.builder()
                .id(developer.getId().toString())
                .name(developer.getFirstName())
                .surname(developer.getLastName())
                .sex(developer.getSex())
                .companyName(devCompanyName)
                .salary(developer.getSalary().toString())
                .build();
    }
}
