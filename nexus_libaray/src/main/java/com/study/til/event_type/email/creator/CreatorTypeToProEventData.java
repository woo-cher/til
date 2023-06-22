package com.study.til.event_type.email.creator;

import com.study.til.event_type.email.EmailEventData;

public class CreatorTypeToProEventData implements EmailEventData {
  private String originalId;
  private String receiver;
  private String creatorName;

  private CreatorTypeToProEventData() {}

  public CreatorTypeToProEventData(final String originalId, final String receiver, final String creatorName) {
    this.originalId = originalId;
    this.receiver = receiver;
    this.creatorName = creatorName;
  }

  @Override
  public String getOriginalId() {
    return originalId;
  }
  @Override
  public String getReceiver() {
    return receiver;
  }
  public String getCreatorName() {
    return creatorName;
  }

  @Override
  public String toString() {
    return "CreatorTypeToProEventData{" +
        "originalId='" + originalId + '\'' +
        ", receiver='" + receiver + '\'' +
        ", nickName='" + creatorName + '\'' +
        '}';
  }
}
