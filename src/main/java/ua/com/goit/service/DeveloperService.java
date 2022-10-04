package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;

import ua.com.goit.Formatter;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.DeveloperNotFound;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;


public class DeveloperService {
    private static final DeveloperService DEV_SERVICE = new DeveloperService();
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @Getter @Setter
    private View view;

    private DeveloperService() {
    }

    public static DeveloperService getInstance() {
        return DEV_SERVICE;
    }

    public void createDeveloper() {
            view.write("To create new developer you have to enter required fields and (optionally) non-required ones.");

            view.write("1. Please enter new Developer's name:");
            String firstName = view.read();
            if (firstName.isBlank()) {
                throw new BlancFieldException();
            } else {
                firstName = Formatter.capitalize(firstName);
            }
            view.write("2. Please enter new Developer's surname:");
            String lastName = view.read();
            if (lastName.isBlank()) {
                throw new BlancFieldException();
            } else {
                lastName = Formatter.capitalize(lastName);
            }
            view.write("3. Please enter new Developer's sex (M/F):");
            String sex = view.read();
            if (sex.isBlank()) {
                throw new BlancFieldException();
            } else {
                sex = Formatter.capitalize(sex).substring(0,1);
            }

            Developer newDev = new Developer(firstName, lastName, sex);

            view.write("Would you like to add non-required fields? Y/N  Press ENTER for default option(N)");

            String userAnswer = view.read();
            userAnswer = userAnswer.isBlank() ? "N" : userAnswer.substring(0, 1).toUpperCase();

            switch (userAnswer) {
                case "Y":
                    view.write("4. Please enter new Developer's company id:");
                    Integer companyId = Integer.parseInt(view.read());
                    view.write("5. Please enter new Developer's salary:");
                    BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(view.read()));

                    newDev.setCompanyId(companyId);
                    newDev.setSalary(salary);
                    break;
                case "N":
                    break;
                default:
                    view.write("Answer not recognised. Default parameters will be applied");
                    break;
            }

            Developer savedDev = DEV_DAO.save(newDev, view);

            if (Objects.nonNull(savedDev.getId())) {
                view.write("\033[0;92mThe following developer was successfully added to database:\033[0m");
                view.write(savedDev + "\n");
            }
    }

    public void deleteDeveloperById() {
        view.write("Please enter developer's id:");
        Integer devId = Integer.parseInt(view.read());

        if (DEV_DAO.removeById(devId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete developer with following Id = %d", devId ));
        }
    }

    public Developer findDeveloperById() {
        view.write("Please enter developer's id:");
        Integer devId = Integer.parseInt(view.read());

        return DEV_DAO.findById(devId).orElseThrow(() ->
                        new DeveloperNotFound(
                                String.format("\033[0;91mDeveloper with Id = %d wasn't found\033[0m", devId)));
    }

    public void getDevelopersWithSkillLevel() {
        view.write("Please enter skill level, for example junior, middle, senior etc");
        String userInput = Formatter.capitalize(view.read());

        System.out.printf("\nСписок всех разработчиков уровня \"%s\"\n", "\033[0;92m" + userInput + "\033[0m");
        SKILL_DAO.getDevelopersWithSkillLevel(userInput).forEach(System.out::println);
    }

    public void getDevelopersWithSkillName() {
        view.write("Please enter skill name, for example Java, Python, C++ etc");
        String userInput = Formatter.capitalize(view.read());
        view.write(String.format("Список всех разработчиков cо знанием %s\n", "\033[0;93m" + userInput + "\033[0m"));

        SKILL_DAO.getDevelopersWithSkillName(userInput).stream()
                .map(String::valueOf)
                .forEach(view::write);
    }
}
