package test;

import huffman.FrequencyReader;
import huffman.Leaf;
import huffman.TreeElement;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class FrequencyReaderTest {

    @Test
    public void should_return_all_characters_when_calling_read_file() throws IOException {
        //ARRANGE
        FrequencyReader frequencyReader = new FrequencyReader();
        String file = "compressed/fichier1.txt";
        //ACT
        ArrayList<TreeElement> actualResult = frequencyReader.readFile(file);
        for (TreeElement treeElement : actualResult) {
            System.out.println("character: " + treeElement.getValue() + ", frequency: " + treeElement.getFrequency());
        }

    }


    @Test
    public void should_have_heap_tree_when_calling_build_heap() {
        //ARRANGE
        FrequencyReader frequencyReader = new FrequencyReader();
        ArrayList<TreeElement> myList = new ArrayList<>();

        myList.add(new Leaf('*', 1));
        myList.add(new Leaf(')', 3));
        myList.add(new Leaf('E', 5));
        myList.add(new Leaf('2', 4));
        myList.add(new Leaf('d', 6));
        myList.add(new Leaf('b', 13));
        myList.add(new Leaf('1', 10));
        myList.add(new Leaf('c', 9));
        myList.add(new Leaf('(', 8));
        myList.add(new Leaf('a', 15));
        myList.add(new Leaf(' ', 17));


        ArrayList<TreeElement> expectedResult = new ArrayList<>();
        expectedResult.add(new Leaf(' ', 17));
        expectedResult.add(new Leaf('a', 15));
        expectedResult.add(new Leaf('b', 13));
        expectedResult.add(new Leaf('c', 9));
        expectedResult.add(new Leaf('d', 6));
        expectedResult.add(new Leaf('E', 5));
        expectedResult.add(new Leaf('1', 10));
        expectedResult.add(new Leaf('2', 4));
        expectedResult.add(new Leaf('(', 8));
        expectedResult.add(new Leaf(')', 3));
        expectedResult.add(new Leaf('*', 1));

        //ACT
        ArrayList<TreeElement> actualResult = frequencyReader.buildHeap(myList);

        //ASSERT
        Assert.assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }

    @Test
    public void should_return_sorted_arraylist_when_calling_heapsort() {
        //ARRANGE
        FrequencyReader frequencyReader = new FrequencyReader();

        ArrayList<TreeElement> myList = new ArrayList<>();
        myList.add(new Leaf('*', 5));
        myList.add(new Leaf('*', 6));
        myList.add(new Leaf('1', 10));
        myList.add(new Leaf('1', 18));
        myList.add(new Leaf('d', 60));
        myList.add(new Leaf('2', 17));
        myList.add(new Leaf('c', 9));
        myList.add(new Leaf('*', 25));
        myList.add(new Leaf('d', 8));


        ArrayList<TreeElement> expectedResult = new ArrayList<>();
        expectedResult.add(new Leaf('*', 5));
        expectedResult.add(new Leaf('*', 6));
        expectedResult.add(new Leaf('d', 8));
        expectedResult.add(new Leaf('c', 9));
        expectedResult.add(new Leaf('1', 10));
        expectedResult.add(new Leaf('2', 17));
        expectedResult.add(new Leaf('1', 18));
        expectedResult.add(new Leaf('*', 25));
        expectedResult.add(new Leaf('d', 60));


        //ACT
        ArrayList<TreeElement> actualResult = frequencyReader.heapSort(myList);

        //ASSERT
        Assert.assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }

}
