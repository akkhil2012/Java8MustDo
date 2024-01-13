import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectorExample {

    public static void main(String args[]){

        List<String> stringList = Arrays.asList("apple", "banana", "cherry", "date");

        String  result =
        stringList.stream().collect(new StringJoinerCollector(", "));

        System.out.println("Concatenated list  " + result);

    }
}



class StringJoinerCollector implements Collector<String, StringJoiner,String>{


    private final String delimiter;

    public StringJoinerCollector(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Supplier<StringJoiner> supplier() {
        return () -> new StringJoiner(delimiter);
    }

    @Override
    public BiConsumer<StringJoiner, String> accumulator() {
        return StringJoiner::add;
    }

    @Override
    public BinaryOperator<StringJoiner> combiner() {
        return StringJoiner::merge;
    }

    @Override
    public Function<StringJoiner, String> finisher() {
        return StringJoiner::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}


