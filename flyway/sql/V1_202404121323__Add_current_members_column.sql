ALTER TABLE `chat_room`.`room`
    ADD COLUMN `current_members` INT NOT NULL AFTER `max_members`;
