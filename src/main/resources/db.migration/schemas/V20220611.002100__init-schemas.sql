create sequence if not exists accounts_seq increment by 10;
create table if not exists accounts
(
    id           bigint not null,
    email        varchar(100) NOT NULL,
    password     varchar(255) NOT NULL,
    status       varchar(10) NOT NULL DEFAULT 'ACTIVE',
    username     varchar(100) NOT NULL,
    created_by   varchar(255),
    created_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
    updated_by   varchar(255),
    updated_time timestamp not null DEFAULT CURRENT_TIMESTAMP,
    constraint accounts_pkey primary key (id),
    constraint uk_account_email unique (email),
    constraint uk_account_username unique (username)
);

create sequence if not exists roles_seq increment by 10;
create table if not exists roles
(
    id          bigint not null,
    description text,
    name        varchar(50),
    is_system      boolean not null default true,
    constraint roles_pkey primary key (id),
    constraint uk_role_name unique (name)
);

create sequence if not exists accounts_roles_seq increment by 10;
create table if not exists accounts_roles
(
    id bigint       not null,
    account_id      bigint not null,
    role_id         bigint not null,
    constraint      accounts_roles_pkey primary key (id),
    constraint      fk_accounts_roles_role_id foreign key (role_id) references roles,
    constraint      fk_accounts_roles_account_id foreign key (account_id) references accounts
);

create sequence if not exists profiles_seq increment by 10;
create table if not exists profiles
(
    id              bigint not null,
    avatar_url      text,
    email           varchar(100),
    first_name      varchar(255),
    last_name       varchar(255),
    username        varchar(100),
    account_id      bigint not null,
    created_by      varchar(255),
    created_time    timestamp not null DEFAULT CURRENT_TIMESTAMP,
    updated_by      varchar(255),
    updated_time    timestamp not null DEFAULT CURRENT_TIMESTAMP,
    constraint      users_pkey primary key (id),
    constraint      uk_user_email unique (email),
    constraint      uk_user_username unique (username),
    constraint      fk_users_accounts_account_id foreign key (account_id) references accounts
);