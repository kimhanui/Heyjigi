set foreign_key_checks =0;
insert into CATEGORY(category_enum) values('ARDUINO');
insert into CATEGORY(category_enum) values('SPORTS');
insert into CATEGORY(category_enum) values('EXHIBITION');
insert into CATEGORY(category_enum) values('STUDY');

insert into POST(CREATED_DATE_TIME, MODIFIED_DATETIME,  CATEGORY_ID, CONTENT, END_DATE, PERSON_LIMIT, TITLE, HOST_ID) values('2020-11-18T18:00:00','2020-11-18T18:00:00', 1L,'모이세요','2020-11-30', 5,'부품공구합시다',1L);
insert into POST(CREATED_DATE_TIME, MODIFIED_DATETIME,  CATEGORY_ID, CONTENT, END_DATE, PERSON_LIMIT, TITLE, HOST_ID) values('2020-11-18T18:00:00','2020-11-18T18:00:00', 1L,'모이세요2','2020-11-30', 5,'부품공구합시다2',2L);
insert into POST(CREATED_DATE_TIME, MODIFIED_DATETIME,  CATEGORY_ID, CONTENT, END_DATE, PERSON_LIMIT, TITLE, HOST_ID) values('2020-11-18T18:00:00','2020-11-18T18:00:00', 2L,'모이세요','2020-11-30', 5,'족구합시다',3L );
insert into POST(CREATED_DATE_TIME, MODIFIED_DATETIME,  CATEGORY_ID, CONTENT, END_DATE, PERSON_LIMIT, TITLE, HOST_ID) values('2020-11-18T18:00:00','2020-11-18T18:00:00', 3L,'모이세요','2020-11-30', 5,'코엑스갑시다',4L );
insert into POST(CREATED_DATE_TIME, MODIFIED_DATETIME,  CATEGORY_ID, CONTENT, END_DATE, PERSON_LIMIT, TITLE, HOST_ID) values('2020-11-18T18:00:00','2020-11-18T18:00:00', 4L,'모이세요','2020-11-30', 5,'스터디합시다',5L );

-- 학번 반드시 다르게
insert into USER(created_date_time, modified_datetime, PROFILE_IMAGE,OAUTH_ID,EMAIL, name, student_id) values ('2020-11-18T18:00:00','2020-11-18T18:00:00','/images/hapshida.jpg','1500000000','lee@gmail.com','아두이','170170');
insert into USER(created_date_time, modified_datetime, PROFILE_IMAGE,OAUTH_ID,EMAIL, name, student_id) values ('2020-11-18T18:00:00','2020-11-18T18:00:00','/images/hapshida.jpg','1500000001','kim@gmail.com','아두김','180180');
insert into USER(created_date_time, modified_datetime, PROFILE_IMAGE,OAUTH_ID,EMAIL, name, student_id) values ('2020-11-18T18:00:00','2020-11-18T18:00:00','/images/hapshida.jpg','1500000002','kim@gmail.com','스포김','190190');
insert into USER(created_date_time, modified_datetime, PROFILE_IMAGE,OAUTH_ID,EMAIL, name, student_id) values ('2020-11-18T18:00:00','2020-11-18T18:00:00','/images/hapshida.jpg','1500000003','kim@gmail.com','익스김','200200');
insert into USER(created_date_time, modified_datetime, PROFILE_IMAGE,OAUTH_ID,EMAIL, name, student_id) values ('2020-11-18T18:00:00','2020-11-18T18:00:00','/images/hapshida.jpg','1500000004','kim@gmail.com','스터김','210210');

-- user(guest)-post 다대다
insert into user_post(user_id, post_id) values(1L, 1L);
insert into user_post(user_id, post_id) values(1L, 2L);
insert into user_post(user_id, post_id) values(2L, 3L);
insert into user_post(user_id, post_id) values(2L, 4L);

-- user-category 다대다
insert into user_category(user_id,category_id) values(1L, 1L);
insert into user_category(user_id,category_id) values(1L, 2L);
insert into user_category(user_id,category_id) values(2L, 2L);
insert into user_category(user_id,category_id) values(3L, 3L);
insert into user_category(user_id,category_id) values(4L, 4L);
set foreign_key_checks =1;