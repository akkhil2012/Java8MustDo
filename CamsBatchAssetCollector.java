import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CamsBatchAssetCollector implements Collector<AssetWithExportState, Map<String,AssetWithExportState>,AssetBatch> {

    @Override
    public Supplier<Map<String, AssetWithExportState>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<Map<String, AssetWithExportState>, AssetWithExportState> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<Map<String, AssetWithExportState>> combiner() {
        return null;
    }

    @Override
    public Function<Map<String, AssetWithExportState>, AssetBatch> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
