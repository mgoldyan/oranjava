package main.java.com.oranjava.datastrikers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * MIT license , Copyright (C) 2018 mgoldyan
 *
 * The Oranjava Project : https://github.com/mgoldyan/oranjava
 *
 * Quick and useful static factory methods for safely generating a collection from elements or from collections.
 * Note : all generated collections are mutable (unlike Java's Arrays.asList(...) which is unmodifiable).
 *
 * Usage : pretty straight forward and safe to to use - without having to worry about any NullPointerException(s).
 *
 * To generate a mutable List of Strings :
 *  asLinkedList("Hello", "World"); // Creates a new instance of LinkedList, built of the provided String element(s).
 *  asArrayList(stringCollection); // Creates a new instance of ArrayList, built of the provided collection of Strings.
 *
 * In the same manner, you may also safely create mutable Set of any type :
 *  asLinkedSet(1, 2, 3); // Creates a new instance of LinkedHashSet, containing Integer elements.
 *  asSet(integers, integers2, integers3); // Creates a new instance of HashSet, merged from all collections.
 *  asConcurrentSet("Hi"); // Creates a new instance of a concurrent HashSet, containing a the String "Hi".
 *
 * For an even broader set of abilities, where one may wish to quickly create any type Collection :
 *  asCollection(ArrayDeque::new, longs, longs2); // Creates a new instance of ArrayDeque, merged from all collections.
 *  asCollectionVarargs(PriorityQueue::new, "Y", "Z"); // Creates a new instance of PriorityQueue, containing "Y" , "Z".
 *
 * To quickly and safely check if a collection is null or empty, or, on the other hand, contains at least one element :
 *  isNullOrEmpty(integers); // Returns true iff the integers collection is either null or empty.
 *  hasAtLeastOneElement(strings); // Returns true iff the strings collection is contains at least one element.
 *
 *
 * For more usages and further details, please follow the documentation of the methods in this class.
 *
 *
 * BETA version (0.2).
 */
public class Collections {

    /**
     * Generates a new (mutable) HashSet out of a given number of (zero or more) element(s).
     *
     * @param elements Zero or more number of elements which will be wrapped in a new HashSet.
     * @param <E>      Type of given element(s).
     * @return  New (mutable) HashSet, built of the given varargs of element(s).
     */
    public static <E> HashSet<E> asSet(E... elements) {
        return asCollectionVarargs(HashSet::new, elements);
    }

    /**
     * Generates a new (mutable) HashList, merged from given (one or more) Collection(s).
     *
     * @param collection Collection instance which may contain element(s) of type E.
     * @param more       Zero or more number of collections which may contain element(s) of type E.
     * @param <E>        Type of given element(s).
     * @return  New (mutable) HashList, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E> HashSet<E> asSet(Collection<E> collection, Collection<E>... more) {
        return asCollection(HashSet::new, collection, more);
    }


    /**
     * Generates a new (mutable) LinkedHashSet out of a given number of (zero or more) element(s).
     *
     * @param elements Zero or more number of elements which will be wrapped in a new LinkedHashSet.
     * @param <E>      Type of given element(s).
     * @return  New (mutable) LinkedHashSet, built of the given varargs of element(s).
     */
    public static <E> LinkedHashSet<E> asLinkedSet(E... elements) {
        return asCollectionVarargs(LinkedHashSet::new, elements);
    }

    /**
     * Generates a new (mutable) LinkedHashSet, merged from given (one or more) Collection(s).
     *
     * @param collection Collection instance which may contain element(s) of type E.
     * @param more       Zero or more number of collections which may contain element(s) of type E.
     * @param <E>        Type of given element(s).
     * @return  New (mutable) LinkedHashSet, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E> LinkedHashSet<E> asLinkedSet(Collection<E> collection, Collection<E>... more) {
        return asCollection(LinkedHashSet::new, collection, more);
    }


    /**
     * Generates a new (mutable) concurrent HashSet out of a given number of (zero or more) element(s).
     *
     * @param elements Zero or more number of elements which will be wrapped in a new concurrent HashSet.
     * @param <E>      Type of given element(s).
     * @return  New (mutable) concurrent HashSet, built of the given varargs of element(s).
     */
    public static <E> Set<E> asConcurrentSet(E... elements) {
        return asCollectionVarargs(ConcurrentHashMap::newKeySet, elements);
    }

    /**
     * Generates a new (mutable) concurrent HashSet, merged from given (one or more) Collection(s).
     *
     * @param collection Collection instance which may contain element(s) of type E.
     * @param more       Zero or more number of collections which may contain element(s) of type E.
     * @param <E>        Type of given element(s).
     * @return  New (mutable) concurrent HashSet, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E> Set<E> asConcurrentSet(Collection<E> collection, Collection<E>... more) {
        return asCollection(ConcurrentHashMap::newKeySet, collection, more);
    }


    /**
     * Generates a new (mutable) ArrayList out of a given number of (zero or more) element(s).
     * Note that this method is different than Java's Arrays.asList(...) - which creates an unmodifiable list.
     *
     * @param elements Zero or more number of elements which will be wrapped in a new ArrayList.
     * @param <E>      Type of given element(s).
     * @return  New (mutable) ArrayList, built of the given varargs of element(s).
     */
    public static <E> ArrayList<E> asArrayList(E... elements) {
        return asCollectionVarargs(ArrayList::new, elements);
    }

    /**
     * Generates a new (mutable) ArrayList, merged from given (one or more) Collection(s).
     *
     * @param collection Collection instance which may contain element(s) of type E.
     * @param more       Zero or more number of collections which may contain element(s) of type E.
     * @param <E>        Type of given element(s).
     * @return  New (mutable) ArrayList, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E> ArrayList<E> asArrayList(Collection<E> collection, Collection<E>... more) {
        return asCollection(ArrayList::new, collection, more);
    }


    /**
     * Generates a new (mutable) LinkedList out of a given number of (zero or more) element(s).
     * Note that this method is different than Java's Arrays.asList(...) - which creates an unmodifiable list.
     *
     * @param elements Zero or more number of elements which will be wrapped in a new LinkedList.
     * @param <E>      Type of given element(s).
     * @return  New (mutable) LinkedList, built of the given varargs of element(s).
     */
    public static <E> LinkedList<E> asLinkedList(E... elements) {
        return asCollectionVarargs(LinkedList::new, elements);
    }

    /**
     * Generates a new (mutable) LinkedList, merged from given (one or more) Collection(s).
     *
     * @param collection Collection instance which may contain element(s) of type E.
     * @param more       Zero or more number of collections which may contain element(s) of type E.
     * @param <E>        Type of given element(s).
     * @return  New (mutable) LinkedList, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E> LinkedList<E> asLinkedList(Collection<E> collection, Collection<E>... more) {
        return asCollection(LinkedList::new, collection, more);
    }


    /**
     * Generic method for generating a new Collection, merged from a given number of (zero or more) Collection(s).
     * Note that this method is safe to use, as given Collections which are null will be skipped.
     *
     * @param collectionCtor Supplier of a constructor for a creating new implemented Collection (e.g. HashSet::new).
     * @param collection     Collection instance which may contain element(s) of type E.
     * @param more           Zero or more number of collections which may contain element(s) of type E.
     * @param <E>            Type of given element(s).
     * @param <C>            Type of the Collection which will be generated using the provided Supplier.
     * @return  New (implementation of a) Collection, merged from the given Collection(s).
     */
    @SafeVarargs
    public static <E, C extends Collection<E>> C asCollection(Supplier<C> collectionCtor,
                                                              Collection<E> collection,
                                                              Collection<E>... more) {
        C mergedCollection = collectionCtor.get();

        if(hasAtLeastOneElement(collection)) {
            mergedCollection.addAll(collection);
        }

        Arrays.stream(more)
                .filter(Collections::hasAtLeastOneElement)
                .forEach(mergedCollection::addAll);

        return mergedCollection;
    }

    /**
     * Generic method for generating a new Collection out of a given number of (zero or more) element(s).
     *
     * @param collectionCtor Supplier of a constructor for a creating new implemented Collection (e.g. HashSet::new).
     * @param elements       Zero or more number of elements which will be wrapped in a new Collection.
     * @param <E>            Type of given element(s).
     * @param <C>            Type of the Collection which will be generated using the provided Supplier.
     * @return  New (implementation of a) Collection, built of the given varargs of element(s).
     */
    @SafeVarargs
    public static <E, C extends Collection<E>> C asCollectionVarargs(Supplier<C> collectionCtor, E... elements) {
        C mergedCollection = collectionCtor.get();

        if(isNullOrEmpty(elements)) { return mergedCollection; }

        // Arrays.asList is O(1) : https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#asList%28T...%29
        mergedCollection.addAll(Arrays.asList(elements));

        return mergedCollection;
    }


    /**
     * Checks if the given varargs is either null or empty.
     *
     * @param elements Zero or more number of elements of type E.
     * @param <E>      Type of element(s).
     * @return  True iff given varargs is either null or empty.
     */
    public static <E> boolean isNullOrEmpty(E... elements) {
        return (elements == null || elements.length == 0);
    }

    /**
     * Checks if the given collection is either null or empty.
     *
     * @param collection Collection to be tested.
     * @param <E>        Type of element(s) in the Collection.
     * @return  True iff given collection is either null or empty.
     */
    public static <E> boolean isNullOrEmpty(Collection<E> collection) {
        return (collection == null || collection.size() == 0);
    }


    /**
     * Checks if the given varargs contains one or more elements (i.e. it is not null, nor it is empty).
     *
     * @param elements Zero or more number of elements of type E.
     * @param <E>      Type of element(s).
     * @return  True iff given varargs contains at least one element.
     */
    public static <E> boolean hasAtLeastOneElement(E... elements) {
        return (elements != null && elements.length > 0);
    }

    /**
     * Checks if the given collection contains one or more elements (i.e. it is not null, nor it is empty).
     *
     * @param collection Collection to be tested.
     * @param <E>        Type of element(s) in the Collection.
     * @return  True iff given collection contains at least one element.
     */
    public static <E> boolean hasAtLeastOneElement(Collection<E> collection) {
        return (collection != null && collection.size() > 0);
    }


    public static void main(String[] args) {
        // All static factory methods for generated collection types are safe (no NullPointerException will be raised).
        // Also, the generated collections are mutable.

        // Generates a LinkedList containing 1, 2 and 3
        List<Integer> integers = asLinkedList(1, 2, 3);

        // Generates a LinkedHashSet containing 3, 4, and 5 (maintaining the order of insertion)
        Set<Integer> integers2 = asLinkedSet(3, 4, 5);

        LinkedHashSet<Integer> integersUnion = asLinkedSet(integers, integers2);
        System.out.println(Arrays.deepToString(integersUnion.toArray())); // Prints [1, 2, 3, 4, 5]

        // Generates a HashSet for concurrent operations, containing "Hello" and 1
        Set<?> objects = asConcurrentSet("Hello", 1);
        System.out.println(Arrays.deepToString(objects.toArray())); // Prints either [1, Hello] or [Hello, 1]

        System.out.println(Arrays.deepToString(asSet().toArray())); // Prints the empty generated Set of Object(s) : []

        List<String> stringsWithNulls = Arrays.asList(null, "1", null, "2");
        System.out.println(Arrays.deepToString(asLinkedSet(stringsWithNulls).toArray())); // Prints [null, 1, 2]

        Deque<String> mergeCollections = asCollection(ArrayDeque::new,
                asSet("Hello", "World", "3", "2", "1"),
                asLinkedSet("A", "B"),
                asLinkedList("C", "D"),
                asArrayList("E", "F"));

        System.out.println(mergeCollections); // Prints [1, 2, 3, Hello, World, A, B, C, D, E, F]


        Queue<String> stringsQueue = asCollectionVarargs(PriorityQueue::new, "X", "Y", "Z");
        System.out.println(stringsQueue); // Prints [X, Y, Z]

        String[] emptyStringArr = new String[] { };
        System.out.println("Expecting False : " + hasAtLeastOneElement(emptyStringArr)); // Prints false
        System.out.println("Expecting False : " + hasAtLeastOneElement(asSet())); // asSet() generates an empty HashSet
        System.out.println("Expecting True : " + hasAtLeastOneElement(integers)); // Prints true

        System.out.println("Expecting True : " + isNullOrEmpty(asSet())); // asSet() generates an empty HashSet
        System.out.println("Expecting False : " + isNullOrEmpty(integers)); // Prints false
    }
}