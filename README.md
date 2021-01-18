# nsu-sdt-pds
Курсач по СМП на тему "Persistent data structure"

* [Алгоритмы](https://docs.google.com/document/d/1CKQ902VsTXsa9CZYZVnGKs5C4jJO3WkqjIPo0ntAMcM/edit#)
* [Умные расчеты](https://docs.google.com/spreadsheets/d/1xNfALDSZxXMbOlgF4tEBWf7jhpzwcq4zjkXq-W9hojI/edit#gid=0)

Пресистентность в Кожуре
* [#01](https://hypirion.com/musings/understanding-persistent-vector-pt-1)
* [#02](https://hypirion.com/musings/understanding-persistent-vector-pt-2)
* [#03](https://hypirion.com/musings/understanding-persistent-vector-pt-3)
* [#04](https://hypirion.com/musings/understanding-clojure-transients)
* [#05](https://hypirion.com/musings/persistent-vector-performance-summarised)

## Задание
Требования: реализуйте библиотеку со следующими структурами данных в persistent-вариантах:
- [x] Массив (константное время доступа, переменная длинна)
- [x] Двусвязный список
- [x] Ассоциативный массив (на основе Hash-таблицы, либо бинарного дерева)
- [x] Должен быть единый API для всех структур, желательно использовать естественный API для выбранной платформы

Дополнительные требования
- [x] Обеспечить произвольную вложенность данных (по аналогии с динамическими языками), не отказываясь при этом полностью от типизации посредствомgeneric/template.
- [x] Реализовать универсальныйundo-redo механизм для перечисленных структур с поддержкой каскадности (для вложенных структур)
- [x] Реализовать более эффективное по скорости доступа представление структур данных, чем fat-node
- [ ] Расширить экономичное использование памяти на операцию преобразования одной структуры к другой (например, списка в массив)
- [ ] Реализовать поддержку транзакционной памяти STM (?atom VS ref. agent мб очередь операций с пуллом потоков VS actor) 

## Вопросы
- [ ] Алгоритм копирования пути
- [ ] Как удалять из середины

## Ссылки
* [Задание (pdf)](/ect/task.pdf)
* [Задание (новое)](https://classroom.google.com/u/1/c/MTU4ODY2Njk3NDM3/m/MjIxNzU2MDE1NzA3/details)
* [Задание (старое)](http://ccfit.nsu.ru/~shadow/DT6/course_tasks/PDS.html)
* [Persistent data structure в кожуре (java)](https://github.com/clojure/clojure/blob/0b73494c3c855e54b1da591eeb687f24f608f346/src/jvm/clojure/lang/PersistentVector.java#L97-L113)
* [Functional Data Structures in Scala (видосик 50 мин.)](https://www.infoq.com/presentations/Functional-Data-Structures-in-Scala/)
* [MIT youtube](https://www.youtube.com/playlist?list=PLUl4u3cNGP61hsJNdULdudlRL493b-XZf)
* [Habr 01 simple](https://m.habr.com/ru/post/113585/)
* [Сторонняя реализация java абстракций](https://github.com/functionaljava/functionaljava/tree/series/5.x/core/src/main/java/fj/data)
* [Вики](https://en.wikipedia.org/wiki/Persistent_data_structure)

## Инфа
### Классы в java
![Классы в java](/ect/01.png)

### Виды пресистентности
![Виды пресистентности](/ect/02.png)

## Как че вообще
Можно представить дерево в виде списка, и делать вставку за O(1). Обход дерева указан на рисунке ниже (красным)
![Виды пресистентности](/ect/03.png)
