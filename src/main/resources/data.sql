INSERT INTO software (id, name) VALUES
  (0, 'Eclipse'),
  (1, 'IntelliJ IDEA'),
  (2, 'VS Code');
  
INSERT INTO classrooms (id, building, number, floor, capacity, has_computers) VALUES
  (0, 'A1', 101, 1, 20, True),
  (1, 'A1', 102, 1, 40, False),
  (2, 'A1', 201, 2, 15, True),
  (3, 'B2', 102, 1, 30, False),
  (4, 'B2', 304, 3, 30, True);

INSERT INTO classroom_software (software_id, classroom_id) VALUES
  (0, 0),
  (0, 2),
  (1, 4),
  (2, 2),
  (2, 4);

INSERT INTO users (id, first_name, last_name, email, phone_number, role, password) VALUES
  (0, 'Admin', 'Administer', 'administer@agh.edu.pl', '+48123456789', 0, 'root'); -- passwords needs to be hashed!
insert into users (id, first_name, last_name, email, phone_number, role, password) values (1, 'Lynn', 'Pellissier', 'lpellissier0@marketwatch.com', '+86 410 770 4431', 1, '$2a$04$mBYgo57zbHhRpA7CR8u06eooXPpaNDgM9AjG5djL.Oxw2.kTc10vq');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (2, 'Orelia', 'Sorrel', 'osorrel1@trellian.com', '+30 981 763 2428', 1, '$2a$04$sL2DE283jD5YITf0nH3TKuRFlMD.IdFXoocGgn2TKkckmxzpeubUW');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (3, 'Tanitansy', 'Caulder', 'tcaulder2@sogou.com', '+381 545 605 2118', 1, '$2a$04$r77FmAGgpJQaCkCMVIC.FOHThfAy8WywadLotw2NHq0d.q9nXt0Km');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (4, 'Roxi', 'Kalisz', 'rkalisz3@toplist.cz', '+86 407 870 9009', 1, '$2a$04$lPEMWoipXiLAv2tBI/4d0uHe0OB8z9RXW.X8hzmcPdPhQy4GcEGYi');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (5, 'Benoite', 'Moroney', 'bmoroney4@sakura.ne.jp', '+855 775 432 0762', 3, '$2a$04$Ud/0X3WaP0aK8pHHwlT6OeT01MbvzKnw4XFBKhdIZrqb7oU7F8CJ.');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (6, 'Aldis', 'Hollingby', 'ahollingby5@mapquest.com', '+63 841 743 6017', 1, '$2a$04$JESOIDJRtcdh5tij1pc8puegrAnBuUt0lvJMYaMHCNRCY46uJC9BO');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (7, 'Robin', 'Ducker', 'rducker6@simplemachines.org', '+351 686 205 2056', 3, '$2a$04$fcdkMvXehNmx/SPExE31WeEo/uI9DJ/eXShfri1O8uHAd63aDS0A6');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (8, 'Joana', 'Myhan', 'jmyhan7@patch.com', '+225 399 326 2596', 1, '$2a$04$KUxlSt56jCQcZ43t2AneLuTL9j1XTS3h38dWmMZDe3YF5uueU5HyG');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (9, 'Waly', 'Lyster', 'wlyster8@desdev.cn', '+1 504 520 0747', 1, '$2a$04$VC0R7L6h7Ss9oS37m2J32eYSj0VwpWYnMzGdwx6HRpn4Ek2dm1.fy');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (10, 'Agathe', 'Willox', 'awillox9@sakura.ne.jp', '+86 979 771 3845', 3, '$2a$04$ydooWrZ/SgwWf3d7FacBC.Ea1U9UNpCUTfo4VxKcAHtpnXn0PrCEu');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (11, 'Karlens', 'Langworthy', 'klangworthya@cdc.gov', '+62 630 413 8534', 3, '$2a$04$cTNTLHF9NPVA7jeNGmwFaOzNhhTsF.rejTRbT.ABhTwXDJze2QZ0K');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (12, 'Tobin', 'Ellicott', 'tellicottb@etsy.com', '+63 459 810 8275', 2, '$2a$04$QOTaoV09xHOfvkZMR8Yui.z8AChYn7GdYpHHZ/S9u1n/j33AM7qvm');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (13, 'Mischa', 'Moquin', 'mmoquinc@goodreads.com', '+225 821 991 0495', 3, '$2a$04$uXX9of.z4YqAnmkHg2liv.vuc5IvdIEz6dJQg04ojN0d2RZIUr6Xu');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (14, 'Engelbert', 'Bellward', 'ebellwardd@vimeo.com', '+48 630 387 6984', 3, '$2a$04$t6SxwMZKuzd43eBvc.CBhOTuuwW4hd/M33yJPBbQKqQJ1mbl4t94O');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (15, 'Birk', 'Brunesco', 'bbrunescoe@nasa.gov', '+62 549 535 8837', 1, '$2a$04$YiiQrLKSCgfHdY6C0fEyfedP01DBcTE/KADvVb.0ERo.koN1xeuem');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (16, 'Culley', 'Chantree', 'cchantreef@myspace.com', '+86 464 550 9634', 3, '$2a$04$fBJgWL/IZVcG9aqc.fzyq.t5fOIJBKMs3t7nbTCh4teEBIbxugaAu');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (17, 'Minetta', 'Harragin', 'mharraging@walmart.com', '+58 671 836 6143', 1, '$2a$04$Xj0sPieLtzeMTrIB8rxnS.wS3Gtzu3oF7ikxZvV1ysXgs4eJCn1Fy');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (18, 'Naoma', 'Agate', 'nagateh@people.com.cn', '+7 719 297 3453', 3, '$2a$04$1AmUMXNHsJpNl0KI6ivrd.RzCVjNZjzEVyP8e/DnCa0fCtVzdg9xi');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (19, 'Frans', 'Seeger', 'fseegeri@artisteer.com', '+420 820 641 2918', 1, '$2a$04$0scwYqn8FFgnMMW0U9nE6uOZwH9z5HIcs8RYCpPPPQefFYGcv0Lse');
insert into users (id, first_name, last_name, email, phone_number, role, password) values (20, 'Donn', 'Bouzan', 'dbouzanj@goodreads.com', '+256 398 456 7310', 2, '$2a$04$Dy6zEAw6wtvw2AAd9.Ib5uI9rlW7XXIY2zaVTzVAdJEHYIjk7dSwO');

INSERT INTO courses (id, lecturer_id, representative_id, name) VALUES
  (0, 1, 5, 'Biologia molekularna'),
  (1, 2, 7, 'Karboniki wschodnie'),
  (2, 3, 5, 'Czarne dziury w niewidzialnym wszechświecie'),
  (3, 4, 16, 'ABOBA'),
  (4, 19, 10, 'Zagadki niebieskiego sera');

insert into classes (id, classroom_id, date_time, duration, course_id) values (1, 0, '2025-03-14 01:44:45', 65, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (2, 1, '2024-07-27 14:40:15', 86, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (3, 3, '2024-07-24 06:16:06', 83, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (4, 4, '2024-01-29 14:30:02', 86, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (5, 1, '2024-11-30 04:42:30', 63, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (6, 4, '2025-02-02 16:33:41', 71, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (7, 2, '2024-06-27 15:25:50', 70, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (8, 2, '2024-05-10 18:45:33', 62, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (9, 2, '2025-05-27 20:37:27', 84, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (10, 3, '2025-05-02 10:47:42', 68, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (11, 0, '2024-08-20 09:45:48', 79, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (12, 1, '2024-03-07 19:38:47', 89, 0);
insert into classes (id, classroom_id, date_time, duration, course_id) values (13, 0, '2024-07-09 15:50:53', 60, 3);
insert into classes (id, classroom_id, date_time, duration, course_id) values (14, 0, '2025-04-04 08:46:20', 63, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (15, 1, '2024-07-30 04:08:11', 73, 1);
insert into classes (id, classroom_id, date_time, duration, course_id) values (16, 0, '2024-03-30 01:48:58', 85, 3);
insert into classes (id, classroom_id, date_time, duration, course_id) values (17, 3, '2024-07-25 21:38:55', 89, 4);
insert into classes (id, classroom_id, date_time, duration, course_id) values (18, 0, '2024-06-01 13:11:25', 73, 3);
insert into classes (id, classroom_id, date_time, duration, course_id) values (19, 3, '2025-06-21 10:36:54', 65, 2);
insert into classes (id, classroom_id, date_time, duration, course_id) values (20, 4, '2024-07-31 19:57:05', 89, 3);


