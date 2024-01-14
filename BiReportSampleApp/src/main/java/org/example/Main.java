package org.example;

import java.util.Collection;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    public void export(){

        exportReport();
    }



    private void exportReport(){
        SingleTypeSummary biReportSummary =
        SingleTypeSummary.builder().withEntityType("reportExporter").withEntityType("BI Report").build();
        exportAssetListAndWriteStatus("BI reports.",DataQuery.getAllReports().values(),biReportSummary);

    }


    private <T extends AssetProperty> void exportAssetListAndWriteStatus(String type, Collection<T> assets, SingleTypeSummary singleTypeSummary){
        assets.stream()
                .filter(asset ->{
                    if(asset.getMetadata().getResourceKey().isEmpty()){
                        singleTypeSummary.increaseFailed(1);
                        return false;
                    }
                    return  true;
                })
                .collect(Collectors.toList());

    }


}












