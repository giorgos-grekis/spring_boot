ALTER TABLE store.products
    ADD `description` TEXT NULL;

ALTER TABLE store.products
    MODIFY `description` TEXT NOT NULL;