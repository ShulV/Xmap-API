# Основное

Перейти в env
```bash
source ~/env_ansible2-16/bin/activate
source ~/env_ansible_2_16/bin/activate
```

## Инициализация
Базовая настройка системы
- Установка базовых пакетов через dnf
- Создание пользователя deployer
- Добавление SSH-ключи для deployer в deployer/.ssh/authorized_keys
- Даем sudo без пароля
- настройка swap
- настройка firewalld
- настройка httpd
Инициализация (установка либ + httpd). Первый запуск должен быть под root, т.к. нет пользователя
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/init.yml -e ansible_user=root -k
```
P.S. на manage node должен быть установлен `sshpass` для передачи пароля через `-k`
```bash
sudo dnf install sshpass
```
После запуска попробовать подключиться к серверу
```bash
ssh deployer@45.130.146.99
```

Установка httpd
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy-httpd.yml
```

## Деплой
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy-app.yml
```

## Деплой мониторинга (прометеус + графана)
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy-monitoring.yml
```

## Dump/restore
создать backup на удаленном сервере
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/backup-db.yml
```
после выполнения дамп также скачивается локально в `~/backup` (или `db_backup_local_dir`) с именем вида `spotic-YYYY-MM-DDTHH:MM:SS.sql.gz`
`pg_dump` запускается внутри контейнера `db_backup_docker_container` (для test: `spotic-postgres-db`)

залить дамп в локальную БД, к-ая крутится в докере:
P.S. поменять название дампа!
```bash
залить в локальную БД, которая крутится в докере:
docker exec -i spotic-postgres-db psql -U xmap_admin -d postgres -c "
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'xmap'
  AND pid <> pg_backend_pid();
"

docker exec -i spotic-postgres-db psql -U xmap_admin -d postgres -c "DROP DATABASE IF EXISTS xmap;"
docker exec -i spotic-postgres-db psql -U xmap_admin -d postgres -c "DROP ROLE IF EXISTS xmap_admin;"
docker exec -i spotic-postgres-db psql -U xmap_admin -d postgres -c "CREATE USER xmap_admin WITH PASSWORD '123';"
docker exec -i spotic-postgres-db psql -U xmap_admin -d postgres -c "CREATE DATABASE xmap OWNER xmap_admin;"

docker exec -i spotic-postgres-db psql -U xmap_admin -d xmap -c 'CREATE EXTENSION IF NOT EXISTS "uuid-ossp";'
docker exec -i spotic-postgres-db psql -U xmap_admin -d xmap -c 'CREATE EXTENSION IF NOT EXISTS "postgis";'

gunzip -c ~/backup/spotic-2026-04-22T21:31:20.sql.gz | \
docker exec -i spotic-postgres-db psql -U xmap_admin -d xmap
```

выгружаем дамп на чистую БД с пересозданием пользователя
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/restore-db.yml -e restore_dump_src=~/backup/spotic-2026-02-01T16:35:47.sql.gz
```
