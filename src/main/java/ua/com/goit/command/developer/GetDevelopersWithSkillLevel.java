package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetDevelopersWithSkillLevel implements Command {
    public static final String GET_DEVS_WITH_SKILL = "get developers with skill level";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_WITH_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        System.out.println("Please enter skill level, for example junior, middle, senior etc");
        String userInput = view.read();
        System.out.printf("\nСписок всех разработчиков уровня %s\n", "\033[0;92m" + userInput + "\033[0m");
        var y = SKILL_DAO.getDevelopersWithSkillLevel(userInput);
        y.forEach(System.out::println);
        System.out.println();
    }
}
