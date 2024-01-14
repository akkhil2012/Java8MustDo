package org.example;

import java.util.Map;

class AssetBatch{
    private final Map<String, Asset> assetsWithExportState;

    public AssetBatch(Map<String, Asset> assetsWithExportState) {
        this.assetsWithExportState = assetsWithExportState;
    }


    @Override
    public String toString() {
        return "AssetBatch{" +
                "assetsWithExportState=" + assetsWithExportState +
                '}';
    }
}