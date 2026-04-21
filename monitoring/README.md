## Базовые настройки prometheus:
- scrape_interval: 15s # дергать метрики каждые 15 секунд (default)
- evaluation_interval: 15s # делать пересчет статистики, алерты каждые 15 секунд (default)
- --storage.tsdb.retention.size=10GB # ограничение по размеру данных
- --storage.tsdb.retention.time=30d # ограничение по времени хранения

```bash
P.S.
Что на практике означает 15 секунд:
4 scrape в минуту
240 scrape в час
5760 scrape в сутки
примерно 172 800 scrape в месяц на один target
```

## Примеры
### Prometheus
мониторинг количества запросов по урлам
<img src="./readme_images/prometheus_1.jpg" width="300px"/>

целевые приложения
<img src="./readme_images/prometheus_2.png" width="300px"/>

### Grafana
Первый вход

<img src="./readme_images/grafana_1.png" width="300px"/>

Дашборды

<img src="./readme_images/grafana_2.png" width="300px"/>

Источники данных

<img src="./readme_images/grafana_3.png" width="300px"/>

Метрики из готового дашборда (JVM Micrometer)

<img src="./readme_images/grafana_4.png" width="300px"/>

<img src="./readme_images/grafana_5.png" width="300px"/>

<img src="./readme_images/grafana_6.png" width="300px"/>

На боевом сервере

Micrometer (JVM)

<img src="./readme_images/grafana_7.png" width="300px"/>

Spring boot APM
<img src="./readme_images/grafana_8.png" width="300px"/>



## Стандартные креды grafana
`admin`:`admin`