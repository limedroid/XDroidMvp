package cn.droidlover.xdroidmvp;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.mvp.XPresentation;

public class XDroidMvpUtill {
    public static void vibrator(Context context) {
        //获取系统震动服务
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        //震动70毫秒
        vib.vibrate(70);
    }

    public static <T extends ViewBinding> Class<T> getViewBindingClass(Class<?> _class) {
        if (_class == null) {
            return null;
        }
        if (_class == XActivity.class || _class == XFragment.class || _class == XPresentation.class || _class == Object.class) {
            return null;
        }
        Type genType = _class.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length > 0) {
            if (params.length == 1) {
                return getViewBindingClass(_class.getSuperclass());
            } else {
                for (int i = 1; i < params.length; i++) {
                    Class<?> c = (Class<?>) params[i];
                    if (c == ViewBinding.class) {
                        return null;
                    }
                    if (ViewBinding.class.isAssignableFrom(c)){
                        return (Class<T>)c;
                    }
//                    if (cs == ViewBinding.class) {
//                        return (Class<T>) c;
//                    }
                }
            }
        } else {
            return getViewBindingClass(_class.getSuperclass());
        }
        return null;
    }

}
