docker run --name ndb -p 5432:5432 -e POSTGRES_PASSWORD=pass -d postgres
docker exec -i -t ndb bash
su - postgres
psql -d postgres -U postgres