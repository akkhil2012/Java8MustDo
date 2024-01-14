package org.example;

import java.util.Locale;

public class SingleTypeSummary {

    String entiyType;

    String pluginName;
    long succedded;
    long failed;
    long skipped;

    long succeeded;
    long partial;

    public void increaseSucceeded(long value) {
        succeeded += value;
    }

    public void increaseFailed(long value) {
        failed += value;
    }

    public void increaseSkipped(long value) {
        skipped += value;
    }

    public void increasePartial(long value) {
        partial += value;
    }


    public static SingleTypeSummaryBuilder builder(){
        return new SingleTypeSummaryBuilder();
    }


    static class SingleTypeSummaryBuilder{
        String entityType;

        String pluginName;

        public SingleTypeSummaryBuilder withPluginName(String pluginName){
             this.pluginName = pluginName;
             return  this;
        }

        public SingleTypeSummaryBuilder withEntityType(String entityType){
            this.entityType = entityType;
            return  this;
        }


        public SingleTypeSummary build(){
            SingleTypeSummary singleTypeSummary = new SingleTypeSummary();
            singleTypeSummary.pluginName=pluginName;
            singleTypeSummary.entiyType=entityType;
            return singleTypeSummary;
        }


    }


}
