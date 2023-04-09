CREATE DATABASE IF NOT EXISTS analytics;

use analytics;

CREATE TABLE twitter_analytics
(
    id          varchar(36)  not null,
    word        varchar(255) not null,
    word_count  integer      not null,
    record_date datetime,
    CONSTRAINT twitter_analytics_pkey PRIMARY KEY (id)
);

