use `profile`;

DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `like_reply`;
DROP TABLE IF EXISTS `replies`;
DROP TABLE IF EXISTS `like_post`;
DROP TABLE IF EXISTS `following`;
DROP TABLE IF EXISTS `follower`;
DROP TABLE IF EXISTS `posts`;
DROP TABLE IF EXISTS `user`; 
 
 CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL,
  `nick_name` varchar(50) NOT NULL,
  `pw` char(68) NOT NULL,
  `active` tinyint NOT NULL,
  `email` varchar(50) NOT NULL,
  `about` varchar(250) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `followers` int default null,
  PRIMARY KEY (`user_id`),
  unique key (`image`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `user`
--

INSERT INTO `user`
VALUES
('john','johny','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'john@gmail.com','Hi my name is john','https://images.hellomagazine.com/horizon/square/9b9ad3586fb2-henry-cavill-t.jpg',1),
('mary','Scarlet','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'mary@gmail.com','Hi my name is mary','https://1.bp.blogspot.com/-lCM2qSHkbDs/W1nfHs4l74I/AAAAAAAAMKE/H8UJx4RVPWAFaEiE-hcOP8XKgeWFklDTgCLcBGAs/s1600/2014010812335117640.jpg',2),
('susan','Nami','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'susan@gmail.com','Hi my name is susan','https://qph.cf2.quoracdn.net/main-qimg-cfb83d19fb9211e2129bc5000ccb3fd7-lq',1);    



CREATE TABLE `following` (
  `user_id` varchar(50) NOT NULL,
  `following_id` varchar(50) NOT NULL,
  unique KEY `FK_USER4_idx` (`user_id`,`following_id`),
  CONSTRAINT `FK_USER4` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `following`
values
('john','mary'),
('mary','john'),
('mary','susan'),
('susan','mary');

CREATE TABLE `follower` (
  `user_id` varchar(50) NOT NULL,
  `follower_id` varchar(50) NOT NULL,
  unique KEY `FK_USER6_idx` (`user_id`,`follower_id`),
  CONSTRAINT `FK_USER6` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `follower`
values
('john','mary'),
('mary','john'),
('mary','susan'),
('susan','mary');




CREATE TABLE `posts` (
	`user_id` varchar(50) DEFAULT NULL,
    `post_id` int NOT NULL AUTO_INCREMENT,
	`text` varchar(500) NOT NULL,
    `image` varchar(500) DEFAULT NULL,
    `avatar` varchar(500) DEFAULT NULL,
    `nick_name` varchar(50) default null,
	PRIMARY KEY (`post_id`),
	KEY `FK_USER1_idx` (`user_id`),
	CONSTRAINT `FK_USER1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `posts`
VALUES
('john',1,'Hello this is my first post!!! my name is john',null,'https://images.hellomagazine.com/horizon/square/9b9ad3586fb2-henry-cavill-t.jpg','johny'),
('mary',2,'Hello this is my first post!!! my name is mary', 'https://1.bp.blogspot.com/-lCM2qSHkbDs/W1nfHs4l74I/AAAAAAAAMKE/H8UJx4RVPWAFaEiE-hcOP8XKgeWFklDTgCLcBGAs/s1600/2014010812335117640.jpg','https://1.bp.blogspot.com/-lCM2qSHkbDs/W1nfHs4l74I/AAAAAAAAMKE/H8UJx4RVPWAFaEiE-hcOP8XKgeWFklDTgCLcBGAs/s1600/2014010812335117640.jpg','Scarlet'),
('susan',3,'Hello this is my first post!!! my name is susan',null,'https://qph.cf2.quoracdn.net/main-qimg-cfb83d19fb9211e2129bc5000ccb3fd7-lq','Nami');

CREATE TABLE `like_post` (
	`user_id` varchar(50) DEFAULT NULL,
    `post_id` int default NULL,
    `like_id` int NOT NULL AUTO_INCREMENT,
    `liked` boolean default null,
    primary key (`like_id`),
    KEY `FK_USER5_idx` (`user_id`),
	CONSTRAINT `FK_USER5` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    KEY `FK_POST2_idx` (`post_id`),
	CONSTRAINT `FK_POST2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    )ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    INSERT INTO `like_post` values ('john',2,1,true);

CREATE TABLE `replies` (
	`user_id` varchar(50) DEFAULT NULL,
    `post_id` int default NULL,
    `reply_id` int NOT NULL AUTO_INCREMENT,
	`text` varchar(500) NOT NULL,
    `image` varchar(500) DEFAULT NULL,
    `user_image` varchar(500) DEFAULT NULL,
    `nick_name` varchar(50) default null,
	PRIMARY KEY (`reply_id`),
	KEY `FK_USER2_idx` (`user_id`),
	CONSTRAINT `FK_USER2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    KEY `FK_USER_IMAGE_idx` (`user_image`),
    CONSTRAINT `FK_USER_IMAGE` FOREIGN KEY (`user_image`) REFERENCES `user` (`image`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    KEY `FK_POST1_idx` (`post_id`),
	CONSTRAINT `FK_POST1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `replies`
VALUES
('john',2,1,'Hello mary^^',null,'https://images.hellomagazine.com/horizon/square/9b9ad3586fb2-henry-cavill-t.jpg','johny');

CREATE TABLE `like_reply` (
	`user_id` varchar(50) DEFAULT NULL,
    `reply_id` int default NULL,
    `liked` boolean default null,
    unique KEY `FK_USER6_idx` (`user_id`,`reply_id`),
	CONSTRAINT `FK_USER7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_REPLY1` FOREIGN KEY (`reply_id`) REFERENCES `replies` (`reply_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    )ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    INSERT INTO `like_reply` values ('john',1,true);

CREATE TABLE `roles` (
   `user_id` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`user_id`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `roles`
--

INSERT INTO `roles`
VALUES
('john','ROLE_USER'),
('mary','ROLE_USER'),
('susan','ROLE_USER'),
('susan','ROLE_ADMIN');