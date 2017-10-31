package com.example.lombokworkshop.model;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class LocalizedMessage implements ILocalizedMessage {

  private String messageKey;
  private Object[] args;

  public LocalizedMessage(String messageKey, Object... args) {
    this.setMessageKey(messageKey);
    this.setArgs(args);
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  @Override
  public String getMessageFrom(ResourceBundle bundle) {
    String localizedMessage = bundle.getString(getMessageKey());
    if (getArgs() != null) {
      localizedMessage = MessageFormat.format(localizedMessage, getArgs());
    }
    return localizedMessage;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("key: " + messageKey);
    if (args.length > 0) {
      StringJoiner sj = new StringJoiner(", ", " with {", "}");
      for (int i = 0; i < args.length; i++) {
        sj.add(args[i].toString());
      }
      sb.append(sj.toString());
    }
    return sb.toString();
  }
}
