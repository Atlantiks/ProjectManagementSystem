package ua.com.goit.mapper;

import ua.com.goit.dto.CreateProjectDto;
import ua.com.goit.entity.Project;

import java.time.LocalDate;

public class CreateProjectMapper implements Mapper<CreateProjectDto, Project> {
    private static final CreateProjectMapper CREATE_PROJECT_MAPPER = new CreateProjectMapper();

    private CreateProjectMapper() {}

    public static CreateProjectMapper getInstance() {
        return CREATE_PROJECT_MAPPER;
    }

    @Override
    public Project mapFrom(CreateProjectDto projectDto) {
        return Project.builder()
                .name(projectDto.getName())
                .date_created(LocalDate.parse(projectDto.getDate()))
                .description(projectDto.getDescription())
                .status(projectDto.getStatus())
                .build();
    }

    @Override
    public CreateProjectDto mapTo(Project object) {
        return null;
    }
}
