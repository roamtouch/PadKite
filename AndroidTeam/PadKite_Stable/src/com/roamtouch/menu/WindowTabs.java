package com.roamtouch.menu;


import roamtouch.webkit.WebView;
import com.roamtouch.floatingcursor.FloatingCursor;
import com.roamtouch.swiftee.BrowserActivity;
import com.roamtouch.swiftee.R;
import com.roamtouch.swiftee.SwifteeApplication;
import com.roamtouch.swiftee.SwifteeHelper;
import com.roamtouch.view.EventViewerArea;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class WindowTabs extends CircularTabsLayout implements OnClickListener{

//	private FloatingCursor mFloatingCursor;
	private BrowserActivity mParent;
	private Context mContext;
	private EventViewerArea eventViewer;;
	private static int currentTab = 2;

	public WindowTabs(Context context) {
		super(context);
				
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.window_tabs, this);
		
	
		init();
		int count = getChildCount();
		for(int i=1;i<count;i++ ){
			View v = getChildAt(i);
			v.setOnClickListener(this);
		}		
	}

	public void setTab(WebView wv){
		TabButton tab1 = (TabButton) findViewById(R.id.Tab1);
		tab1.setId(0);
		tab1.setWebView(wv);
		setActiveTabIndex(tab1);
	}
	public void setFloatingCursor(FloatingCursor mFloatingCursor) {
//		this.mFloatingCursor = mFloatingCursor;
	}
	public void setParent(BrowserActivity parent){
		mParent = parent;		
	}
	
	public void setCurrentThumbnail(BitmapDrawable bd,WebView wv){
		int count = getChildCount();
		for(int i =2;i<count-3;i++){
			TabButton tab = (TabButton) getChildAt(i);
			if(wv == tab.getWebView()){
				tab.setImageDrawable(bd);
			}
		}
	//	TabButton tab = (TabButton) getChildAt(currentTab);
	//	tab.setBackgroundColor(Color.GRAY);
	//	tab.setImageDrawable(bd);
	}
	public void onClick(View v) {
		int id = v.getId();
		if(id == 33){
			//addWindow();
			mParent.removeWebView();
			removeWindow();
			return;
		}
		else if (v instanceof TabButton){
			TabButton child = (TabButton)v;
			mParent.setActiveWebViewIndex(id);
			setActiveTabIndex(child);	
			currentTab = child.getTabIndex();
			String url = child.getWebView().getUrl();
			eventViewer.setText(url);
			return;
		}
	}
	
	public void setCurrentTab(int i){
		int count = getChildCount()-3;
		if(i > 1 && i < count){
			currentTab = i;
		}
	}
	public static int getCurrentTab(){
		return currentTab;
	}
	
	public void addWindow(String url){		
		TabButton but = new TabButton(mContext);
		//but.setBackgroundResource(R.drawable.settings_btn);
		but.setWebView(createWebView(url));
		but.setOnClickListener(this);
		but.setTabIndex(2);
		addView(but,2);
		int count = getChildCount();
		for(int i =3;i<count-3;i++){
			TabButton tab = (TabButton) getChildAt(i);
			tab.setTabIndex(i);
		}
		mParent.addWebView(but.getWebView());
		but.setId(mParent.getActiveWebViewIndex()+1);
		mParent.setActiveWebViewIndex(mParent.getActiveWebViewIndex()+1);
		
		currentTab = 2;
		setActiveTabIndex(but);
	}
	
	public int getWindowCount()
	{
		return getChildCount() - 5;
	}
	
	public WebView createWebView(String url){
		
		WebView webView = new WebView(mContext);
		webView.setId(mParent.getActiveWebViewIndex()+1);
		webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setMapTrackballToArrowKeys(false); // use trackball directly
        
        // Enable the built-in zoom
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		webView.setLayoutParams(params);
		
		if(url.equals("")){
			
			String[] u = SwifteeHelper.getHomepage(2);
			String _url = u[0]+u[1]+u[2];
			webView.loadUrl(_url);
			
		} else {
			
			webView.loadUrl(url);
			webView.setSelectionColor(0xAAb4d5fe);
			webView.setSearchHighlightColor(0xAAb4d5fe);
	
			webView.setCursorOuterColors(0xff74b1fc, 0xff46b000, 0xff74b1fc, 0xff36c000);
			webView.setCursorInnerColors(0xffa0c9fc, 0xff8cd900, 0xffa0c9fc, 0xff7ce900);
			webView.setCursorPressedColors(0x80b4d5fe, 0x807ce900);
			
		}
		
		return webView;
		
	}
	public void removeWindow(){
		if(getChildCount()>5){
			removeViewAt(currentTab);
			if(currentTab>2)
				currentTab--;
			else
				currentTab=2;
			TabButton child = (TabButton)getChildAt(currentTab);
			setActiveTabIndex(child);
			mParent.adjustTabIndex(this);
		}
	}

	public void setEventViewer(EventViewerArea eventViewer) {
		this.eventViewer = eventViewer;
	}


	// Extra saved information for displaying the tab in the picker.
    public static class PickerData {
        String  mUrl;
        String  mTitle;
        Bitmap  mFavicon;
        float   mScale;
        int     mScrollX;
        int     mScrollY;
    }
    
}