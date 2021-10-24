CREATE TABLE COUPLE
(
    COUPLE_ID                    bigserial primary key,
    NICK_NAME                    VARCHAR(50) unique,
    WEDDING_DAY                  date,
    DECLARATION_DATE_OF_MARRIAGE date,
    RELATED_DATE_TIME            timestamp,
    UPDATED_DATE_TIME            timestamp
);

CREATE TABLE MEMBER
(
    MEMBER_ID          bigserial primary key,
    EMAIL              varchar(100) unique not null,
    FIRST_NAME         varchar(30)         not null,
    LAST_NAME          varchar(30)         not null,
    SEX                varchar(10)         not null,
    PASSWORD           VARCHAR(256)        not null,
    COUPLE_ID          bigint,
    ROLE               varchar(10)         not null,
    IS_ACTIVE          bool                not null,
    ENROLLED_DATE_TIME timestamp           not null,
    UPDATED_DATE_TIME  timestamp           not null
);
