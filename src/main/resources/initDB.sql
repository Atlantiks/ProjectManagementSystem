CREATE TABLE companies
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(128) NOT NULL UNIQUE,
    country VARCHAR(128)
);

CREATE TABLE developers
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    sex        CHAR(1) NOT NULL,
    company_id INT REFERENCES companies(id),
    UNIQUE (first_name, last_name)
);

CREATE TABLE skills
(
    id    SERIAL,
    name  VARCHAR NOT NULL,
    level VARCHAR NOT NULL,
    PRIMARY KEY (name, level),
    UNIQUE (id)
);

CREATE TABLE projects
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR UNIQUE,
    description TEXT,
    status      TEXT CHECK (status IN ('Active', 'Inactive', 'Discontinued'))
);

CREATE TABLE customers
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    company VARCHAR,
    address TEXT,
    UNIQUE (first_name, last_name, company)
);

CREATE TABLE developers_skills
(
    developers_id INT REFERENCES developers (id),
    skill_name TEXT,
    skill_level TEXT,
    FOREIGN KEY (skill_name, skill_level) REFERENCES skills(name, level),
    UNIQUE (developers_id,skill_name)
);

CREATE TABLE projects_developers
(
    projects_id   INT REFERENCES projects (id),
    developers_id INT REFERENCES developers (id)
);

CREATE TABLE companies_projects
(
    companies_id INT REFERENCES companies (id),
    projects_id  INT REFERENCES projects (id) UNIQUE
);

CREATE TABLE customers_projects
(
    customers_id INT REFERENCES customers (id),
    projects_id  INT REFERENCES projects (id)
);