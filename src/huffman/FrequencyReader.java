package huffman;

import io.BinaryReader;
import io.ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Read file, construct a heap and sort its characters
 *
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class FrequencyReader {

    /**
     * Caractère de fin de fichier
     */
    public static final char EOF = '\0';

    /**
     * Method to read a file and and get the its characters with their number of occurences
     *
     * @param A file (path)
     * @return an arrayList of treeElement, each element has its character and its frequency
     **/

    public ArrayList<TreeElement> readFile(String file) throws IOException {
        File f = new File(file);
        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int c = 0;

        Map<Character, Integer> frequencyMap = new HashMap<>();
        String line;
        Character character;
        ReadFile br = new ReadFile(file);

        //READ THE FILE LINE BY LINE
        while ((character = br.readChar()) != null) {
                // for each line check if is already readen, if true,
                // increase its frequency else add it to the array
                if (frequencyMap.containsKey(character)) {
                    int frequency = frequencyMap.get(character) + 1;
                    frequencyMap.put(character, frequency);
                } else
                    frequencyMap.put(character, 1);
        }
        ArrayList<TreeElement> frequencyArray = new ArrayList<>();
        for (Map.Entry m : frequencyMap.entrySet()) {
            TreeElement treeElement = new Leaf((Character) m.getKey(), (Integer) m.getValue());
            frequencyArray.add(treeElement);
        }
        frequencyArray.add(new Leaf(FrequencyReader.EOF, 1));
        return frequencyArray;
    }

    /**
     * Method to heap an ArrayList of TreeElements according to their frequencies
     *
     * @param An    arrayList of treeElements
     * @param size  of the array
     * @param index of root od a subtree
     * @return the arrayList represinting the heap
     **/

    public void heapifyTree(ArrayList<TreeElement> frequencyArray, int size, int index) {

        int indexOfGreater = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        int indexOfGreatestFrequency = frequencyArray.get(indexOfGreater).getFrequency();

        // If left child is greater than greater
        if (leftChildIndex < size && frequencyArray.get(leftChildIndex).getFrequency() > indexOfGreatestFrequency)
            indexOfGreater = leftChildIndex;

        indexOfGreatestFrequency = frequencyArray.get(indexOfGreater).getFrequency();
        // If right child is greater than greater so far
        if (rightChildIndex < size && frequencyArray.get(rightChildIndex).getFrequency() > indexOfGreatestFrequency)
            indexOfGreater = rightChildIndex;

        // If greatest is not root
        if (indexOfGreater != index) {
            TreeElement swap = frequencyArray.get(index);
            frequencyArray.set(index, frequencyArray.get(indexOfGreater));
            frequencyArray.set(indexOfGreater, swap);
            heapifyTree(frequencyArray, size, indexOfGreater);

        }
    }

    /**
     * Method to  build a heap of  TreeElements according to their frequencies
     *
     * @param An arrayList of treeElements
     * @return the arrayList representing the heap
     **/

    public ArrayList<TreeElement> buildHeap(ArrayList<TreeElement> frequencyArray) {

        // Index of last non-leaf node
        int startIndex = (frequencyArray.size() / 2) - 1;
        // begin from last non-leaf node and heapify
        // each node
        for (int i = startIndex; i >= 0; i--) {
            heapifyTree(frequencyArray, frequencyArray.size(), i);
        }
        return frequencyArray;
    }


    /**
     * Method to sort an ArrayList of TreeElements according to their frequencies
     *
     * @param An arrayList of treeElements
     * @return the arrayList sorted with heapsort method
     **/

    public ArrayList<TreeElement> heapSort(ArrayList<TreeElement> treeArray) {
        // build a heap from the array on parameters
        ArrayList<TreeElement> heapTree = buildHeap(treeArray);
        //browse the array starting by the last element
        // and each time swap the first element and the last one of the array
        // then heap the subtree
        for (int i = heapTree.size() - 1; i >= 0; i--) {
            TreeElement temp = heapTree.get(0);
            heapTree.set(0, heapTree.get(i));
            heapTree.set(i, temp);
            heapifyTree(heapTree, i, 0);
        }
        return heapTree;

    }

}
