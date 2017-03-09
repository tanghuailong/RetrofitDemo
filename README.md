### Android 项目说明文档

---

#### 相关技术点

* 固定格式的公共数据类型(已完成)
* 封装RxJava 操作(已完成)
* 轮询(已完成)
* 图表的时间处理(已完成)

---
#### 项目分析

> **相关技术**
   采用了[Retrofit][1]+[RxJava][2]的方式实现数据获取,[MPAndroidChart][3]绘制图表，时间处理采用了[threetenabp][4](在Android上实现了JSR-310标准，即类似于Java8中的时间处理，十分便利)，[ButterKnife][5]一个View注入框架

* [ButterKnife][5]的使用(基本就使用到下面这几种类型，想要更具体了解请参考官网wiki)


```
 class ExampleActivity extends Activity {
  @BindView(R.id.title) TextView title;
  @BindView(R.id.subtitle) TextView subtitle;
  @BindView(R.id.footer) TextView footer;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    ButterKnife.bind(this);
    // TODO Use fields...
  }
}
```
```
class ExampleActivity extends Activity {
  @BindString(R.string.title) String title;
  @BindDrawable(R.drawable.graphic) Drawable graphic;
  @BindColor(R.color.red) int red; // int or ColorStateList field
  @BindDimen(R.dimen.spacer) Float spacer; // int (for pixel size) or float (for exact value) field
  // ...
}
```
```
@OnClick(R.id.submit)
public void submit(View view) {
  // TODO submit data to server...
}
```
* 轮询使用范例(每隔10分钟查询一次，出现错误的时候每隔10分钟重试一次)
```
 HttpMethods.getInstance()
                .getService(SacredsunService.class)
                .getPresetValue(assertsCode, beginAndEnd.get("begin"), beginAndEnd.get("end"))
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.MINUTES)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.MINUTES))
                .subscribe((r) -> {
                   //逻辑处理
                    }
                }, (e) -> {
                    //错误处理
                });
```

* [MPAndroidChart][3] 请参照官网Wiki

* 项目中的难点，相同格式数据在Retrofit中的处理，异常的处理，下面将一一说明

* 相同格式的数据在Retrofit中的处理过程是这样的，首先通过给Retrofit添加Factory,自定义的ResponseConvertFactory类将拦截请求返回的数据，然后把它交给GsonResponseConvert 处理。在GsonResponseConvert 里面，会统一处理服务器传过来的异常Code,如果遇到不为0的Code,将会抛出自定义的异常ApiException
```
public class GsonResponseConvert<T> implements Converter<ResponseBody,T> {

    private final Gson gson;
    private final Type type;

    GsonResponseConvert(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("NetWork","response"+response);
        HttpResult httpResult = gson.fromJson(response,HttpResult.class);
        if(!httpResult.getCode().equals("0")) {
            throw new ApiException("访问失败");
        }
        return gson.fromJson(response,type);
    }
}
```










[1]:https://github.com/square/retrofit "retrofit"
[2]:https://github.com/ReactiveX/RxJava "RxJava"
[3]:https://github.com/PhilJay/MPAndroidChart "MPAndroidChart"
[4]:https://github.com/JakeWharton/ThreeTenABP
[5]:https://github.com/JakeWharton/butterknife "butterknife"
