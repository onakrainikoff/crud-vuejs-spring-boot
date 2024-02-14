create table stored_objects (
    id serial,
    date_created timestamptz not null,
    date_updated timestamptz not null,
    name varchar(1000) not null,
    value text
);
create unique index udx_stored_objects_name on stored_objects (name);

create index idx_stored_objects_date_created on stored_objects (date_created);
create index idx_stored_objects_date_updated on stored_objects (date_updated);
insert into stored_objects (date_created, date_updated, name, value) values(now(), now(), 'OBJECT-1', 'My test OBJECT-1 value');
insert into stored_objects (date_created, date_updated, name, value) values(now(), now(), 'OBJECT-2', 'My test OBJECT-2 value');
insert into stored_objects (date_created, date_updated, name, value) values(now(), now(), 'OBJECT-3', 'My test OBJECT-3 value');