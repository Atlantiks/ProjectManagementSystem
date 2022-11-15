package ua.com.goit.mapper;

import ua.com.goit.Formatter;
import ua.com.goit.dto.CreateCompanyDto;
import ua.com.goit.entity.Company;

public class CreateCompanyMapper implements Mapper<CreateCompanyDto, Company> {
    private static final CreateCompanyMapper CREATE_DEVELOPER_MAPPER = new CreateCompanyMapper();

    private CreateCompanyMapper() {}

    public static CreateCompanyMapper getInstance() {
        return CREATE_DEVELOPER_MAPPER;
    }

    @Override
    public Company mapFrom(CreateCompanyDto companyDto) {
        return Company.builder()
                .name(Formatter.capitalize(companyDto.getName()))
                .country(companyDto.getCountry())
                .build();
    }

    @Override
    public CreateCompanyDto mapTo(Company object) {
        return null;
    }
}
