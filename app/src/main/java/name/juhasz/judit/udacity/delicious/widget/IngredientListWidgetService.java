package name.juhasz.judit.udacity.delicious.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientListWidgetService extends RemoteViewsService {

    public static final String RECIPE_DATA = "RECIPE_DATA";

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new IngredientListRemoteViewsFactory(getApplicationContext());
    }
}
