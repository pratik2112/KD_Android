package kdgs.kdgroup.config;
/**
 * Created by navneet on 29-Sep-17.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nispok.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import kdgs.kdgroup.R;
import kdgs.kdgroup.model.LoginResponse;
import kdgs.kdgroup.reciver.ConnectivityReceiver;
import kdgs.kdgroup.utills.Exiter;

import static android.content.Context.WIFI_SERVICE;

public class CommonFunctions {
    public static String errMessage = "";
    static String tag = "CommonFunctions :";
    /**
     * Create Progress bar
     */
    static ProgressDialog pd;
    public static boolean slide_act_flag = true;

    /**
     * Check Internet connection available or not
     *
     * @return
     */
    public static boolean checkConnection(Activity activity) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected)
            showSnack(activity, activity.getResources().getString(R.string.msg_NO_INTERNET_RESPOND));
        return isConnected;

    }

    // Showing the status in Snackbar
    public static void showSnack(Activity activity, String errMessage) {
        Snackbar.with(activity) // context
                .text(errMessage) // text to display
                .textColor(Color.RED)
                .color(Color.BLACK)
                .show(activity); // activity where it is displayed
    }

    public static String getIpaddress(Activity activity) {
        String ipAddress = "";
        try {
            WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
            ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public static String e(String data) {
        String dm = "";
        try {
            // Sending side
            byte[] data1 = data.getBytes("UTF-8");
            dm = Base64.encodeToString(data1, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
        return dm;
    }

    public static String d(String data) {
        String dm = "";
        try {
            // Sending side
            byte[] data1 = Base64.decode(data, Base64.DEFAULT);
            dm = new String(data1, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
        return dm;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                new java.io.InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    public static boolean isNetworkAvailable(Context mContext) {
        android.net.ConnectivityManager connec = (android.net.ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED
                || connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            // MESSAGE TO SCREEN FOR TESTING (IF REQ)
            // Toast.makeText(this, connectionType + " connected",
            // Toast.LENGTH_SHORT).show();
            return true;

        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED
                || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            // System.out.println("Not Connected");
            return false;
        }
        return false;
    }

    /**
     * Convert Stream to String
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is,
                "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    /**
     * Create Directories
     *
     * @param filePath
     * @return
     */
    public static boolean createDirs(String filePath) {
        Logger.debugE(filePath.substring(0, filePath.lastIndexOf("/")));
        java.io.File f = new java.io.File(filePath.substring(0, filePath.lastIndexOf("/")));
        Logger.debugE(f.mkdirs() + "");
        return f.mkdirs();
    }

    public static void showAlert(Context c, String title, String msg) {
        new Builder(c, R.style.AlertDialogCustom)
                .setMessage(msg)
                .setTitle(title)
                .setPositiveButton(c.getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).show();
    }

    @android.annotation.SuppressLint("NewApi")
    public static void showAlert(Context c, String title, String msg, int icon) {
        new Builder(c, android.app.AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(msg)
                .setIcon(icon)
                .setTitle(title)
                .setPositiveButton(c.getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).show();
    }

    public static void showAlert(Context c, String title, String msg, int icon,
                                 DialogInterface.OnClickListener listener) {
        new Builder(c).setMessage(msg).setIcon(icon)
                .setTitle(title).setCancelable(false)
                .setPositiveButton(c.getString(android.R.string.ok), listener).show();
    }

    public static void showAlert(Context c, String title, String msg, int icon,
                                 DialogInterface.OnClickListener listener,
                                 DialogInterface.OnClickListener cancelListener) {
        new Builder(c)
                .setMessage(msg)
                .setIcon(icon)
                .setTitle(title)
                .setPositiveButton(c.getString(android.R.string.ok), listener)
                .setNegativeButton(c.getString(android.R.string.cancel), cancelListener)
                .show();
    }

    public static void showAlert(Context c, String title, String msg, int icon,
                                 DialogInterface.OnClickListener listener,
                                 DialogInterface.OnClickListener cancelListener, boolean cancalable) {
        new Builder(c)
                .setMessage(msg)
                .setIcon(icon)
                .setTitle(title)
                .setPositiveButton(c.getString(android.R.string.ok), listener)
                .setCancelable(cancalable)
                .setNegativeButton(c.getString(android.R.string.cancel), cancelListener)
                .show();
    }

    public static void showAlert(Context c, String title, String msg, int icon,
                                 DialogInterface.OnClickListener listener, boolean cancalable) {
        new Builder(c).setMessage(msg).setIcon(icon)
                .setTitle(title)
                .setPositiveButton(c.getString(android.R.string.ok), listener)
                .setCancelable(cancalable).show();
    }

    public static void setPreference(Context c, String pref, boolean val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();
    }

    public static boolean getPreference(Context context, String pref,
                                        boolean def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(pref, def);
    }

    public static void setPreference(Context c, String pref, int val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();
    }

    public static int getPreference(Context context, String pref, int def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getInt(
                pref, def);
    }

    public static void setPreference(Context c, String pref, float val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putFloat(pref, val);
        e.commit();
    }

    public static float getPreference(Context context, String pref, float def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getFloat(
                pref, def);
    }

    public static void setPreference(Context c, String pref, long val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getPreference(Context context, String pref, long def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getLong(
                pref, def);
    }

    public static void setPreference(Context c, String pref, double val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, Double.doubleToLongBits(val));
        e.commit();
    }

    public static double getPreference(Context context, String pref, double def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getLong(
                pref, Double.doubleToLongBits(def));
    }

    public static void setPreference(Context c, String pref, String val) {
        android.content.SharedPreferences.Editor e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();
    }

    public static String getPreference(Context context, String pref, String def) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getString(pref, def);
    }

    public static String title(String string) {
        String ret = "";
        StringBuffer sb = new StringBuffer();
        java.util.regex.Matcher match = Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(string);
        while (match.find()) {
            match.appendReplacement(sb, match.group(1).toUpperCase()
                    + match.group(2).toLowerCase());
        }
        ret = match.appendTail(sb).toString();
        return ret;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }

    /**
     * Enables/Disables all child views in a view group.
     *
     * @param viewGroup the view group
     * @param enabled   <code>true</code> to enable, <code>false</code> to disable the
     *                  views.
     */
    public static void enableDisableViewGroup(android.view.ViewGroup viewGroup,
                                              boolean enabled, android.view.View[] exceptionalViews) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            for (int j = 0; j < exceptionalViews.length; j++)
                if (view.getId() == exceptionalViews[j].getId()) {
                    view.setEnabled(true);
                    break;
                }
            if (view instanceof android.view.ViewGroup) {
                enableDisableViewGroup((android.view.ViewGroup) view, enabled,
                        exceptionalViews);
            }
        }
    }

    public static ProgressDialog createProgressBar(Context context,
                                                   String strMsg) {
        if (pd == null) {
            pd = new ProgressDialog(context, R.style.ProThemeOrange);
            pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pd.setMessage(strMsg);
            pd.setCancelable(false);
            pd.show();
        } else {
            pd.dismiss();
            pd = null;
            pd = new ProgressDialog(context, R.style.ProThemeOrange);
            pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pd.setMessage(strMsg);
            pd.setCancelable(false);
            pd.show();

        }
        return pd;
    }

    public static ProgressDialog createProgressBar(Context context,
                                                   String strMsg, boolean isCancelable) {
        pd = new ProgressDialog(context, R.style.ProThemeOrange);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setMessage(strMsg);
        pd.setCancelable(isCancelable);
        pd.show();
        return pd;
    }

    /**
     * Destroy progress bar
     */
    public static void destroyProgressBar() {
        if (pd != null)
            pd.dismiss();
    }

    // Region startactivity
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void changeactivity(Context context, Class<?> Act_des) {
        android.content.Intent i = new android.content.Intent(context, Act_des);

        ((Activity) context).startActivityForResult(i, 0);
        ((Activity) context).finish();
        if (GlobalVariable.slide_act_flag) {
            ((Activity) context).overridePendingTransition(
                    R.anim.slide_in_left, R.anim.slide_out_right);
            GlobalVariable.slide_act_flag = false;
        } else {
            ((Activity) context).overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
            GlobalVariable.slide_act_flag = true;
        }
    }
    // EndRegion

    // Region hidekeyboad
    public static void hideSoftKeyboard(Activity activity, android.widget.EditText e) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(e.getWindowToken(), 0);
    }

    // EndRegion
    // Region Toast function
    public static void ToastMessage(Activity a, String message) {
        try {
            android.widget.Toast.makeText(a, message, android.widget.Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    // EndRegion
    //Region exit from application
    @android.annotation.SuppressLint("NewApi")
    public static void alertboxshow(final Activity activity) {
        // TODO Auto-generated method stub
        try {
            Builder builder = new Builder(
                    activity, android.app.AlertDialog.THEME_HOLO_LIGHT);
            builder.setTitle(activity.getResources().getString(R.string.app_name));
            //	builder.setIcon(R.drawable.iconlogo);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        @android.annotation.SuppressLint("InlinedApi")
                        public void onClick(DialogInterface dialog, int which) {
                            // System.exit(0);
                            // Home.this.finish();
                            final android.content.Intent relaunch = new android.content.Intent(activity,
                                    Exiter.class)
                                    .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                                            | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            activity.startActivity(relaunch);
                            activity.finish();

                        }
                    });
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
            Logger.debugE("IntroActivity", e + "");
        }
    }

    //EndRegion
    public static java.util.Map jsonToMap(JSONObject json) throws org.json.JSONException {
        java.util.Map<String, Object> retMap = new java.util.HashMap<String, Object>();
        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static java.util.Map toMap(JSONObject object) throws org.json.JSONException {
        java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
        java.util.Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof org.json.JSONArray) {
                value = toList((org.json.JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static java.util.List toList(org.json.JSONArray array) throws org.json.JSONException {
        java.util.List<Object> list = new java.util.ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof org.json.JSONArray) {
                value = toList((org.json.JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static boolean validateEmailAddress(String email) {
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static String getDeviceMenufacture() {
        String dm = "";
        try {
            dm = Build.MANUFACTURER;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }

    public static String getDeviceModel() {
        String dm = "";
        try {
            dm = Build.MODEL;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }

    public static String getDeviceOSVersion() {
        String dm = "";
        try {
            dm = Build.VERSION.RELEASE;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }

    public static String getDeviceOEMBuildNumber() {
        String dm = "";
        try {
            dm = Build.FINGERPRINT;
            ;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }

    public static String getDeviceUID(Activity activity) {
        try {
            String android_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return android_id;
        } catch (Exception e) {
            Logger.debugE(e.toString());
            if (e.getMessage() == null) {
                Logger.debugE("" + " getDeviceUID : ");
            } else {
                Logger.debugE("" + " getDeviceUID : " + e.getMessage());

            }
            return "";
        }

    }

    public static String getDeviceSerialNumber() {
        String dm = "";
        try {
            dm = Build.SERIAL;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }


    public static String ApplicationVersion(Context context) {
        String dm = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            dm = info.versionName;
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
            return dm;
        }
    }

    public static String getDeviceType() {
        return "Android";
    }

    public static void displayErrorMessage(Context ctx, String title, String error) {
        Builder alert = new Builder(ctx);
        alert.setTitle(title);
        alert.setMessage(error);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // dismiss the dialog
                dialog.cancel();
            }
        });
        alert.setCancelable(true);
        alert.create().show();
    }

    public static JSONObject loadJSONFromAsset(Activity activity, String filename) {
        String json = null;
        JSONObject result = new JSONObject();
        try {
            InputStream is = activity.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            result = new JSONObject(json);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return result;
    }


    public static Date getDateFromUnixTime(String sTimestamp) {
        if (sTimestamp == null || sTimestamp.isEmpty()) {
            return null;
        }
        int iTimeStamp = 0;
        try {
            iTimeStamp = Integer.parseInt(sTimestamp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new Date(iTimeStamp * 1000L); // *1000 is to convert seconds to milliseconds
    }

    public static boolean toBoolean(String target) {
        if (target == null) return false;
        return target.matches("(?i:^(1|true|yes|oui|vrai|y)$)");
    }

    public static int dpToPx(int dp, Activity activity) {
        float density = activity.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static int getMessaerHeight(int width, int height, Activity activity) {
        try {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            int widthD = displayMetrics.widthPixels;
            int heightD = displayMetrics.heightPixels;
            int w = width;
            int h = height;
            int mh = (int) ((h * widthD) / w);
            return mh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String parseDateToddMMyyyy(String inputPattern, String outputPattern, String time) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }

    public static String applicationVersion(Context context) {
        String version = "";
        int verCode;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return version;
        }
    }

    public static void setImage(Context activity, ImageView imageView, int i) {
        Glide.with(activity)
                .load(Integer.valueOf(i))
                .thumbnail(0.5f)
                .dontAnimate()
                .fitCenter()
                .placeholder((int) R.drawable.ic_trans)
                .error((int) R.drawable.ic_trans)
                .into(imageView);
    }

    public static LoginResponse getloginresponse(Context context) {
        LoginResponse loginResponse = null;
        loginResponse = new Gson().fromJson(CommonFunctions.getPreference(context, Constants.userdata, "").toString(), LoginResponse.class);
        return loginResponse;
    }

    public static void setImageURL(Context activity, ImageView imageView, String url) {
        Glide.with(activity)
                .load(url)
                .thumbnail(0.5f)
                .dontAnimate()
                .fitCenter()
                .placeholder((int) R.drawable.unknown)
                .error((int) R.drawable.unknown)
                .into(imageView);
    }
}