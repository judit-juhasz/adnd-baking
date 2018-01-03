package name.juhasz.judit.udacity.delicious.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new IngredientListRemoteViewsFactory(getApplicationContext());
    }
}
