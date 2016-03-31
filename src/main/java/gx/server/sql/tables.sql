CREATE SCHEMA rolo;
CREATE TABLE rolo.users (
  id SERIAL PRIMARY KEY,
  name varchar(30) NOT NULL,
  pass varchar(20) NOT NULL,
  mail varchar(50)
);
CREATE TABLE rolo.roles (
  id SERIAL PRIMARY KEY,
  name varchar(30) NOT NULL
 );
 CREATE TABLE rolo.urro (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL references rolo.users(id),
  role_id INTEGER NOT NULL references rolo.roles(id)
 );
 CREATE TABLE rolo.log (
  id SERIAL PRIMARY KEY,
  grp varchar(50) NOT NULL,
  item varchar(50) NOT NULL,
  mess varchar(100),
  dt date NOT NULL
  );
--------- 
 CREATE SCHEMA rss;
CREATE TABLE rss.mails (
  id SERIAL PRIMARY KEY,
  url varchar(50),
  name varchar(50),
  user_id INTEGER NOT NULL references rolo.users(id)
);

CREATE TABLE rss.urls (
  id SERIAL PRIMARY KEY,
  mails_id INTEGER NOT NULL references rss.mails(id),
  url varchar(100),
  schedule varchar(50),
  lastpub date,
  is_active char(1),
  laststart date
);
CREATE TABLE rss.items (
  id SERIAL PRIMARY KEY,
  mails_id INTEGER NOT NULL references rss.mails(id),
  urls_id INTEGER NOT NULL references rss.urls(id),
  title varchar(50),
  is_active char(1)
);
