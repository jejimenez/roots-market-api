--
-- Logike.co - DeRaíz.
-- 2020.
--
-- Drop database script.
-- @author: Javier Latorre, <javier.latorre@logike.co>.
-- @version: 1.0
--

-------------------------------------------------------------
-- To disconnect all users.
-------------------------------------------------------------
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = ''
AND pid <> pg_backend_pid();

-- Drop database and role.
DROP DATABASE roots;
DROP ROLE roots_role;
