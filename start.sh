docker-compose down

docker build -t tutoring-school:latest ./tutoring-school

docker-compose up --build --force-recreate --remove-orphans