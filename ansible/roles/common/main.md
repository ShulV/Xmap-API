# Базовая настройка системы
- Установка базовых пакетов через dnf
- Создание пользователя deployer
- Добавление SSH-ключи для deployer в deployer/.ssh/authorized_keys
- Даем sudo без пароля

Первый запуск с запросом пароля под рутом:
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/init.yml -u root --ask-become-pass
```

После запуска попробовать подключиться к серверу 
```bash
ssh deployer@45.130.146.99
```