CREATE TABLE `users` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `login` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `admin` boolean NOT NULL,
  `is_positive` boolean NOT NULL,
  `positive_date` date,
  `is_vaccinated` boolean
);

CREATE TABLE `friendships` (
  `id_user1` int,
  `id_user2` int
);

CREATE TABLE `notifications` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `id_user` int,
  `src_user` int,
  `type` int NOT NULL,
  `content` varchar(255) NOT NULL,
  `is_read` boolean NOT NULL
);

CREATE TABLE `activities` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_place` varchar(255),
  `id_user` int,
  `begining` datetime NOT NULL,
  `end` datetime NOT NULL
);

ALTER TABLE `friendships` ADD FOREIGN KEY (`id_user1`) REFERENCES `users` (`id`);

ALTER TABLE `friendships` ADD FOREIGN KEY (`id_user2`) REFERENCES `users` (`id`);

ALTER TABLE `notifications` ADD FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

ALTER TABLE `notifications` ADD FOREIGN KEY (`src_user`) REFERENCES `users` (`id`);

ALTER TABLE `activities` ADD FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);
