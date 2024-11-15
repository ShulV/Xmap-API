--liquibase formatted sql
--changeset Shulpov Victor:20241111223433-insert-test-spots splitStatements:true
INSERT INTO spot ("name", lat, lon, description) VALUES
                                                     ('Парк на Арене', 40.7128, -74.0060, 'Прекрасный городской парк для катания на BMX.'),
                                                     ('Скейтпарк у Река', 34.0522, -118.2437, 'Скейтпарк с крутыми рампами и перилами.'),
                                                     ('Горная Тропа', 37.7749, -122.4194, 'Трасса для MTB с живописными видами.'),
                                                     ('Курорт у Побережья', 36.7783, -119.4179, 'Набережная для катания с потрясающими видами на океан.'),
                                                     ('Городская Площадь', 51.5074, -0.1278, 'Центральная площадь для любителей скейта.'),
                                                     ('Площадь в Центре', 48.8566, 2.3522, 'Подходит для BMX и скейтбордов.'),
                                                     ('Пешеходная Дорога', 41.8781, -87.6298, 'Идеальное место для спокойных поездок на велосипеде.'),
                                                     ('Территория Кампуса', 35.6895, 139.6917, 'Корпус университета, популярное место для катания.'),
                                                     ('Старый Завод', 40.4406, -79.9959, 'Заброшенный завод, отличный для BMX.'),
                                                     ('Городской Парк', 52.5200, 13.4050, 'Парковая зона с множеством функций для катания.'),
                                                     ('Валley Ramp', 37.3382, -121.8863, 'Крутые рампы и препятствия для скейтборда.'),
                                                     ('Рай для Скейтбордистов', 34.0522, -118.2437, 'Отличное место для всех любителей скейтбординга.'),
                                                     ('Уличный Гонг', 53.349805, -6.26031, 'Городская трасса, популярная среди скутеров.'),
                                                     ('Восточные Тропы', 40.730610, -73.935242, 'Трассы для MTB с живописными пейзажами.'),
                                                     ('На Моллой Волне', 37.7749, -122.4194, 'Идеальное место для катания на скейтборде и BMX.');