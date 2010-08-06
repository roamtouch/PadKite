/*
 * Copyright 2009 Codecarpet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.api.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.api.facebook.FBDialog.FBDialogDelegate;

public class FBPermissionActivity extends Activity {
	  private FBPermissionDialog fbDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        fbDialog = new FBPermissionDialog(this, FBSession.getSession(), (String[]) intent.getExtras().get("permissions"));

        fbDialog.setDelegate(new FBPermissionDialogDelegate());

        setContentView(fbDialog);

        fbDialog.show();
    }

	  @Override
	  protected void onDestroy() {
		    super.onDestroy();
    		try{
    			  fbDialog._webView.destroy();
    		}
    		catch (Exception e)
    		{
    			  e.printStackTrace();
    		}
	  }

    private class FBPermissionDialogDelegate extends FBDialogDelegate {

        @Override
        public void dialogDidCancel(FBDialog dialog) {
            // TODO Auto-generated method stub
            super.dialogDidCancel(dialog);
            setResult(0);
        }

        @Override
        public void dialogDidSucceed(FBDialog dialog) {
            // TODO Auto-generated method stub
            super.dialogDidSucceed(dialog);
            setResult(1);
        }

    }

}
