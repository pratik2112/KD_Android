package kdgs.kdgroup.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import kdgs.kdgroup.R;

public abstract class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);
        onViewReady(savedInstanceState);
        return view;
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState) {
        resolveDaggerDependency();
        //To be used by child activities
    }

    protected void resolveDaggerDependency() {

    }

    protected void showAlertDialog(String title, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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

    @Override
    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(title);
            mProgressDialog.setIcon(R.mipmap.ic_launcher);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
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

    @Override
    public void noInternetConnectionAvailable() {
        showToast(getString(R.string.noNetworkFound));
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(getActivity(), mToastMsg, Toast.LENGTH_LONG).show();
    }


    public abstract int getContentView();
}
