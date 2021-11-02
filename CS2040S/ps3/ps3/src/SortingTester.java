import java.util.Random;

public class SortingTester {

    // checkSort determines if the specified sorting algorithm sorts correctly.
    // getKey() from each KeyValuePair
    // example input: checkSort(new SorterA(), 5);
    // sorter.sort(testArr) --> supposedly gives the sorted array
    public static boolean checkSort(ISort sorter, int size) {
        //TODO: implement this
        KeyValuePair[] testRandomArr = randomArray(size);
        sorter.sort(testRandomArr);
        for (int i = 0; i < size - 1; i++) {
            if (testRandomArr[i].getKey() > testRandomArr[i+1].getKey()) {
                // System.out.println(Arrays.toString(testRandomArr));
                return false;
            }
        }
        // System.out.println(Arrays.toString(testRandomArr));
        return true;
    }

    // isStable determine if the specified sorting algorithm is stable.
    // getValue() from each KeyValuePair
    public static boolean isStable(ISort sorter, int size) {
        //TODO: implement this
        KeyValuePair[] testRandomArr = randomArray(size);
        sorter.sort(testRandomArr);
        for (int i = 0; i < size - 1; i++) {
            if ((testRandomArr[i].getKey() == testRandomArr[i+1].getKey()) &&
                    (testRandomArr[i].getValue() > testRandomArr[i+1].getValue())) {
                // System.out.println(Arrays.toString(testRandomArr));
                return false;
            }
        }
        // System.out.println(Arrays.toString(testRandomArr));
        return true;
    }

    public static KeyValuePair[] randomArray(int size){
        Random rng = new Random();
        KeyValuePair[] randomArr = new KeyValuePair[size];
        for(int i = 0; i < size; i++) {
            randomArr[i] = new KeyValuePair(rng.nextInt(10), i); // rng.nextInt() to test Dr. Evil
        }
        return randomArr;
    }

    public static KeyValuePair[] sortedArray(int size){
        KeyValuePair[] arr = new KeyValuePair[size];
        for(int i = 0; i < size; i++) {
            arr[i] = new KeyValuePair(i, i);
        }
        return arr;
    }

    public static KeyValuePair[] revSortedArray(int size){
        KeyValuePair[] arr = new KeyValuePair[size];
        for(int i = 0; i < size; i++) {
            arr[i] = new KeyValuePair(size - 1 - i, i);
        }
        return arr;
    }

    public static void main(String[] args) {
        //TODO: implement this

        // Create a stopwatch
        StopWatch watch = new StopWatch();
        ISort sortingObjectA = new SorterA();
        ISort sortingObjectB = new SorterB();
        ISort sortingObjectC = new SorterC();
        ISort sortingObjectD = new SorterD();
        ISort sortingObjectE = new SorterE();
        ISort sortingObjectF = new SorterF();


        // Do sorting: n = 2000;
        watch.start();
        System.out.println("checkSort A random = " + checkSort(sortingObjectA, 2000));
        System.out.println("isStable A random = " + isStable(sortingObjectA, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort B random = " + checkSort(sortingObjectB, 2000));
        System.out.println("isStable B random = " + isStable(sortingObjectB, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort C random = " + checkSort(sortingObjectC, 2000));
        System.out.println("isStable C random = " + isStable(sortingObjectC, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort D random = " + checkSort(sortingObjectD, 2000));
        System.out.println("isStable D random = " + isStable(sortingObjectD, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort E random = " + checkSort(sortingObjectE, 2000));
        System.out.println("isStable E random = " + isStable(sortingObjectE, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort F random = " + checkSort(sortingObjectF, 2000));
        System.out.println("isStable F random = " + isStable(sortingObjectF, 2000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        // Do the sorting: n = 40000
        watch.start();
        System.out.println("checkSort A random = " + checkSort(sortingObjectA, 40000));
        System.out.println("isStable A random = " + isStable(sortingObjectA, 40000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort B random = " + checkSort(sortingObjectB, 40000));
        System.out.println("isStable B random = " + isStable(sortingObjectB, 40000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort C random = " + checkSort(sortingObjectC, 40000));
        System.out.println("isStable C random = " + isStable(sortingObjectC, 40000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();
        /* Output
        size = 40000;
        checkSort C random = false
        isStable C random = false
        Time: 0.0888664
         */

        watch.start();
        System.out.println("checkSort D random = " + checkSort(sortingObjectD, 40000));
        System.out.println("isStable D random = " + isStable(sortingObjectD, 40000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        watch.start();
        System.out.println("checkSort E random = " + checkSort(sortingObjectE, 4000));
        System.out.println("isStable E random = " + isStable(sortingObjectE, 4000));
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();

        KeyValuePair[] nealySortedArr1 = {new KeyValuePair(2,2), new KeyValuePair(3,3),
                new KeyValuePair(4,4), new KeyValuePair(5,5), new KeyValuePair(1,1)};
        KeyValuePair[] nealySortedArr2 = {new KeyValuePair(2,2), new KeyValuePair(3,3),
                new KeyValuePair(4,4), new KeyValuePair(5,5), new KeyValuePair(1,1)};
        KeyValuePair[] nealySortedArr3 = {new KeyValuePair(2,2), new KeyValuePair(3,3),
                new KeyValuePair(4,4), new KeyValuePair(5,5), new KeyValuePair(1,1)};

        // Differentiating Insertion, Merge and Bubble sorters
        watch.start();
        sortingObjectB.sort(nealySortedArr1);
        watch.stop();
        System.out.println("Time for B: " + watch.getTime());
        watch.reset();
        // Time for B: 4.45E-5

        watch.start();
        sortingObjectE.sort(nealySortedArr2);
        watch.stop();
        System.out.println("Time for E: " + watch.getTime());
        watch.reset();
        // Time for E: 5.8001E-5

        watch.start();
        sortingObjectF.sort(nealySortedArr3);
        watch.stop();
        System.out.println("Time for F: " + watch.getTime());
        watch.reset();
        // Time for F: 1.687E-4

        // Differentiating Insertion and Merge Sorts
        KeyValuePair[] revArr1 = revSortedArray(40000);
        KeyValuePair[] revArr2 = revSortedArray(40000);

        watch.start();
        sortingObjectB.sort(revArr1);
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();
        // Time: 9.2814445
        // Takes a longer time to sort a reversely sorted array --> B = insertion sort

        watch.start();
        sortingObjectE.sort(revArr2);
        watch.stop();
        System.out.println("Time: " + watch.getTime());
        watch.reset();
        // Time: 0.365385
        // Takes a shorter time to sort a reversely sorted array --> E = merge sort
    }
}

/*
By restring the range of the random integers to 10 (or any number less than 2000) and setting the array size to 2000,
there will always be repeated elements in the array (by Pigeon Hole Principle).
As such, the stability of the arrays can be determined.
Sorter A is able to sort the array and is unstable.
Sorter B is able to sort the array and is stable.

Sorter C is able to sort the array for small array sizes but gives unsorted array for large array size (I got unsorted array for n = 40000).
Similarly, C is stable for small array size but unstable for large array size. It can be concluded that C is Dr. Evil

Sorter D is able to sort the array and is unstable.
Sorter E is able to sort the array and is stable.
Sorters B and E take approximately the same amount of time (0.06s) to sort the given array of size 2000.
However, for a reversely sorted array (worst case scenario) with a larger size of 40,000, B takes about 9s while E takes about 0.4s to sort.
It can be inferred that E has a smaller asymptotic running time compared to B
and it is likely to be merge sort (O(n log n)) while B is likely to be insertion sort(O(n^2)).

Sorter F is able to sort the array and is stable.
F takes a significantly longer time to sort the array input even for small array size of 2000.
To illustrate, sorters A to E takes less than 1 sec on average to sort the given array while F takes about 10s to sort the array.
Therefore, F is likely to be bubble sort which has the slowest asymptotic running time in practical.

The only unstable algorithms which we have learnt are selection sort and quick sort.
Sorter A takes a longer time to sort a smaller array of size 2000 than sorter D.
However, comparing the time taken for sorters A and D to sort a larger random array size of 40,000,
A (1.5s) takes a considerably shorter time (approximately 3 times) than D (4.5s).
In addition, the percentage increase in time taken for sorting from size 2000 to size 40,000 is much larger for D (~33,000%) than A(~500%).
This shows that the time taken for A increases at a slower rate and hence a smaller asymptotic running time than D.
Therefore, A is quick sort (O(n log n) for average case) while D is selection sort (O(n^2)).

 */