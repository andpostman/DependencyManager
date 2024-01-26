create schema if not exists project;

create table if not exists project.subscriber
(
    project text primary key,
    time timestamp not null ,
    master text not null ,
    version text not null
);

create table if not exists project.event
(
    project text ,
    version text ,
    time timestamp not null ,
    state bool not null ,
    primary key (project,version)
);

create unique index if not exists "subscriber_id_idx" on project.subscriber using btree(project);
create unique index if not exists "event_id_idx" on project.event using btree(project,version);
