#XDroidMvp 轻量级的Android MVP快速开发框架

## 概述

<p align="center">
	<img src="xdroid_logo_128.png"/>
</p>

**XDroidMvp**是[XDroid](https://github.com/limedroid/XDroid)Android快速开发框架的MVP版本，其使用方式类似于`XDroid`，大部分源码也来自`XDroid`。

`XDroidMvp`主要会有这些特性：

新增：

**无需写`Contract`！ 无需写`Present`接口！  无需写`View`接口!**

* Mvp实现
* `RxJava` & `RxAndroid`
* 权限适配 `RxPermission`
* 事件订阅默认采用 `RxBus`
* 网络交互：
	* `Retrofit` + `rx`
	* `Https`
	* 统一异常处理
	* 缓存
	* 。。。。

保留：

* 提供`XActivity`、`XFragment`、`SimpleRecAdapter`、`SimpleListAdapte`r等基类，可快速进行开发
* 完整封装`XRecyclerView`，可实现绝大部分需求
* `QTContentLayout`、`XRecyclerContentLayout`实现loading、error、empty、content四种状态的自由切换
* 实现了`Memory`、`Disk`、`SharedPreferences`三种方式的缓存，可自由扩展
* 内置了`EventBus`，可自由切换到其他事件订阅库
* 内置`Glide`，可自由切换其他图片加载库
* 可输出漂亮的`Log`，支持`Json`、`Xml`、`Throwable`等，蝇量级实现
* 内置链式路由
* 内置常用工具类：`package`、`random`、`file`...,提供的都是非常常用的方法
* 内置加密工具类 `XCodec`，你想要的加密姿势都有



<p align="center">
	<img src="mvp.png"/>
</p>


**先睹为快**

你可以这么使用:

MainActivity

```java
public class MainActivity extends XActivity<VMain> {

    @Override
    public void initData(Bundle savedInstanceState) {
        getV().test();      //p调用v的方法
    }

    public void test() {
        getpDelegate().toastShort("v 调用 p");
    }

    @Override
    public VMain newV() {
        return new VMain();
    }
}
```

VMain

```java
public class VMain extends XView<MainActivity> {

    @BindView(R.id.tv_showMvp)
    TextView tvShowMvp;


    @Override
    public void bindEvent() {
        tvShowMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getP().test();      //v 调用 p的方法
            }
        });
    }


    public void test() {
        getvDelegate().toastShort("p 调用 v的方法");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
```


## 更新日志

* 2016-12-23，新增mvp、base、cache、event、imageloader、log、router
* 2016-12-25，新增rxJava、rxAndroid、rxlifecycle、rxpermission、rxbus、net(retrofit)
* 2016-12-26，新增网络异常统一处理


## TODO

* [x] rx
* [x] retrofit
* [x] rxpermission
* [x] rxbus
* [ ] cache
* [ ] wiki
* [ ] demo

## About Me

**Email** : droidlover@126.com

**XDroid交流群**：153569290

**XDroid MVC版本**：[XDroid](https://github.com/limedroid/XDroid)


