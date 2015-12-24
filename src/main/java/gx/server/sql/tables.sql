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
  laststart date
);
CREATE TABLE gwt.items (
  id SERIAL PRIMARY KEY,
  mails_id INTEGER NOT NULL references gwt.mails(id),
  urls_id INTEGER NOT NULL references gwt.urls(id),
  title varchar(50)
);
CREATE TABLE gwt.log (
  id SERIAL PRIMARY KEY,
  grp varchar(50) NOT NULL,
  item varchar(50) NOT NULL,
  mess varchar(100),
  update date NOT NULL
  );