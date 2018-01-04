package name.juhasz.judit.udacity.delicious.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Recipe;

public class IngredientListWidgetProvider extends AppWidgetProvider {

    public static void updateAllWidgets(final Context context, final Recipe recipe) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        final String json = new Gson().toJson(recipe);
        sharedPreferences.edit().putString("recipe_on_widget", json).apply();
        final Class<IngredientListWidgetProvider> widgetProviderClass
                = IngredientListWidgetProvider.class;
        final Intent updateWidgetIntent = new Intent(context, widgetProviderClass);
        updateWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        final int[] appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, widgetProviderClass));
        updateWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(updateWidgetIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            final AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            final ComponentName componentName =
                    new ComponentName(context, IngredientListWidgetProvider.class);
            widgetManager.notifyAppWidgetViewDataChanged(
                    widgetManager.getAppWidgetIds(componentName), R.id.lv_widget_ingredients);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(final Context context,
                         final AppWidgetManager appWidgetManager,
                         final int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            final Recipe recipe = getSelectedRecipe(context);
            final RemoteViews views = getRemoteViews(context);
            if (null != recipe) {
                showIngredientList(views, context, appWidgetId, recipe);
            } else {
                showEmptyMessage(views);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void showIngredientList(final RemoteViews views,
                                    final Context context,
                                    final int appWidgetId,
                                    final Recipe recipe) {
        views.setViewVisibility(R.id.tv_empty_widget, View.GONE);

        views.setViewVisibility(R.id.tv_widget_title, View.VISIBLE);
        views.setTextViewText(R.id.tv_widget_title, recipe.getName());

        views.setViewVisibility(R.id.lv_widget_ingredients, View.VISIBLE);
        final Intent serviceIntent = new Intent(context, IngredientListWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lv_widget_ingredients, serviceIntent);
    }

    private void showEmptyMessage(final RemoteViews views) {
        views.setViewVisibility(R.id.tv_widget_title, View.GONE);
        views.setViewVisibility(R.id.lv_widget_ingredients, View.GONE);
        views.setViewVisibility(R.id.tv_empty_widget, View.VISIBLE);
    }

    private RemoteViews getRemoteViews(final Context context) {
        return new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
    }

    private Recipe getSelectedRecipe(final Context context) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        final String recipeJson = sharedPreferences.getString("recipe_on_widget",null);
        return (null == recipeJson) ? null : new Gson().fromJson(recipeJson, Recipe.class);
    }
}
