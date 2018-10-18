# 持续更新中，本项目基于[QuickComponent开发](https://github.com/SpringSmell/quick-component)
# quick.library
本项目包含完整的代码Demo,可直接clone
# 快速开发框架
zxing 扫描、生成、解析二维码、近距离扫码<br>
## BaseActivity
该基类确定了业务逻辑处理的规范，如果再使用dagger2+MVP模式，将大大提高代码的可读性。<br>
使用基类布局将获得标题，而且该标题可以根据自己需求onResultToolbar替换为自已的，可单独设置返回按钮图片，点击事件。<br>
加入了泛型的快速获取Viewr的getView(),快速获取intentk中的数据getTransmitValue()。还有showToast(),showSnackbar()等小工具<br>
快速设置onClickListener，可成组设置，也可单独设置<br>
~~~java
setOnClickListener(object :OnClickListener2(){
            override fun onClick2(view: View?) {
                
            }
        },id1,id2,id3,id4,id5)
~~~
快速设置menu，无需再像传统那样分开创建、监听。如果不使用menu，也可设置rightView。
~~~java 
setMenu(R.menu.navigation) { menu ->
            when(menu?.itemId){
                
            }
            true
        }
~~~

示例
~~~java
class TestActivity : BaseActivity() {

    override val isUsingBaseLayout: Boolean
        get() = super.isUsingBaseLayout/*是否引用基类布局，如不引用将没有标题，开发者自行实现*/

    override val isShowTitle: Boolean
        get() = super.isShowTitle/*是否显示标题*/

    override fun onResultLayoutResId(): Int {/*返回layout*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInit() {/*初始化变量*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInitLayout() {/*初始化Layout相关*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindListener() {/*绑定监听事件*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {/*初始化完毕，开始加载数据*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
~~~
## BaseFragment
同上，不同的是默认不显示标题
## BaseListActivity
这此基类使用RecyclerView，快速构建基本的列表，让开发者只需要专注业务逻辑的处理<br>
基本列表只需重写必要的回调即可，如果有特殊需求也可自行重写其他方法，像分割线，刷新方法，加载方法，错误提示等，非常灵活<br>
基本实用示例
~~~java
class TestListActivity : BaseListActivity() {

    /**
     * 流程：先初始化基类本身需要的属性，比如isPullRefreshEnable、isLoadMoreEnable、onResultAdapter、onResultLayoutManager之后，再走到onInit()
     * 此处应当初始化业务逻辑的变量，
     */
    override fun onInit() {
        getAdapter<Adapter>().setOnItemClickListener { v, position ->
            showToast(String.format("点击了第%d项", position))
        }
        getAdapter<Adapter>().setOnClickListener(BaseRecyclerViewAdapter.OnClickListener{ view, holderRv, position ->
            
        },R.id.你的ID1,R.id.你的ID2)
    }

    override fun start() {
        onRefresh()
    }

    /*是否开启下拉刷新*/
    override fun isPullRefreshEnable(): Boolean = true

    /*是否开启上拉加载*/
    override fun isLoadMoreEnable(): Boolean = true

    /*返回适配器*/
    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    /*布局管理器，默认为纵向管理器上下滚动*/
    override fun onResultLayoutManager(): RecyclerView.LayoutManager {
        return super.onResultLayoutManager()
    }

    override fun onResultUrl(): String = "http://www.baidu.com"

    override fun onResultParams(params: MutableMap<String, String>) {
        params["userName"] = "张三"
    }

    override fun onRequestDataSuccess(jsonData: String?, isPullRefresh: Boolean) {
        if (isPullRefresh) {//下拉刷新

        } else {//上拉加载

        }
    }

    inner class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindData(holderRv: BaseViewHolder, position: Int, itemData: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
~~~
## BaseListActivity2
带Header的基类列表，描述：给数据分类，不同ID分为多组，例如：6月、7月的数据各分为两组，并且header浮在顶部<br>
列表使用XStickyListHeadersListView，由于此列表Adapter复杂，遂封装进基类，开发都只需要处理业务逻辑即可。
基本使用示例：
~~~java
class TestListActivity2 : BaseListActivity2<String>() {
    override fun onInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPullRefreshEnable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLoadMoreEnable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultUrl(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultParams(params: MutableMap<String, String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDataSuccess(jsonData: String?, isPullRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultItemLayout(): Int {/*返回列表LayoutId*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultHeaderLayout(): Int {/*返回头部LayoutId*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindHeaderId(itemData: String?, position: Int): Long {/*返回用于分类的ID，例如：根据月份分类，应当返回月月份，*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*绑定item数据*/
    override fun onBindDataItemView(holderRv: BaseRecyclerViewAdapter.BaseViewHolder?, itemData: String?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*绑定header数据*/
    override fun onBindDataHeaderView(holderRv: BaseRecyclerViewAdapter.BaseViewHolder?, itemData: String?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
~~~

## BaseListFragment
考虑到不同使用场景，提供了fragment版本，使用方法同上
## BaseListFragment2
同上
## BaseRecyclerViewAdapter
recyclerView基类，集成了点击事件、长按、item内的view点击<br><br>

最简单的使用方法
~~~java
class Adapter : BaseRecyclerViewAdapter<Int>() {
        override fun onResultLayoutResId(): Int = R.layout.item_commodity_list<br>

        override fun onBindData(holderRv: BaseViewHolder, position: Int, itemData: Int?) {
//            holderRv.setImg(R.id.coverIv, itemData!!)
            holderRv.getImageView(R.id.coverIv).setImageResource(itemData!!)
    }
~~~
设置margin与padding。<br>
~~~java
inner class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int = R.layout.item_discover_list

        override fun onBindData(holderRv: BaseViewHolder, position: Int, itemData: String?) {
            holderRv.setImg(R.id.contentIv, itemData)
        }

        //----------margin 一般用于CardView
        override fun onResultItemMargin(): Int = 40

        override fun onResultItemMarginLeft(position: Int): Int = when {
            position % 2 == 0 -> 40
            else -> 20
        }

        override fun onResultItemMarginRight(position: Int): Int = when {
            position % 2 != 0 -> 40
            else -> 20
        }

        override fun onResultItemMarginTop(position: Int): Int {
            return super.onResultItemMarginTop(position)
        }

        override fun onResultItemMarginBottom(position: Int): Int {
            return super.onResultItemMarginBottom(position)
        }

        //------------Padding，一般用于常规列表
        override fun onResultItemPadding(): Int {
            return super.onResultItemPadding()
        }

        override fun onResultItemPaddingLeft(position: Int): Int {
            return super.onResultItemPaddingLeft(position)
        }
    }
~~~

## 以下是实用组件

## QuickStartActivity
以回调的方式管理onActivityForResult的返回值。无需再向传统那样，先startActivityForResult,然后再监听onActivityForResult，真正做到在哪开始，就在哪结束，方便开发者debug，并且无需再为担心requestCode怎么传，会不会有重复的，有没有超出65536上限而担心，因为已经生成了唯一的requestCode,每个Activity对应一个requestCode。<br>
开始调用
~~~java
var intent=Intent(this,YourActivity::class.java)
intent.putExtra("TYPE","this is a type")
QuickStartActivity.startActivity(activity, intent, { resultCode, data ->
                
})
~~~
在这里绑定
~~~java
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        QuickStartActivity.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
~~~

一般来讲，A跳转到B，A中只会有一处跳转到B。如果有特殊需求，A存在多处跳转到B，就像按钮1跳转B，按钮2也要跳转到B，因为每个Activity只对应一个requestCode,所以应该以动态传值的方法来写

~~~java
fun startActionB(intent:Intent){
  QuickStartActivity.startActivity(activity, intent, { resultCode, data ->
    
  })
}
~~~

## QuickToast
可在任意线程弹出，方便的使用Toast，无需在使用的时候频繁传递context，一次初始化，终生受益。<br>
传统使用方式
~~~java
Toast.makeText(activity,"这是一个Toast",Toast.LENGTH_SHORT).show()
~~~
如果需要自定义时
~~~java
val toast = Toast.makeText(activity, "这是一个Toast", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
~~~
这种方式使用时每次都需要依赖context，如果需要在子线程使用，将会变得很不方便，那么此组件如何呢<br>
所以请看
~~~java
QuickToast.showToastDefault("这是一个Toast")
~~~
或者，使用链式配置参数，或者自定义Toast
~~~java
QuickToast.Builder().setGravity(Gravity.CENTER).setDuration(Toast.LENGTH_SHORT).setLayoutView(customView).build().showToast("这是一个Toast")
~~~
看到这里或许就有疑问了，在哪里初始化context呢，使用quickLibrary将无需处理，若只需使用此组件，重写QuickToast中的context方法即可。<br>
示例
~~~java
class CustomToast:QuickToast() {

    override val context: Context
        get() = super.context/*这里替换为自定义context*/
}
~~~
如此就能愉快的玩耍了。

## QuickBroadcast
方便的使用动态广播，告别繁琐的注册与注销广播。<br>
正常写法<br>
先写一个广播
~~~java
val broadcastRecevier=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            
        }
    }
~~~
再根据action注册一个
~~~java 
registerReceiver(broadcastRecevier, IntentFilter("action"))
~~~
再onDestroy中注销
~~~java
unregisterReceiver(broadcastRecevier)
~~~
再发送广播
~~~java
sendBroadcast(Intent("test"))
~~~
每个页面都需这样去写，你到底累不累？<br><br>
现在来看看新的写法，只需两步即可实现<br>
1、注册
~~~java
QuickBroadcast.addBroadcastListener(绑定者, { action, intent ->
            when (action) {
                "test" -> showToast(intent.getStringExtra("test"))
                "test2" -> showToast(intent.getStringExtra("test"))
            }
        }, "test", "test2")/*天呐，这里居然可以指定多个接受者*/
~~~
2、发送广播
~~~java
QuickBroadcast.sendBroadcast(Intent(), "test")
~~~
如此简单的两步就完成了广播的注册与发送，so easy!<br><br>
如果更复杂的使用呢，当前页面发送给多个页面。<br><br>
正常写法
~~~java
sendBroadcast(Intent("test"))
sendBroadcast(Intent("MyCenterFragment"))
~~~
新写法
~~~java
QuickBroadcast.sendBroadcast(Intent(), "test","MyCenterFragment")
~~~
对于发送只需增加接收者即可，这样注册了test与MyCenterFragment的接收者都将收到你的消息。<br><br>

并且此组件同一时刻不会同时触发接收者为同一监听的广播
~~~java
QuickBroadcast.addBroadcastListener(绑定者, { action, intent ->
            when (action) {
                "test" -> showToast(intent.getStringExtra("test"))
                "test2" -> showToast(intent.getStringExtra("test"))
            }
        }, "test", "test2")/*天呐，这里居然可以指定多个接受者*/
~~~
比如，test,test2为同一组接收者。<br>
如果发送同时发送test,test2
~~~java
QuickBroadcast.sendBroadcast(Intent(), "test","test2")
~~~
那只会触发早先的test，test2将不再触发。<br><br>
此组件需要注意的是注册时需要传递唯一的绑定者。

## 蓝牙终端管理

![蓝牙终端管理](https://img-blog.csdn.net/20180604145009313 "蓝牙管理")
![蓝牙终端管理](https://img-blog.csdn.net/20180604145021307 "搜索蓝牙")
蓝牙博客地址：https://blog.csdn.net/fy993912_chris/article/details/80540516
