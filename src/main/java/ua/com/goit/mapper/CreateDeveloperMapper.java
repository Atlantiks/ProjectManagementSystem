package ua.com.goit.mapper;

import ua.com.goit.Formatter;
import ua.com.goit.dto.CreateDeveloperDto;
import ua.com.goit.entity.Developer;

import java.math.BigDecimal;

public class CreateDeveloperMapper implements Mapper<CreateDeveloperDto, Developer> {
    private static final CreateDeveloperMapper CREATE_DEVELOPER_MAPPER = new CreateDeveloperMapper();

    private CreateDeveloperMapper() {}

    public static CreateDeveloperMapper getInstance() {
        return CREATE_DEVELOPER_MAPPER;
    }

    @Override
    public Developer mapFrom(CreateDeveloperDto developerDto) {
        return Developer.builder()
                .firstName(Formatter.capitalize(developerDto.getFirstName()))
                .lastName(Formatter.capitalize(developerDto.getLastName()))
                .sex(developerDto.getSex())
                .salary(BigDecimal.valueOf(Double.parseDouble(developerDto.getSalary())))
                .build();
    }

    @Override
    public CreateDeveloperDto mapTo(Developer developer) {
        return null;
    }
}
