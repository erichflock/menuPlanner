package de.tdsoftware.liebstoeckel.web;

import de.tdsoftware.liebstoeckel.model.Menu;

/**
 * Created by erich on 16/10/17.
 */

public interface LoadModelTaskCallback {
    void onModelLoaded(Menu menu);
}
