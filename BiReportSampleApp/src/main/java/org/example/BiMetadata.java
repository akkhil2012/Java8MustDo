package org.example;

public class BiMetadata extends AssetMetadata{

    public static BiMetadata of(String assetId) {
        return BiMetadata.builder().assetId(assetId).resourceKey("").build();
    }


    static BiMetadataBuilder builder(){
        return new BiMetadataBuilder();
    }


    static  class BiMetadataBuilder{
        private  String assetId;
        private String resourceKey;


        public BiMetadataBuilder assetId(String assetId){
            this.assetId = assetId;
            return  this;
        }

        public BiMetadataBuilder resourceKey(String resourceKey){
            this.resourceKey = resourceKey;
            return  this;
        }


        public BiMetadata build(){
            BiMetadata biMetadata = new BiMetadata();
            biMetadata.assetId = assetId;
            biMetadata.resourceKey = resourceKey;
            return biMetadata;
        }


    }




}


