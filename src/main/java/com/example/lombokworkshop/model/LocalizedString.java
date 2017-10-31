package com.example.lombokworkshop.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

@NoArgsConstructor
public class LocalizedString extends HashMap<String, String> {

  private static final long serialVersionUID = -3396296944446450693L;

  public LocalizedString(String label) {
    this.put(LocaleContextHolder.getLocale().getLanguage(), label);
  }

  public LocalizedString with(String locale, String label) {
    this.put(locale, label);
    return this;
  }

  public LocalizedString with(Locale locale, String label) {
    return with(locale.getLanguage(), label);
  }

  @Override
  public String get(Object key) {
    if (key instanceof Locale) {
      Locale locale = (Locale) key;
      String label = super.get(key);
      if (label == null) {
        return super.get(locale.getLanguage());
      }
      return label;

    } else {
      return super.get(key);

    }
  }

  public Map<String, String> asMap() {
    return new HashMap<>(this);
  }

  public LocalizedString stripAccent() {
    LocalizedString newLocalizedString = new LocalizedString();
    this.forEach((k, v) -> {
      newLocalizedString.put(k, StringUtils.stripAccents(v));
    });
    return newLocalizedString;
  }
}
