drop table IF EXISTS gwt.items;
drop table IF EXISTS gwt.urls;
drop table IF EXISTS gwt.mails;
CREATE TABLE gwt.mails (
  id SERIAL PRIMARY KEY,
  url varchar(50),
  name varchar(50)
);

CREATE TABLE gwt.urls (
  id SERIAL PRIMARY KEY,
  mails_id INTEGER NOT NULL references gwt.mails(id),
  url varchar(100),
  schedule varchar(50),
  lastpub date,
  is_active char(1),
  laststart date
);
CREATE TABLE gwt.items (
  id SERIAL PRIMARY KEY,
  mails_id INTEGER NOT NULL references gwt.mails(id),
  urls_id INTEGER NOT NULL references gwt.urls(id),
  title varchar(50),
  is_active char(1)
);
drop table IF EXISTS gwt.log;
CREATE TABLE gwt.log (
  id SERIAL PRIMARY KEY,
  grp varchar(50) NOT NULL,
  item varchar(50) NOT NULL,
  mess varchar(100),
  dt date NOT NULL
  );