package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.view.View;

import java.util.List;

@RequiredArgsConstructor
public class GetDevelopersWithSkillName implements Command {
    public static final String GET_DEVS_WITH_SKILL = "get developers with knowledge of";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @NonNull
    private View view;



    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_WITH_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter skill name, for example Java, Python, C++ etc");
        String userInput = view.read();
        view.write(String.format("Список всех разработчиков cо знанием %s\n", "\033[0;93m" + userInput + "\033[0m"));

        SKILL_DAO.getDevelopersWithSkillName(userInput).forEach(System.out::println);

        view.write("");
        view.write(String.format("Please enter next command or type %s to see available list\n", "\033[0;93m" + "help" + "\033[0m"));
    }
}
