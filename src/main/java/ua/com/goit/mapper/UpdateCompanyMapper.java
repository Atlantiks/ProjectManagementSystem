package ua.com.goit.mapper;

import ua.com.goit.dto.UpdateCompanyDto;
import ua.com.goit.entity.Company;

public class UpdateCompanyMapper implements Mapper<UpdateCompanyDto, Company> {
    private static final UpdateCompanyMapper UPDATE_COMPANY_MAPPER = new UpdateCompanyMapper();

    private UpdateCompanyMapper() {}

    public static UpdateCompanyMapper getInstance() {
        return UPDATE_COMPANY_MAPPER;
    }

    @Override
    public Company mapFrom(UpdateCompanyDto companyDto) {
        return Company.builder()
                .id(Integer.parseInt(companyDto.getId()))
                .name(companyDto.getName())
                .country(companyDto.getCountry())
                .build();
    }

    @Override
    public UpdateCompanyDto mapTo(Company company) {
        return UpdateCompanyDto.builder()
                .id(company.getId().toString())
                .name(company.getName())
                .country(company.getCountry())
                .build();
    }
}
