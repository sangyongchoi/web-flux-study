drop table if exists post;
create table post (
   id bigint generated by default as identity,
   content varchar(2000)
);