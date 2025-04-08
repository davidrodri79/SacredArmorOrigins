package com.activeminds.sao;

import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.Gdx;

import java.util.Locale;

public class LocalizationManager {
    private I18NBundle bundle;

    public void loadDefaultLocale() {
        Locale locale = Locale.getDefault(); // o puedes forzar uno, ej: new Locale("es");
        bundle = I18NBundle.createBundle(Gdx.files.internal("strings"), locale);
    }

    public String get(String key) {
        return bundle.get(key);
    }

    // Cambiar idioma manualmente
    public void setLocale(Locale locale) {
        bundle = I18NBundle.createBundle(Gdx.files.internal("strings"), locale);
    }
}
