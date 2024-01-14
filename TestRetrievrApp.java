import java.sql.Connection;
import java.util.Map;
import java.util.stream.Stream;

public class TestRetrievrApp<T,B{


    private final EdsRetriever<T, B> retriever;

    public TestRetrievrApp(EdsRetriever<T, B> retriever) {
        this.retriever = retriever;
    }

    public static void main(String args[]) {
        new TestRetrievrApp().testRetriver(Map.of());
    }


    //case 2:
    // retriver part
    public void testRetriver(){
        Connection connection;
        retriever.retrieve(connection, Stream->
                chunked(stream.map(eds->this.t))
                )

    }



}
