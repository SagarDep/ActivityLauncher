/**
 * Based on code from Stackoverflow.com under CC BY-SA 3.0
 * Url: http://stackoverflow.com/questions/6493518/create-a-shortcut-for-any-app-on-desktop
 * By:  http://stackoverflow.com/users/815400/xuso
 * 
 * and
 * 
 * Url: http://stackoverflow.com/questions/3298908/creating-a-shortcut-how-can-i-work-with-a-drawable-as-icon
 * By:  http://stackoverflow.com/users/327402/waza-be
 */

package org.thirdparty;

import de.szalkowski.activitylauncher.MyActivityInfo;
import de.szalkowski.activitylauncher.MyPackageInfo;
import de.szalkowski.activitylauncher.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;

import android.support.v4.graphics.drawable.IconCompat;
import android.util.Log;
import android.widget.Toast;

public class LauncherIconCreator {
	public static Intent getActivityIntent(ComponentName activity) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.setComponent(activity);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		return intent;
	}

	public static void createLauncherIcon(Context context, MyActivityInfo activity) {
		final String pack = activity.getIconResouceName().substring(0, activity.getIconResouceName().indexOf(':'));


		Log.d("LauncherIconCreator", activity.getComponentName().flattenToString());
		Log.d("LauncherIconCreator", activity.getName());
		Log.d("LauncherIconCreator", activity.getIconResouceName());

		// Use bitmap version if icon from different package is used
//		if(!pack.equals(activity.getComponentName().getPackageName())) {
//			createLauncherIcon(context, activity.getComponentName(), activity.getName(), activity.getIcon());
//		} else {
//			createLauncherIcon(context, activity.getComponentName(), activity.getName(), activity.getIconResouceName());
//		}

		IconCompat icon;

//		if(pack.equals(activity.getComponentName().getPackageName())) {
//			icon = null;
//
//		} else {
//			icon = IconCompat.createWithBitmap(drawableToBitmapDrawable(context, activity.getIcon()).getBitmap());
//
//		}


		icon = IconCompat.createWithBitmap(drawableToBitmapDrawable(context, activity.getIcon()).getBitmap());

		Intent intent = getActivityIntent(activity.getComponentName());


		createShortcut(context, activity.getName(), intent, icon);
	}

//	public static void createLauncherIcon(Context context, MyPackageInfo pack) {
//		Intent intent = context.getPackageManager().getLaunchIntentForPackage(pack.getPackageName());
//		createLauncherIcon(context, intent, pack.getName(), pack.getIconResourceName());
//
//		createShortcut(context, pack.getName(), intent, false, null, pack.getIconResourceName());
//
//	}

	public static void createShortcut(Context context, String name, Intent intent, IconCompat icon) {

		ComponentName cname = intent.getComponent();
		if (cname==null) return;

		Log.d("ActivityLauncher", String.format(context.getText(R.string.creating_activity_shortcut).toString(), cname.flattenToShortString()));
		Toast.makeText(context, String.format(context.getText(R.string.creating_activity_shortcut).toString(), cname.flattenToShortString()), Toast.LENGTH_LONG).show();
		if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
			String shortcutid = "sc_" + intent.getComponent();

			ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(context, shortcutid)
					.setIntent(intent)
					.setShortLabel(name)
					.setIcon(icon)
					.build();


			//scbuilder.setIcon(IconCompat.createWithBitmap(drawableToBitmapDrawable(context, icon).getBitmap()));

			ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null);
		}
	}

//	public static void createLauncherIcon(Context context, ComponentName activity, String name, Drawable icon) {
//		Toast.makeText(context, String.format(context.getText(R.string.creating_activity_shortcut).toString(), activity.flattenToShortString()), Toast.LENGTH_LONG).show();
//
//	    Intent shortcutIntent = new Intent();
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getActivityIntent(activity));
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//	    Bitmap bm = drawableToBitmapDrawable(context, icon).getBitmap();
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bm);
//	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//	    context.sendBroadcast(shortcutIntent);
//	    //finish();
//	}
//
//	public static void createLauncherIcon(Context context, ComponentName activity, String name, String icon_resource_name) {
//		Toast.makeText(context, String.format(context.getText(R.string.creating_activity_shortcut).toString(), activity.flattenToShortString()), Toast.LENGTH_LONG).show();
//
//	    Intent shortcutIntent = new Intent();
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getActivityIntent(activity));
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//	    Intent.ShortcutIconResource ir = new Intent.ShortcutIconResource();
//	    ir.packageName = activity.getPackageName();
//	    ir.resourceName = icon_resource_name;
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ir);
//	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//	    context.sendBroadcast(shortcutIntent);
//	    //finish();
//	}
//
////
////
//	public static void createLauncherIcon(Context context, Intent intent, String name, String icon_resource_name) {
//		Toast.makeText(context, String.format(context.getText(R.string.creating_application_shortcut).toString(), name), Toast.LENGTH_LONG).show();
//
//	    Intent shortcutIntent = new Intent();
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//	    Intent.ShortcutIconResource ir = new Intent.ShortcutIconResource();
//	    ir.packageName = intent.getPackage();
//	    ir.resourceName = icon_resource_name;
//	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ir);
//	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//	    context.sendBroadcast(shortcutIntent);
//	    //finish();
//	}
	
	public static void launchActivity(Context context, ComponentName activity) {
		Intent intent = LauncherIconCreator.getActivityIntent(activity);
		Toast.makeText(context, String.format(context.getText(R.string.starting_activity).toString(), activity.flattenToShortString()), Toast.LENGTH_LONG).show();
		try {
			context.startActivity(intent);
		}
		catch(Exception e) {
			Toast.makeText(context, context.getText(R.string.error).toString() + ": " + e.toString(), Toast.LENGTH_LONG).show();
		}

	}

	public static BitmapDrawable drawableToBitmapDrawable (Context context, Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return (BitmapDrawable)drawable;
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return new BitmapDrawable(context.getResources(), bitmap);
	}
}
