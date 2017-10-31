package com.example.lombokworkshop.model;

import java.io.Serializable;

public interface ModelWithExternalReference extends Serializable {

  ExternalRef getExternalRef();

  void setExternalRef(ExternalRef externalRef);
}
