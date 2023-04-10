CREATE TABLE IF NOT EXISTS twitter_analytics
(
    id          BINARY(16)   not null,
    word        varchar(255) not null,
    word_count  integer      not null,
    record_date datetime,
    CONSTRAINT twitter_analytics_pkey PRIMARY KEY (id)
);
