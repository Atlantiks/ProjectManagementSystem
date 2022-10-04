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
    sex        CHAR(1) NOT NULL CHECK (sex IN ('M', 'F')),
    company_id INT     REFERENCES companies (id) ON DELETE SET NULL,
    salary     NUMERIC,
    UNIQUE (first_name, last_name)
);

CREATE TABLE skills
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR NOT NULL,
    level VARCHAR NOT NULL,
    UNIQUE (name, level)
);

CREATE TABLE projects
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR UNIQUE NOT NULL,
    date_created DATE DEFAULT now(),
    description  TEXT,
    status       TEXT CHECK (status IN ('Active', 'Inactive', 'Discontinued', 'Not commissioned')),
    cost         NUMERIC
);

CREATE TABLE customers
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    company    VARCHAR,
    address    TEXT,
    UNIQUE (first_name, last_name, company)
);

CREATE TABLE developers_skills
(
    developers_id INT REFERENCES developers (id) ON DELETE CASCADE,
    skill_id      INT REFERENCES skills (id) ON DELETE CASCADE,
    UNIQUE (developers_id, skill_id)
);

CREATE TABLE projects_developers
(
    projects_id   INT REFERENCES projects (id) ON DELETE CASCADE,
    developers_id INT REFERENCES developers (id) ON DELETE CASCADE,
    UNIQUE (projects_id, developers_id)
);

CREATE TABLE companies_projects
(
    companies_id INT REFERENCES companies (id) ON DELETE CASCADE,
    projects_id  INT REFERENCES projects (id) ON DELETE CASCADE UNIQUE
);

CREATE TABLE customers_projects
(
    customers_id INT REFERENCES customers (id) ON DELETE CASCADE,
    projects_id  INT REFERENCES projects (id) ON DELETE CASCADE,
    UNIQUE (customers_id, projects_id)
);
