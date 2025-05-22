ALTER TABLE store.users
    DROP COLUMN state;

ALTER TABLE store.addresses
    ADD state VARCHAR(255) NOT NULL;