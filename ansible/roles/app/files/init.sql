-- Автоматическое создание расширений при старте контейнера
-- TODO создать БД и дать права пользователю

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "postgis";
