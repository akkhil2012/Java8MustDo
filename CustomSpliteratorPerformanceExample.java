import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliteratorPerformanceExample {
    static class Person {
        private String name;
        private int age;
        private String gender;

        public Person(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }
    }

    public static void main(String[] args) {

        // Create a large list of Person objects
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i <= 1_000_000; i++) {
            personList.add(new Person("Person" + i, i % 100, i % 2 == 0 ? "Male" : "Female"));
        }



        // Measure performance of parallel stream with custom spliterator
        long startTimeParallel = System.currentTimeMillis();
        double averageAgeParallel = calculateAverageAgeParallel(personList);
        long endTimeParallel = System.currentTimeMillis();

        System.out.println("Parallel Stream with Spliterator - Average Age: " + averageAgeParallel);
        System.out.println("Time taken (parallel): " + (endTimeParallel - startTimeParallel) + " milliseconds");
    }

    // Calculate average age using sequential stream
    private static double calculateAverageAgeSequential(List<Person> persons) {
        return persons.stream()
                .filter(p -> "Male".equals(p.getGender()))
                .mapToInt(Person::getAge)
                .average()
                .orElse(0.0);
    }

    // Calculate average age using parallel stream with custom spliterator
    private static double calculateAverageAgeParallel(List<Person> persons) {
        Spliterator<Person> personSpliterator = new PersonSpliterator(persons);
       // ResultSetSpliterator
        Stream<Person> personStream = StreamSupport.stream(personSpliterator, false);

        return personStream
                .filter(p -> "Male".equals(p.getGender()))
                .mapToInt(Person::getAge)
                .average()
                .orElse(0.0);
    }

    // Custom spliterator for the Person class
    static class PersonSpliterator implements Spliterator<Person> {
        private final List<Person> persons;
        private int currentIdx;

        public PersonSpliterator(List<Person> persons) {
            this.persons = persons;
            this.currentIdx = 0;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super Person> action) {
            if (currentIdx < persons.size()) {
                action.accept(persons.get(currentIdx++));
                return true;
            }
            return false;
        }

        @Override
        public java.util.Spliterator<Person> trySplit() {
            int currentSize = persons.size() - currentIdx;
            if (currentSize < 1000) {
                return null; // Stop splitting if the size is below a threshold
            }

            int splitSize = currentSize / 2;
            List<Person> splitList = persons.subList(currentIdx, currentIdx + splitSize);
            currentIdx += splitSize;

            return new PersonSpliterator(splitList);
        }

        @Override
        public long estimateSize() {
            return persons.size() - currentIdx;
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
        }
    }
}
