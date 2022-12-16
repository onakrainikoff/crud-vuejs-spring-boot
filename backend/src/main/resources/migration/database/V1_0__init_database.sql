create table projects (
    id serial,
    date_created timestamptz not null,
    date_updated timestamptz not null,
    code varchar(50) not null,
    name varchar(100) not null,
    description varchar(250) not null
);
create unique index udx_projects_code on projects (code);
create index idx_projects_name on projects (name);
create index idx_projects_date_created on projects (date_created);
create index idx_projects_date_updated on projects (date_updated);
insert into projects (date_created, date_updated, code, name, description) values(now(), now(), 'PROJECT-1', 'My test project-1', 'My test project-1 description');
insert into projects (date_created, date_updated, code, name, description) values(now(), now(), 'PROJECT-2', 'My test project-2', 'My test project-2 description');
insert into projects (date_created, date_updated, code, name, description) values(now(), now(), 'PROJECT-3', 'My test project-3', 'My test project-3 description');