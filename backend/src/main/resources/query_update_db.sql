/* INSERIMENTO FOREIGN KEY SU TUTORIAL -> AUTORE*/

ALTER TABLE `testdb`.`tutorials`
    ADD COLUMN `author_id` BIGINT NULL DEFAULT NULL AFTER `title`,
    ADD INDEX `author_id_idx` (`author_id` ASC) VISIBLE;
;
ALTER TABLE `testdb`.`tutorials`
    ADD CONSTRAINT `author_id`
        FOREIGN KEY (`author_id`)
            REFERENCES `testdb`.`authors` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;


