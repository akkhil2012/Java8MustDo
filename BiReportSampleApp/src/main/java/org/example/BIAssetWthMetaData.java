package org.example;

import java.util.List;

public abstract class BIAssetWthMetaData implements ParentAware, AssetProperty,ContextAware{
    protected BiMetadata metadata;
    protected List<Attachment> attachments;
    protected transient String parent;

    @Override
    public String getName() {
        return getMetadata().getName();
    }

    @Override
    public String getContext() {
        return metadata.getResourceKey();
    }

    @Override
    public void setContext(String context) {
        metadata.setResourceKey(context);
    }


}
