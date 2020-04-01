package cn.droidlover.xdroidmvp;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

public class XDroidMvpUtill {
    public static void vibrator(Context context) {
//获取系统震动服务
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
//震动70毫秒
        vib.vibrate(70);
    }
}
