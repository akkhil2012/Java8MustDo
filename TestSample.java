import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SimpleStreamSpliter {

    public static void main(String[] args) {


        //case1: Basic native splitter
        List<String> lst = new ArrayList<>();
        lst.add("akkhil");
        lst.add("kumar");
        lst.add("gupta");
        Spliterator<String> spliterator = lst.spliterator();
        Stream<String>  stream = StreamSupport.stream(spliterator,false);
        stream.map(String::toUpperCase)
                .forEach(System.out::println);


    }



}
