package ua.com.goit.mapper;

import ua.com.goit.dto.UpdateProjectDto;
import ua.com.goit.entity.Project;

import java.time.LocalDate;

public class UpdateProjectMapper implements Mapper<UpdateProjectDto, Project> {
    private static final UpdateProjectMapper UPDATE_PROJECT_MAPPER = new UpdateProjectMapper();

    private UpdateProjectMapper() {}

    public static UpdateProjectMapper getInstance() {
        return UPDATE_PROJECT_MAPPER;
    }

    @Override
    public Project mapFrom(UpdateProjectDto projectDto) {
        return Project.builder()
                .id(Integer.parseInt(projectDto.getId()))
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .status(projectDto.getStatus())
                .date_created(LocalDate.parse(projectDto.getDate()))
                .build();
    }

    @Override
    public UpdateProjectDto mapTo(Project project) {
        return UpdateProjectDto.builder()
                .id(project.getId().toString())
                .name(project.getName())
                .description(project.getDescription())
                .date(project.getDate_created().toString())
                .status(project.getStatus())
                .build();
    }
}
