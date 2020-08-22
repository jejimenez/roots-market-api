--
-- Logike.co - DeRaíz.
-- 2020.
--
-- Database creation script.
-- @author: Javier Latorre, <javier.latorre@logike.co>.
-- @version: 1.0
--

-------------------------------------------------------------
-- Role password: rootsPwd
-------------------------------------------------------------

CREATE ROLE roots_role NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'rootsPwd';

-------------------------------------------------------------
-- Must be run by the user postgres
-------------------------------------------------------------

CREATE DATABASE roots
    WITH
    OWNER = roots_role
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
  
COMMENT ON DATABASE roots
    IS 'Database for DeRaíz App';

