# nsu-sdt-pds
Курсач по СМП на тему "Persistent data structure"

## Ссылки
* [Задание](http://ccfit.nsu.ru/~shadow/DT6/course_tasks/PDS.html)
* [MIT youtube](https://www.youtube.com/playlist?list=PLUl4u3cNGP61hsJNdULdudlRL493b-XZf)
* [Habr 01 simple](https://m.habr.com/ru/post/113585/)
* [Пресистентность в Кожуре (часть 1)](https://hypirion.com/musings/understanding-persistent-vector-pt-1)
* [Пресистентность в Кожуре (часть 2)](https://hypirion.com/musings/understanding-persistent-vector-pt-2)
* [Сторонняя реализация java абстракций](https://github.com/functionaljava/functionaljava/tree/series/5.x/core/src/main/java/fj/data)
* [Вики](https://en.wikipedia.org/wiki/Persistent_data_structure)

## План работы
- [ ] Сделать собственную обертку над LinkedList, реализующую интерфейс ??**Collection**/Iterable??. **Deadline 31.10.2020**
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
