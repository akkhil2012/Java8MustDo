package org.example;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class GenericJoinerCollector<Asset> implements Collector<Asset, Map<String,Asset>,AssetBatch> {

    private final String delimiter;

    public GenericJoinerCollector(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Supplier<Map<String,Asset>> supplier() {
        return HashMap::new;
        //return () -> new StringJoiner(delimiter);
    }

    @Override
    public BiConsumer<Map<String,Asset>, Asset> accumulator() {
        return (stringAssetMap, assetAndState) ->
                stringAssetMap.put("11", assetAndState);
    }

    @Override
    public BinaryOperator<Map<String,Asset>> combiner() {
        return (stringAssetMap, stringAssetMap2) -> {
            stringAssetMap.putAll(stringAssetMap2);
            return stringAssetMap;
        };
    }

    @Override
    public Function<Map<String, Asset>, AssetBatch> finisher() {
        HashMap m = new HashMap();
        return map -> {
            AssetBatch assetBatch = new AssetBatch(m);
            return assetBatch;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
