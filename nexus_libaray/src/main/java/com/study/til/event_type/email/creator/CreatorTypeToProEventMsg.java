package com.study.til.event_type.email.creator;

import com.study.til.event_type.email.EmailEventMsg;

public class CreatorTypeToProEventMsg implements EmailEventMsg<CreatorTypeToProEventData> {
  private CreatorTypeToProEventData eventData;

  private CreatorTypeToProEventMsg() {}

  public CreatorTypeToProEventMsg(final CreatorTypeToProEventData eventData) {
    this.eventData = eventData;
  }

  @Override
  public CreatorTypeToProEventData getEventData() {
    return eventData;
  }

  @Override
  public String toString() {
    return "CreatorTypeToProEventMsg{" +
        "eventData=" + eventData +
        '}';
  }
}
