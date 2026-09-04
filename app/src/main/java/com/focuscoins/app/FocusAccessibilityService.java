package com.focuscoins.app;

import android.accessibilityservice.AccessibilityService; import android.content.Intent; import android.os.*; import android.view.accessibility.AccessibilityEvent;

public class FocusAccessibilityService extends AccessibilityService {
 private static volatile FocusAccessibilityService active; private final Handler handler=new Handler(Looper.getMainLooper()); private String pendingPackage="";
 @Override protected void onServiceConnected(){super.onServiceConnected();active=this;FocusService.start(this);} public static boolean showRecents(){FocusAccessibilityService s=active;return s!=null&&s.performGlobalAction(GLOBAL_ACTION_RECENTS);}
 @Override public void onAccessibilityEvent(AccessibilityEvent event){if(event.getEventType()!=AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED||event.getPackageName()==null)return;pendingPackage=event.getPackageName().toString();handler.removeCallbacksAndMessages(null);handler.postDelayed(()->{String pkg=pendingPackage;Intent i=new Intent(this,FocusService.class).setAction(FocusService.ACTION_FOREGROUND).putExtra("pkg",pkg);if(Build.VERSION.SDK_INT>=26)startForegroundService(i);else startService(i);},450);}
 @Override public void onInterrupt(){}
 @Override public boolean onUnbind(Intent intent){handler.removeCallbacksAndMessages(null);if(active==this)active=null;return super.onUnbind(intent);}
}
