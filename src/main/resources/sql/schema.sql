show tables;

drop table if exists book;
drop table if exists member;

create table if not exists member
(
    member_id bigint not null,
    username  varchar(255),
    email     varchar(255),
    password  varchar(255),
    primary key (member_id)
);

create table if not exists book
(
    book_id bigint not null,
    name varchar(255),
    idx bigint default 0,
    isDel boolean,
    member_id bigint not null,
    primary key (book_id),
    foreign key (member_id) references member (member_id)
);


-- alter table if exists book
--     add foreign key(member_id) references member;

select * from member;
select * from book;
select * from document;

select * from member m
join book b on m.member_id = b.member_id
join document d on b.book_id = d.book_id
where d.is_deleted = true and m.member_id = 1
order by d.updated_time desc;