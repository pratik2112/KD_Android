/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdgs.kdgroup.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import kdgs.kdgroup.R;
import kdgs.kdgroup.backstack.BackStackEntry;
import kdgs.kdgroup.backstack.BackStackManager;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String STATE_BACK_STACK_MANAGER = "back_stack_manager";
    protected BackStackManager backStackManager;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backStackManager = new BackStackManager();
        setContentView(getContentView());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        resolveDaggerDependency();
        //To be used by child activities
    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this);
        backStackManager = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_BACK_STACK_MANAGER, backStackManager.saveState());
    }

    protected LinearLayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    //Backstack management
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        backStackManager.restoreState(savedInstanceState.getParcelable(STATE_BACK_STACK_MANAGER));
    }

    protected boolean pushFragmentToBackStack(int hostId, @NonNull Fragment fragment) {
        try {
            BackStackEntry entry = BackStackEntry.create(getSupportFragmentManager(), fragment);
            backStackManager.push(hostId, entry);
            return true;
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
            return false;
        }
    }

    @Nullable
    protected Fragment popFragmentFromBackStack(int hostId) {
        BackStackEntry entry = backStackManager.pop(hostId);
        return entry != null ? entry.toFragment(this) : null;
    }

    @Nullable
    protected Pair<Integer, Fragment> popFragmentFromBackStack() {
        Pair<Integer, BackStackEntry> pair = backStackManager.pop();
        return pair != null ? Pair.create(pair.first, pair.second.toFragment(this)) : null;
    }

    /**
     * @return false if back stack is missing.
     */
    protected boolean resetBackStackToRoot(int hostId) {
        return backStackManager.resetToRoot(hostId);
    }

    /**
     * @return false if back stack is missing.
     */
    protected boolean clearBackStack(int hostId) {
        return backStackManager.clear(hostId);
    }

    @Override
    public void noInternetConnectionAvailable() {
        showToast(getString(R.string.noNetworkFound));
    }

    protected void resolveDaggerDependency() {

    }

    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void openActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(this, calledActivity);
        this.startActivity(myIntent);
    }

    @Override
    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            if (title != null)
                mProgressDialog.setTitle(title);
            mProgressDialog.setIcon(R.mipmap.ic_launcher);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            if (message != null)
                mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showAlertDialog(String title, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (title != null)
            dialogBuilder.setTitle(title);
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }

    protected AlertDialog.Builder showConfirmationDialog(String title, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(title);
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);

        dialogBuilder.setCancelable(false);
        return dialogBuilder;
    }


    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected abstract int getContentView();


}
