# Основное

перейти в env
```bash
source ~/env_ansible2-16/bin/activate
```

инициализация
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/init.yml -u root --ask-become-pass
```

деплой
```bash
ansible-playbook -i inventories/spotic-test/hosts.yml playbooks/deploy.yml -u root --ask-become-pass
```
