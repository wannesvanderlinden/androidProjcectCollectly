package com.example.androidprojcectcollectly

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class CollectionListWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val widgetText = context.getString(R.string.appwidget_text)


    //getting the data and format it to a specific
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    val formattedDate = df.format(c)
    val intent = Intent(context,MainActivity::class.java)

    //Open app when the widget is been clicked
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.collection_list_widget).apply {
        setOnClickPendingIntent(R.id.date_text, pendingIntent)
    }
    views.setTextViewText(R.id.date_text,formattedDate)

        // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
