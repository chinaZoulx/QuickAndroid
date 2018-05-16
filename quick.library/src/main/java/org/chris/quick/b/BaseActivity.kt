package org.chris.quick.b

import android.support.annotation.LayoutRes
import org.chris.quick.b.activities.ThemeActivity
import org.chris.quick.function.IsOkDialog
import org.chris.quick.function.LoadingDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AppSettingsDialog
import android.widget.Toast
import android.content.Intent
import org.chris.quick.R


/**
 * Created by zoulx on 2017/11/13.
 */
abstract class BaseActivity : ThemeActivity(), EasyPermissions.PermissionCallbacks {
    lateinit var isOkDialog: IsOkDialog
    lateinit var loadingDialog: LoadingDialog

    override fun init() {
        if (isUsingBaseLayout && isShowTitle) {
            setBackValid()
        }
        isOkDialog = IsOkDialog(activity)
        loadingDialog = LoadingDialog(activity)
        onInit()
        onInitLayout()
        onBindListener()
        start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            AppSettingsDialog.Builder(this).setNegativeButton("退出").setPositiveButton("马上去设置").build().show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    /**
     * 返回资源文件ID
     *
     * @return
     */
    @LayoutRes
    public abstract override fun onResultLayoutResId(): Int

    /**
     * 初始化操作
     */
    abstract fun onInit()

    /**
     * 初始化布局
     */
    abstract fun onInitLayout()

    /**
     * 绑定监听
     */
    abstract fun onBindListener()

    /**
     * 绑定数据
     */
    abstract fun start()


}