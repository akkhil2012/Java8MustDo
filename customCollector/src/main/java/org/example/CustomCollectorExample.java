package org.example;

import java.util.List;

public class CustomCollectorExample {

    public static void main(String args[]) {
        //  List<String> stringList = Arrays.asList("apple", "banana", "cherry", "date");
        //List<Integer> intList = Arrays.asList(1,2,3,4,5);
        List<Asset> assetsList = List.of(new Asset("A",1),new Asset("B",2),
                new Asset("C",3),new Asset("D",4));

        GenericJoinerCollector<Asset> collector = new GenericJoinerCollector<>(" | ");

        //String result = Stream.of(1, 2, 3, 4, 5)
        //      .collect(collector);

        AssetBatch result = assetsList.stream().collect(collector);


        System.out.println("Concatenated list: "+ result.toString());



    }
}
