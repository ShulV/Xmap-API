# Установка ansible

control node: `Python 3.13.7`
managed node: `Python 3.9.21`

| Ansible core version                                                                       | Support                                                                   | End Of Life                | Control Node Python                | Target Python / PowerShell                                          |
| ------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------- | -------------------------- | ---------------------------------- | ------------------------------------------------------------------- |
| [2.18](https://github.com/ansible/ansible/blob/stable-2.18/changelogs/CHANGELOG-v2.18.rst) | GA: 04 Nov 2024<br><br>Critical: 19 May 2025<br><br>Security: 03 Nov 2025 | May 2026                   | Python 3.11 - 3.13                 | Python 3.8 - 3.13<br><br>PowerShell 5.1                             |

>P.S. названия команд могут быть с `3` (python3, pip3)

Лучше создать виртуальное окружение с версией:
1) создать отдельную виртуальную среду
```bash
virtualenv -p python env_ansible2-16
```
> Будет создана директория ~/env_ansible2-16

2) перейти в env
```bash
source ~/env_ansible2-16/bin/activate
```

>P.S. будет так: `(env_ansible2-16) victor@MacMini ~ % `

3) внутри env устанавливаем ansible
```bash
pip install 'ansible==11'
```

проверяем версию
```bash
(env_ansible2-16) victor@MacMini ~ % ansible --version
ansible [core 2.18.9]
```

