package ua.com.goit.mapper;

import ua.com.goit.dto.FindDeveloperDto;
import ua.com.goit.entity.Developer;
import ua.com.goit.service.CompanyService;

public class FindDeveloperMapper implements Mapper<FindDeveloperDto, Developer> {
    private static final FindDeveloperMapper FIND_DEVELOPER_MAPPER = new FindDeveloperMapper();
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    private FindDeveloperMapper() {}

    public static FindDeveloperMapper getInstance() {
        return FIND_DEVELOPER_MAPPER;
    }

    @Override
    public Developer mapFrom(FindDeveloperDto object) {
        return null;
    }

    @Override
    public FindDeveloperDto mapTo(Developer developer) {
        String devCompanyName = "";
        try {
            var company = COMPANY_SERVICE.findCompanyById(developer.getCompanyId().toString());
            devCompanyName = company.getName();
        } catch (NullPointerException e) {
            devCompanyName = "none";
        }

        return FindDeveloperDto.builder()
                .firstName(developer.getFirstName())
                .lastName(developer.getLastName())
                .sex(developer.getSex())
                .companyName(devCompanyName)
                .build();
    }
}
