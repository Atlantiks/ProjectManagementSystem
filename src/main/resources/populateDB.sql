INSERT INTO companies (name, country)
VALUES ('Apple', 'United States'),
       ('Google', 'United States'),
       ('Oracle', 'United States'),
       ('Schneider Electric', 'France'),
       ('Kongsberg Software DevDep', 'Norway'),
       ('ABB', 'Swiss'),
       ('Symantec', 'United States');

INSERT INTO projects (name, date_created, description, status)
VALUES ('iOS Development', '2002-01-01','develops operating system for iPhones, iPads', 'Active'),
       ('OS X Development','2002-01-01', 'develops operating system for iMacs, MacBooks', 'Discontinued'),
       ('Mac OS Development','2002-01-01', 'develops operating system for iPhones, iPads', 'Active'),
       ('Android OS Development','2002-01-01', 'develops operating system for all android-run phones', 'Active'),
       ('Google Search Website','2002-01-01', 'develops google.com search engine', 'Active'),
       ('Google Talk Development','2002-01-01', 'develops voice and text messenger', 'Discontinued'),
       ('Google Techs Development','2002-01-01', 'develops some technical software', 'Discontinued'),
       ('NORCONTROL Development','2002-01-01', 'develops integrated monitoring alarming and controlling system for ships',
        'Inactive'),
       ('Simos IMAC','2002-01-01', 'develops integrated monitoring alarming and controlling system for ships', 'Active'),
       ('Kongsberg DP3 Development', '2002-01-01','develops dynamical positioning system (DP) for ships', 'Active'),
       ('NS5 Planned Management System', '2002-01-01','develops multipurpose maintenance plan, control of its execution etc for power plants', 'Active');

INSERT INTO projects (name, description, status)
VALUES ('GOIT.ua Java DEveloper 6','develops web pet project', 'Not commissioned');

INSERT INTO companies_projects
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (5, 8),
       (5, 9),
       (6, 10);

INSERT INTO skills (name, level)
VALUES ('Java', 'Junior'),
       ('Java', 'Middle'),
       ('Java', 'Senior'),
       ('C++', 'Junior'),
       ('C++', 'Middle'),
       ('C++', 'Senior'),
       ('C#', 'Junior'),
       ('C#', 'Middle'),
       ('C#', 'Senior'),
       ('Javascript', 'Junior'),
       ('Javascript', 'Middle'),
       ('Javascript', 'Senior'),
       ('Scala', 'Junior'),
       ('Scala', 'Middle'),
       ('Scala', 'Senior'),
       ('Groovy', 'Junior'),
       ('Groovy', 'Middle'),
       ('Groovy', 'Senior'),
       ('Python', 'Junior'),
       ('Python', 'Middle'),
       ('Python', 'Senior');

INSERT INTO developers (first_name, last_name, sex, company_id)
VALUES ('Ivan', 'Petrov', 'M', NULL),
       ('Stephen', 'McCallins', 'M', NULL),
       ('Brennan', 'Richards', 'M', 5),
       ('Maddox', 'Weiss', 'M', 2),
       ('Steve', 'Jobs', 'M', 1),
       ('John', 'Gibbs', 'M', 1),
       ('Peter', 'Jeningston', 'M', 1),
       ('Matteo', 'Baggio', 'M', 1),
       ('Sergii', 'Brin', 'M', 5),
       ('Svetlana', 'Kravetz', 'F', 5),
       ('Magnus', 'Jones', 'M', 5),
       ('Lennon', 'Watson', 'M', 4),
       ('Max', 'Miller', 'M', 4),
       ('Sylvie ', 'Smith', 'F', 4),
       ('Vanessa ', 'Riley', 'F', 4),
       ('Astrid', 'Harris', 'F', 1),
       ('Brooklyn', 'Gray', 'F', 6),
       ('Elis', 'Jones', 'M', 5),
       ('Barnaby', 'Taylor', 'M', 3),
       ('Maisie', 'Pearce', 'F', 6),
       ('Albert', 'Shaw', 'M', 2),
       ('Callie', 'White', 'F', 5),
       ('Elliot', 'Campbell', 'M', 1),
       ('Paul', 'Saunders', 'M', 6),
       ('Alen', 'Dallas', 'M', 6),
       ('Dean', 'Saunders', 'M', 7),
       ('Kevin', 'Law', 'M', 7),
       ('John', 'Philips', 'M', 7),
       ('Galina', 'Blanka', 'F', 7);

INSERT INTO developers_skills (developers_id, skill_id)
VALUES (1, 1),
       (1, 11),
       (2, 20),
       (2, 9),
       (2, 11),
       (3, 4),
       (3, 1),
       (3, 21),
       (4, 21),
       (4, 15),
       (5, 18),
       (6, 3),
       (7, 13);

INSERT INTO projects_developers (projects_id, developers_id)
VALUES (1, 5),(1, 6),(1, 7),
       (5, 4),(5, 21),
       (2, 5),
       (3, 5),
       (8, 3),(8, 9),(8, 10),(8, 22),
       (9, 24),(9, 25),
       (10,3),(10,9),(10,10),(10,11);

INSERT INTO customers (first_name, last_name, address)
VALUES ('John','Carter','USA 401 Highridge Cv Killeen, Texas(TX), 76543'),
       ('Carl','Lohnbee', 'USA 25 Nut Swamp St. Highland, IN 46322'),
       ('Walter','White', 'USA 40 Water Road Ballston Spa, NY 12020 '),
       ('Jonathan','Donnet', 'Switzerland Uznach platz 3a 4016 Les Cl??es'),
       ('Emilien','Dupuy', 'Switzerland Le Landeron str 66 6305 Sarnen'),
       ('Levin','Dolder', 'Switzerland Schwarzring 4a 6959 Ilanz');

INSERT INTO customers_projects (customers_id, projects_id)
VALUES (3, 5),
       (5, 6),
       (6, 10),
       (2, 4),
       (4, 8),
       (4, 10);

UPDATE developers
SET salary = 650 + round((random() * 3350)::numeric, 2);

UPDATE projects p
SET cost = total
FROM  (SELECT p.id, sum(d.salary) total
            FROM developers d
            JOIN projects_developers pd on d.id = pd.developers_id
            JOIN projects p on pd.projects_id = p.id
            GROUP BY p.id) AS result
WHERE p.id = result.id;
