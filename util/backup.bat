set PGUSER=postgres
set PGPASSWORD=postgres123
cd util
pg_dump.exe --host localhost --port 5432 --username "postgres" --no-password  --format custom --blobs --verbose --file "siscon.backup" "siscon"

