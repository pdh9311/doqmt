drop table if exists document;
drop table if exists book;
drop table if exists member;

create table if not exists member (
    member_id     bigint       not null auto_increment,
    username      varchar(255) not null,
    email         varchar(255) not null,
    password      varchar(255) not null,
    profile_image longtext,
    created_time  datetime(6)  not null,
    updated_time  datetime(6)  not null,
    primary key (member_id)
);

create table if not exists book (
    book_id      bigint       not null auto_increment,
    idx          bigint  default 0,
    name         varchar(255) not null,
    is_deleted   boolean default false,
    created_time datetime(6)  not null,
    updated_time datetime(6)  not null,
    member_id    bigint       not null,
    primary key (book_id),
    foreign key (member_id) references member (member_id)
);

create table if not exists document (
    document_id  bigint       not null auto_increment,
    idx          bigint  default 0,
    title        varchar(255) not null,
    filename     varchar(255) not null,
    is_deleted   boolean default false,
    hits         bigint  default 0,
    created_time datetime(6)  not null,
    updated_time datetime(6)  not null,
    book_id      bigint       not null,
    primary key (document_id),
    foreign key (book_id) references book (book_id)
);
