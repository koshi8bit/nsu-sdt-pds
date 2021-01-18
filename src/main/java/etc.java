public class etc {
//    @Test
//    public void testPersistentPersistentHashMapInsertedUndoRedo() {
//        PersistentHashMap<String, PersistentHashMap<String, Integer>> parent = new PersistentHashMap<>();
//        PersistentHashMap<String, Integer> child1 = new PersistentHashMap<>();
//        PersistentHashMap<String, Integer> child2 = new PersistentHashMap<>();
//        PersistentHashMap<String, Integer> child3 = new PersistentHashMap<>();
//
//        parent.put("child1", child1);
//        parent.put("child2", child2);
//        parent.put("child3", child3);
//
//        parent.get("child1").put("One", 1);
//        parent.get("child1").put("Two", 2);
//        parent.get("child1").put("Three", 3);
//
//        parent.get("child2").put("One", 11);
//        parent.get("child2").put("Two", 22);
//        parent.get("child2").put("Three", 33);
//
//        parent.get("child3").put("One", 111);
//        parent.get("child3").put("Two", 222);
//        parent.get("child3").put("Three", 333);
//
//        assertEquals(Integer.valueOf(333), parent.get("child3").get("Three"));
//        parent.undo();
//        assertFalse(parent.get("child3").containsKey("Three"));
//
//        PersistentHashMap<String, Integer> child4 = new PersistentHashMap<>();
//        parent.put("child4", child4);
//        System.out.println("!!!");
//        System.out.println(parent.toString());
//        parent.undo();
//        System.out.println(parent.toString());
//        System.out.println("!!!");
//        child4.put("Меня выпилят :(", 666);
//
//        assertEquals(Integer.valueOf(666), parent.get("child4").get("Меня выпилят :("));
//        System.out.println(parent.toString());
//        parent.undo();
//        assertFalse(parent.get("child4").containsKey("Меня выпилят :("));
//        System.out.println(parent.toString());
//        parent.undo();
//        System.out.println(parent.toString());
//        assertFalse(parent.containsKey("child4"));
//    }
//
//    @Test
//    public void testPersistentLinkedListInsertedUndoRedo() {
//        PersistentLinkedList<PersistentLinkedList<String>> parent = new PersistentLinkedList<>();
//        PersistentLinkedList<String> child1 = new PersistentLinkedList<>();
//        PersistentLinkedList<String> child2 = new PersistentLinkedList<>();
//        PersistentLinkedList<String> child3 = new PersistentLinkedList<>();
//        parent.add(child1);
//        parent.add(child2);
//        parent.add(child3);
//
//        parent.get(0).add("1");
//        parent.get(0).add("2");
//        parent.get(0).add("3");
//
//        parent.get(1).add("11");
//        parent.get(1).add("22");
//        parent.get(1).add("33");
//
//        parent.get(2).add("111");
//        parent.get(2).add("222");
//        parent.get(2).add("333");
//
//        assertEquals("[[1, 2, 3], [11, 22, 33], [111, 222, 333]]", parent.toString());
//        parent.undo();
//        assertEquals("[[1, 2, 3], [11, 22, 33], [111, 222]]", parent.toString());
//
//
//        PersistentLinkedList<String> child4 = new PersistentLinkedList<>();
//        parent.add(1, child4);
//        child4.add("Меня выпилят :(");
//        assertEquals("[[1, 2, 3], [Меня выпилят :(], [11, 22, 33], [111, 222]]", parent.toString());
//        parent.undo();
//        assertEquals("[[1, 2, 3], [], [11, 22, 33], [111, 222]]", parent.toString());
//
//        parent.get(0).set(0, "Я выживу!");
//        parent.get(0).set(1, "А я нет...");
//        assertEquals("[[Я выживу!, А я нет..., 3], [], [11, 22, 33], [111, 222]]", parent.toString());
//        parent.undo();
//        assertEquals("[[Я выживу!, 2, 3], [], [11, 22, 33], [111, 222]]", parent.toString());
//    }

//        pl.add(5, 2);
//        System.out.println("add(5, 2)");
//        System.out.println(pl.drawGraph());
}
