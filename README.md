Задание 3: Система учёта студентов (Дима Дикун)
Цель: Управление списком студентов и их оценками.
Требования:

Модели: Student (id, name, email), Grade (studentId, subject, score).
Интерфейсы: StudentRepository, GradeRepository.
Реализации: StudentRepositoryJSON, GradeRepositoryJSON.
Сервис AcademicService — методы: getAverageScore(String studentName), getTopStudents(int limit).
Контроллер AcademicController — вывод среднего балла студента.
В application.properties:

data.files=src/main/resources/students.json,src/main/resources/grades.json
В XML:

Использовать SpEL для разделения data.files на два пути.
Создать два репозитория с разными dataPath.
Внедрить оба репозитория в AcademicService через конструктор.

Запушить в репозиторий (можно в новый) ветку 'develop' без решения.
Решение оформить в отдельной ветке 'feature/xml-config', ответвившись от 'develop'.
Запушить решение.
Создать пул-реквест 'feature/xml-config'>'develop'.
В настройках github необходимо установить запрет слияния веток без аппрува.
Сбросить пул-реквест в группу в ТГ и получить аппрув или замечания.
После получения аппрува выполнить слияние.

