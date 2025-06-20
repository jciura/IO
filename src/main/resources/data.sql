INSERT INTO software (id, name) VALUES
                                    (0, 'Eclipse'),
                                    (1, 'IntelliJ IDEA'),
                                    (2, 'VS Code');

INSERT INTO classrooms (id, building, number, floor, capacity, has_computers) VALUES
                                                                                  (100, 'A1', 101, 1, 20, True),
                                                                                  (101, 'A1', 102, 1, 40, False),
                                                                                  (102, 'A1', 201, 2, 15, True),
                                                                                  (103, 'B2', 102, 1, 30, False),
                                                                                  (104, 'B2', 304, 3, 30, True),
                                                                                  (105, 'A2', 201, 2, 200, False);

INSERT INTO classroom_software (software_id, classroom_id) VALUES
                                                               (0, 100),
                                                               (0, 102),
                                                               (1, 104),
                                                               (2, 102),
                                                               (2, 104);

INSERT INTO users (id, first_name, last_name, email, phone_number, role, password) VALUES
    (0, 'Admin', 'Administer', 'administer@agh.edu.pl', '+48123456789', 0, '$2a$10$OLrm7QW9suopMoSgArU5eutobWG3PZbqxW0TSCs.sGO5J7T.xxcA2'); -- passwords needs to be hashed!
insert into users (id, first_name, last_name, email, phone_number, role, password) values (1, 'Lynn', 'Pellissier', 'lpellissier0@marketwatch.com', '+86 410 770 4431', 1, '$2a$10$kDtOd5y4iKTaj4nyaRvoyuU0EyV2THAF2w7y0Kl2/h4Uj35uGVJsC');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (2, 'Orelia', 'Sorrel', 'osorrel1@trellian.com', '+30 981 763 2428', 1, '$2a$04$sL2DE283jD5YITf0nH3TKuRFlMD.IdFXoocGgn2TKkckmxzpeubUW');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (3, 'Tanitansy', 'Caulder', 'tcaulder2@sogou.com', '+381 545 605 2118', 1, '$2a$04$r77FmAGgpJQaCkCMVIC.FOHThfAy8WywadLotw2NHq0d.q9nXt0Km');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (4, 'Roxi', 'Kalisz', 'rkalisz3@toplist.cz', '+86 407 870 9009', 1, '$2a$04$lPEMWoipXiLAv2tBI/4d0uHe0OB8z9RXW.X8hzmcPdPhQy4GcEGYi');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (5, 'Benoite', 'Moroney', 'bmoroney4@sakura.ne.jp', '+855 775 432 0762', 3, '$2a$04$Ud/0X3WaP0aK8pHHwlT6OeT01MbvzKnw4XFBKhdIZrqb7oU7F8CJ.');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (6, 'Aldis', 'Hollingby', 'ahollingby5@mapquest.com', '+63 841 743 6017', 1, '$2a$04$JESOIDJRtcdh5tij1pc8puegrAnBuUt0lvJMYaMHCNRCY46uJC9BO');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (7, 'Robin', 'Ducker', 'rducker6@simplemachines.org', '+351 686 205 2056', 3, '$2a$04$fcdkMvXehNmx/SPExE31WeEo/uI9DJ/eXShfri1O8uHAd63aDS0A6');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (8, 'Joana', 'Myhan', 'jmyhan7@patch.com', '+225 399 326 2596', 1, '$2a$04$KUxlSt56jCQcZ43t2AneLuTL9j1XTS3h38dWmMZDe3YF5uueU5HyG');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (9, 'Waly', 'Lyster', 'wlyster8@desdev.cn', '+1 504 520 0747', 1, '$2a$04$VC0R7L6h7Ss9oS37m2J32eYSj0VwpWYnMzGdwx6HRpn4Ek2dm1.fy');
insert into users (id, first_name, last_name, email, phone_number, role, password)
values (10, 'Agathe', 'Willox', 'awillox9@sakura.ne.jp', '+86 979 771 3845', 3,
        '$2a$10$cYdF8yvfJE6QT7sQUYpwwOjMkqjepRG38TNesJ7.5YpqTIdeaE.LS'); -- hasło: starosta
insert into users (id, first_name, last_name, email, phone_number, role, password) values (11, 'Karlens', 'Langworthy', 'klangworthya@cdc.gov', '+62 630 413 8534', 3, '$2a$04$cTNTLHF9NPVA7jeNGmwFaOzNhhTsF.rejTRbT.ABhTwXDJze2QZ0K');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (12, 'Tobin', 'Ellicott', 'tellicottb@etsy.com', '+63 459 810 8275', 2, '$2a$10$Xmtfs/13iQjz/on1BciczuIKUx4lDqNI0yavdki7tGT3uAk0H/s3.');  -- hasło: koord
insert into users (id, first_name, last_name, email, phone_number, role, password) values (13, 'Mischa', 'Moquin', 'mmoquinc@goodreads.com', '+225 821 991 0495', 3, '$2a$04$uXX9of.z4YqAnmkHg2liv.vuc5IvdIEz6dJQg04ojN0d2RZIUr6Xu');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (14, 'Engelbert', 'Bellward', 'ebellwardd@vimeo.com', '+48 630 387 6984', 3, '$2a$04$t6SxwMZKuzd43eBvc.CBhOTuuwW4hd/M33yJPBbQKqQJ1mbl4t94O');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (15, 'Birk', 'Brunesco', 'bbrunescoe@nasa.gov', '+62 549 535 8837', 1, '$2a$04$YiiQrLKSCgfHdY6C0fEyfedP01DBcTE/KADvVb.0ERo.koN1xeuem');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (16, 'Culley', 'Chantree', 'cchantreef@myspace.com', '+86 464 550 9634', 3, '$2a$04$fBJgWL/IZVcG9aqc.fzyq.t5fOIJBKMs3t7nbTCh4teEBIbxugaAu');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (17, 'Minetta', 'Harragin', 'mharraging@walmart.com', '+58 671 836 6143', 1, '$2a$04$Xj0sPieLtzeMTrIB8rxnS.wS3Gtzu3oF7ikxZvV1ysXgs4eJCn1Fy');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (18, 'Naoma', 'Agate', 'nagateh@people.com.cn', '+7 719 297 3453', 3, '$2a$04$1AmUMXNHsJpNl0KI6ivrd.RzCVjNZjzEVyP8e/DnCa0fCtVzdg9xi');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (19, 'Frans', 'Seeger', 'fseegeri@artisteer.com', '+420 820 641 2918', 1, '$2a$04$0scwYqn8FFgnMMW0U9nE6uOZwH9z5HIcs8RYCpPPPQefFYGcv0Lse');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (20, 'Donn', 'Bouzan', 'dbouzanj@goodreads.com', '+256 398 456 7310', 2, '$2a$04$Dy6zEAw6wtvw2AAd9.Ib5uI9rlW7XXIY2zaVTzVAdJEHYIjk7dSwO');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (21, 'test', 'testowy', 'test@gmail.com', '+48 123 456 789', 1, '$2a$10$kDtOd5y4iKTaj4nyaRvoyuU0EyV2THAF2w7y0Kl2/h4Uj35uGVJsC');  -- hasło: test

INSERT INTO courses (id, lecturer_id, representative_id, name, regular_day_of_week)
VALUES (0, 1, 5, 'Biologia molekularna', 'FRIDAY'),
       (1, 2, 7, 'Karboniki wschodnie', 'FRIDAY'),
       (2, 3, 5, 'Czarne dziury w niewidzialnym wszechświecie', 'FRIDAY'),
       (3, 21, 16, 'ABOBA', 'FRIDAY'),
       (4, 19, 10, 'Zagadki niebieskiego sera', 'FRIDAY'),
       (5, 21, 10, 'Algebra', 'FRIDAY');

ALTER TABLE courses
    ALTER COLUMN id RESTART WITH 6;

ALTER TABLE Software
    ALTER COLUMN id RESTART WITH 3;

insert into classes (id, classroom_id, date_time, duration, course_id) values (1, 100, '2025-03-14 12:00:00', 65, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (2, 101, '2025-07-27 14:40:00', 86, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (3, 103, '2025-07-24 08:00:00', 83, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (4, 104, '2025-01-29 14:30:00', 86, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (5, 101, '2025-11-30 16:30:00', 63, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (6, 104, '2025-02-02 16:30:00', 71, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (7, 102, '2025-06-27 15:15:00', 70, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (8, 102, '2025-05-09 18:45:00', 62, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (9, 102, '2025-05-27 18:30:00', 90, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (10, 103, '2025-05-02 10:30:00', 68, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (11, 100, '2025-08-20 09:45:00', 79, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (12, 101, '2025-03-07 18:00:00', 89, 0);
insert into classes (id, classroom_id, date_time, duration, course_id) values (13, 100, '2025-07-09 15:50:00', 60, 3);
insert into classes (id, classroom_id, date_time, duration, course_id) values (14, 100, '2025-04-04 08:45:00', 63, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (15, 101, '2025-07-30 15:00:00', 73, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (16, 100, '2025-03-30 14:45:00', 85, 3);
insert into classes (id, classroom_id, date_time, duration, course_id) values (17, 103, '2025-07-25 11:00:00', 89, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (18, 100, '2025-06-02 13:00:00', 73, 5);
insert into classes (id, classroom_id, date_time, duration, course_id) values (19, 103, '2025-06-20 10:30:00', 65, 5);
insert into classes (id, classroom_id, date_time, duration, course_id) values (20, 103, '2025-07-31 18:00:00', 89, 5);
insert into classes (id, classroom_id, date_time, duration, course_id) values (21, 103, '2025-06-27 10:30:00', 65, 5);
insert into classes (id, classroom_id, date_time, duration, course_id)
values (22, 103, '2025-06-20 13:00:00', 65, 4);
insert into classes (id, classroom_id, date_time, duration, course_id)
values (23, 103, '2025-07-04 10:30:00', 65, 5);
insert into classes (id, classroom_id, date_time, duration, course_id)
values (24, 103, '2025-07-11 10:30:00', 65, 5);
