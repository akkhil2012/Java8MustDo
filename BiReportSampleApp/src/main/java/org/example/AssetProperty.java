package org.example;

import java.util.List;

public interface AssetProperty {

    BiMetadata getMetadata();

    BaseEntity getEntity();

    List<Attachment> getAttachments();

    void setAttachments(List<Attachment> attachments);
}
