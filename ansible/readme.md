# Основное

Перейти в env
```bash
source ~/env_ansible2-16/bin/activate
source ~/env_ansible_2_16/bin/activate
```

Инициализация (установка либ + httpd)
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/init.yml -u root --ask-become-pass
```

Установка httpd
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy-httpd.yml
```

Деплой
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy.yml -u root --ask-become-pass
```

Деплой мониторинга (прометеус + графана)
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy-monitoring.yml
```