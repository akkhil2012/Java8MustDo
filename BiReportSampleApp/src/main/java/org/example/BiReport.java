package org.example;

import java.util.List;

public class BiReport extends  BIAssetWthMetaData{


    private transient List<String> reportDataSets;
    private BaseEntity entity = new BIReportEntity();

    public BiReport(String id) {
        metadata = BiMetadata.of(id);
        metadata.setAssetType("BiReport"); //(Constants.REPORT_ASSET_TYPE);
        metadata.setOriginCountry("origina Country");//(OriginCountry.getOriginCountry());
    }

    @Override
    public BiMetadata getMetadata() {
        return null;
    }

    @Override
    public BaseEntity getEntity() {
        return null;
    }

    @Override
    public List<Attachment> getAttachments() {
        return null;
    }

    @Override
    public void setAttachments(List<Attachment> attachments) {

    }

    @Override
    public String getParent() {
        return null;
    }
}
