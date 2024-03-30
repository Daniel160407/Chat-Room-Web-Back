CREATE TABLE `chat_room`.`rooms`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45) NOT NULL,
    `max_members` INT         NOT NULL,
    PRIMARY KEY (`id`)
);
