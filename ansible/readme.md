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
выгружаем дамп на чистую БД с пересозданием пользователя
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/restore-db.yml -e restore_dump_src=~/backup/spotic-2026-02-01T16:35:47.sql.gz
```