package certif.collections;

import java.util.*;

public class Sand {
    public static void main(String[] args) {
//        array();
//        List<String> list = list();
//        set(list);
//        deque(list);
//        map();
        concur();
    }

    public static void array() {
        int[] intArrDest = new int[10];
        float floatArr[] = new float[5]; // not recommended

        int[] intArrSrc = {100, 200, 300};
        Arrays.stream(intArrSrc).mapToObj(i -> i + " ").forEach(System.out::print);
        System.out.println();
        System.out.println(Arrays.toString(intArrSrc));//[100, 200, 300]

        System.arraycopy(intArrSrc, 0, intArrDest, 0, 3); // no autoboxing
        for (int i : intArrDest) {
            System.out.print(i + " ");
        }
        System.out.println();

        int[] copyOfIntArr = Arrays.copyOfRange(intArrSrc, 1, 3);
        for (int i : copyOfIntArr) {
            System.out.print(i + " ");// 200 300
        }
        System.out.println();
        int[] copyOfIntArrLonger = Arrays.copyOfRange(intArrSrc, 1, 5);
        for (int i : copyOfIntArrLonger) {
            System.out.print(i + " ");// 200 300 0 0
        }
        System.out.println();

        int i = Arrays.binarySearch(intArrSrc, 200);//intArrSrc must be sorted
        System.out.println(i);

        System.out.println(Arrays.equals(intArrDest, intArrSrc));

        Arrays.fill(floatArr, 5f);
        for (float f : floatArr) {
            System.out.print(f + " ");// 5.0 5.0 ...
        }
        System.out.println();

        Arrays.parallelSort(floatArr);
    }

    public static List<String> list() {
        List<String> unmodifiableList = List.of("zff", "kk", "ll");//null are disallowed
        //unmodifiableList.add("throws");//UnsupportedOperationException
        // value-based: final class, final fields, equals() may be ==, ...
        List<String> modifiableList = new ArrayList<>();
        modifiableList.add("gf");
        modifiableList.add("hy");
        System.out.println(modifiableList);
        ListIterator<String> stringListIterator = modifiableList.listIterator();
        //stringListIterator.remove();//IllegalStateException (call next() or previous() before)

        int i = Collections.binarySearch(unmodifiableList, "g", Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("index of g " + i); // -1 (always < 0 if not found but possible too if collection not ordered!)
        i = Collections.binarySearch(unmodifiableList, "kk", Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("index of kk " + i); // 1

        Collections.sort(modifiableList);
        System.out.println(modifiableList); // [gf, hy]
        Collections.reverse(modifiableList);
        System.out.println(modifiableList); // [hy, gf]

        SequencedCollection<String> list = new ArrayList<>();
        list.addFirst("a");
        System.out.println(list.getFirst());
        System.out.println(list.removeFirst());

        return unmodifiableList;
    }

    public static void set(SequencedCollection<String> list) {
        Set<String> modifiableSet = new HashSet<>();
        modifiableSet.addAll(list);
        System.out.println(modifiableSet); //[kk, ll, zff]
        System.out.println("disjoint "+Collections.disjoint(modifiableSet, list));

        SortedSet<String> sortedSet = new TreeSet<>(modifiableSet);
        System.out.println(sortedSet.first());
        System.out.println(sortedSet.comparator());//null because natural order
        System.out.println(sortedSet.tailSet("ll"));//[ll, zff]

        SequencedSet<String> sequencedSet = new TreeSet<>(modifiableSet);
        System.out.println(sequencedSet.reversed()); //[zff, ll, kk]
    }

    public static void deque(SequencedCollection<String> list) {
        // Queue methods on Deque are FIFO
        Deque<String> modifiableDeque = new LinkedList<>(list);
        System.out.println(modifiableDeque.offerFirst("y"));
        System.out.println(modifiableDeque.pollLast());//removes last too
        System.out.println(modifiableDeque.peekLast());
        modifiableDeque.addFirst("u");//throws if can't
        System.out.println(modifiableDeque.removeFirst());//throws if can't
        modifiableDeque.push("yiu"); // addFirst
        modifiableDeque.add("last"); // addLast
        System.out.println(modifiableDeque.getFirst());
        System.out.println(modifiableDeque.peek());//getFirst
        System.out.println(modifiableDeque.removeFirstOccurrence("y"));
    }

    public static void map() {
        Map<String, String> modifiableMap = new HashMap<>();
        System.out.println(modifiableMap.put("fgf", "gtgrt"));// returns previous
        System.out.println(modifiableMap.putIfAbsent("hgdfhf", "hfhdfgfh"));// returns previous
        System.out.println(modifiableMap.computeIfAbsent("uy", k -> "i"));// returns current (existing or computed)
        System.out.println(modifiableMap.computeIfPresent("uy", (k, v) -> "j"));// returns new

        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("ioio", "ipoi");
        sortedMap.put("yrtfg", "hfghfg");
        System.out.println(sortedMap.merge("fgf", "zerzrz", (oldValue, value) -> "ert"));
        System.out.println(sortedMap.merge("yrtfg", "rter", (oldValue, value) -> "nbnv"));
        System.out.println(sortedMap);//{fgf=zerzrz, ioio=ipoi, yrtfg=nbnv}
        System.out.println(sortedMap.firstKey());// fgf
        System.out.println(sortedMap.headMap("ioio"));//{fgf=zerzrz}

        SequencedMap<String, String> sequencedMap = new TreeMap<>();
        sequencedMap.put("ioio", "ipoi");
        sequencedMap.put("yrtfg", "hfghfg");
        System.out.println(sequencedMap.merge("fgf", "zerzrz", (oldValue, value) -> "ert"));
        System.out.println(sequencedMap.merge("yrtfg", "rter", (oldValue, value) -> "nbnv"));
        System.out.println(sequencedMap);//{fgf=zerzrz, ioio=ipoi, yrtfg=nbnv}
        System.out.println(sequencedMap.firstEntry());// fgf=zerzrz
        System.out.println(sequencedMap.sequencedKeySet());//[fgf, ioio, yrtfg]
        System.out.println(sequencedMap.reversed());//{yrtfg=nbnv, ioio=ipoi, fgf=zerzrz}
        System.out.println(sequencedMap.sequencedEntrySet());//[fgf=zerzrz, ioio=ipoi, yrtfg=nbnv]
        System.out.println(sequencedMap.pollFirstEntry());// fgf=zerzrz
        System.out.println(sequencedMap);//{ioio=ipoi, yrtfg=nbnv}
        //System.out.println(sequencedMap.putLast("uyt", "pom"));//throws UnsupportedOperationException
    }

    public static void concur() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 5, 5);
        integers.parallelStream().distinct().forEach(System.out::println);// distinct is as expected, but order is not guaranteed
        integers.parallelStream().distinct().forEachOrdered(System.out::println);// distinct is as expected, and order is guaranteed
        integers.stream().unordered().forEachOrdered(System.out::println);// order is not guaranteed (forEachOrdered has encounter order if stream has)
    }
}
