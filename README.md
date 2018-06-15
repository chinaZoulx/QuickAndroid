# quick.library
# 快速开发框架
zxing 扫描、生成、解析二维码、近距离扫码<br>
## BaseActivity
## BaseFragment
## BaseListActivity
## BaseListActivity2

## BaseListFragment
## BaseListFragment
## QuickStartActivity <br>
以回调的方式管理onActivityForResult的返回值。<br>
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


requestCode根据目标Activity的路径("com....ui.main.MainActivity")hasCode生成，每个Activity对应一个requestCode。<br><br>
一般来讲，A跳转到B，A中只会有一处跳转到B。如果有特殊需求，A存在多处跳转到B，就像按钮1跳转B，按钮2也要跳转到B，因为每个Activity只对应一个requestCode,所以应该以动态传值的方法来写
~~~java
fun startActionB(intent:Intent){
  QuickStartActivity.startActivity(activity, intent, { resultCode, data ->
    
  })
}
~~~
## BaseRecyclerViewAdapter
recyclerView基类，集成了点击事件、长按、item内的view点击<br><br>

最简单的使用方法
~~~java
class Adapter : BaseRecyclerViewAdapter<Int>() {
        override fun onResultLayoutResId(): Int = R.layout.item_commodity_list<br>

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: Int?) {
//            holder.setImg(R.id.coverIv, itemData!!)
            holder.getImageView(R.id.coverIv).setImageResource(itemData!!)
    }
~~~
设置margin与padding。<br>
~~~java
inner class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int = R.layout.item_discover_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String?) {
            holder.setImg(R.id.contentIv, itemData)
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

## 蓝牙终端管理

![蓝牙终端管理](https://img-blog.csdn.net/20180604145009313 "蓝牙管理")
![蓝牙终端管理](https://img-blog.csdn.net/20180604145021307 "搜索蓝牙")
蓝牙博客地址：https://blog.csdn.net/fy993912_chris/article/details/80540516
