# nsu-sdt-pds
Курсач по СМП на тему "Persistent data structure"

## Ссылки
* [Алгоритм копирования путей, он же черновик](https://docs.google.com/document/d/1CKQ902VsTXsa9CZYZVnGKs5C4jJO3WkqjIPo0ntAMcM/edit#)
* [Functional Data Structures in Scala (видосик 50 мин.)](https://www.infoq.com/presentations/Functional-Data-Structures-in-Scala/)
* [Persistent data structure в кожуре](https://github.com/clojure/clojure/blob/0b73494c3c855e54b1da591eeb687f24f608f346/src/jvm/clojure/lang/PersistentVector.java#L97-L113)

* [Задание](http://ccfit.nsu.ru/~shadow/DT6/course_tasks/PDS.html)
* [MIT youtube](https://www.youtube.com/playlist?list=PLUl4u3cNGP61hsJNdULdudlRL493b-XZf)
* [Habr 01 simple](https://m.habr.com/ru/post/113585/)
* [Пресистентность в Кожуре (часть 1)](https://hypirion.com/musings/understanding-persistent-vector-pt-1)
* [Пресистентность в Кожуре (часть 2)](https://hypirion.com/musings/understanding-persistent-vector-pt-2)
* [Сторонняя реализация java абстракций](https://github.com/functionaljava/functionaljava/tree/series/5.x/core/src/main/java/fj/data)
* [Вики](https://en.wikipedia.org/wiki/Persistent_data_structure)

## План работы
- [x] Сделать собственную обертку над LinkedList, реализующую интерфейс List ~~??**Collection**/Iterable??~~. **Deadline 31.10.2020**
- [ ] Раскурить все статьи из раздела ссылки. **Deadline  7.11.2020**
- [ ] Описать на листе бумаги, как будем реализовывать алгоритмы в коде. **Deadline 14.11.2020**

## Инфа
### Классы в java
![Классы в java](/img/01.png)

### Виды пресистентности
![Виды пресистентности](/img/02.png)

## Как че вообще
Можно представить дерево в виде списка, и делать вставку за O(1). Обход дерева указан на рисунке ниже (красным)
![Виды пресистентности](/img/03.png)
