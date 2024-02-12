create table kv_objects (
    id serial,
    date_created timestamptz not null,
    date_updated timestamptz not null,
    key varchar(1000) not null,
    value text
);
create unique index udx_kv_objects_key on kv_objects (key);

create index idx_kv_objects_date_created on kv_objects (date_created);
create index idx_kv_objects_date_updated on kv_objects (date_updated);
insert into kv_objects (date_created, date_updated, key, value) values(now(), now(), 'OBJECT-1', 'My test OBJECT-1 value');
insert into kv_objects (date_created, date_updated, key, value) values(now(), now(), 'OBJECT-2', 'My test OBJECT-2 value');
insert into kv_objects (date_created, date_updated, key, value) values(now(), now(), 'OBJECT-3', 'My test OBJECT-3 value');