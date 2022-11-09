package ua.com.goit.mapper;

import ua.com.goit.dto.CreateDeveloperDto;
import ua.com.goit.entity.Developer;

public class CreateDeveloperMapper implements Mapper<CreateDeveloperDto, Developer> {

    @Override
    public Developer mapFrom(CreateDeveloperDto object) {
        return null;
    }

    @Override
    public CreateDeveloperDto mapTo(Developer object) {
        return null;
    }
}
