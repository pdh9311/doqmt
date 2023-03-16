NAME=doqmt

all: $(NAME)

$(NAME) : g_build up

g_build :
	./gradlew build

g_clean :
	./gradlew clean

up :
	docker compose up -d

ps :
	docker compose ps

down :
	docker compose down

logs-db:
	docker compose logs database

logs-app:
	docker compose logs application

flogs-db :
	docker compose logs database -f

flogs-app :
	docker compose logs application -f

top :
	docker compose top database
	docker compose top application

exec-db :
	docker compose exec database /bin/bash

exec-app :
	docker compose exec application /bin/bash

clean : down g_clean
	docker compose down -v --rmi all
	docker system prune --volumes --all --force
	docker network prune --force
	docker volume prune --force

re : clean g_build
	docker compose up -d --build --force-recreate

.PHONY : g_build, g_clean, up, ps, down, logs, logsf, top, exec-db, exec-app, clean, re