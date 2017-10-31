package com.example.lombokworkshop.model;

import java.util.ResourceBundle;

public interface ILocalizedMessage {

  String getMessageFrom(ResourceBundle bundle);

  @Override
  String toString();

  String getMessageKey();
}
