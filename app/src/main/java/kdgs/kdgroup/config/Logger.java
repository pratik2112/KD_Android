package kdgs.kdgroup.config;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.util.logging.FileHandler;

public final class Logger {

    public static FileHandler logger = null;
    public static Context context;
    private static String TAG = "Logger";
    private static String filename = "errorlog";
    static boolean isExternalStorageAvailable = true;
    static boolean isExternalStorageWriteable = true;
    static String state = Environment.getExternalStorageState();

    public static void debugE(String className, String message) {
        Log.e(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", className + " : " + message);
        addRecordToLog(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + className + " : " + message);


    }

    public static void debugE(String message) {
        Log.e(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", message);
        addRecordToLog(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + " : " + message);
    }

    public static void debugE(String message, boolean execption) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        String errormessage = className + "." + methodName + "():-->" + lineNumber + "--->" + message;
        Log.e(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", errormessage);
        addRecordToLog(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + errormessage);
    }

    public static void debugV(String message) {
        Log.v(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", message);
        addRecordToLog(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + message);
    }

    public static void debugV(String className, String message) {
        Log.v(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", className + ":" + message);
        addRecordToLog(KDGConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + className + ":" + message);
    }

    public static void addRecordToLog(String message) {

      /*  if(true)
        {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                isExternalStorageAvailable = isExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                isExternalStorageAvailable = true;
                isExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states, but all we need
                //  to know is we can neither read nor write
                isExternalStorageAvailable = isExternalStorageWriteable = false;
            }

            File dir = new File("/sdcard/Files/Project_Name");
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (!dir.exists()) {
                    Log.d("Dir created ", "Dir created ");
                    dir.mkdirs();
                }

                File logFile = new File("/sdcard/Files/Project_Name/" + filename + ".txt");

                if (!logFile.exists()) {
                    try {
                        Log.d("File created ", "File created ");
                        logFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Logger.debugE(e.toString());
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));

                    buf.write(message + "\r\n");
                    //buf.append(message);
                    buf.newLine();
                    buf.flush();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Logger.debugE(e.toString());
                }
            }
            else
            {

            }
        }*/


    }


}