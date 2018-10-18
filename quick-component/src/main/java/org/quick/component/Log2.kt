package org.quick.component

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/14
 * @modifyInfo1 chris zou-16/10/14
 * @modifyContent
 */
object Log2 {
    val defaultTag: String by lazy { javaClass.`package`.name + "-" + javaClass.simpleName }
    var isDebug = true

    enum class TYPE(var value: Int) {
        E(0x101),
        I(0x201),
        V(0x301),
        D(0x401),
        W(0x501);
    }

    fun e(params: Any?) {
        if (params == null) e("打印了一个空元素")
        else e(parse(params))
    }

    fun e(msg: String?) {
        e(defaultTag, msg)
    }

    fun e(tag: String?, msg: String?) {
        e(tag, msg, null)
    }

    fun e(tag: String?, msg: String?, o_o: Exception?) {
        print(TYPE.E, tag, msg, o_o)
    }

    fun v(params: Any?) {
        if (params == null) e("打印了一个空元素")
        else v(parse(params))
    }

    fun v(msg: String?) {
        v(defaultTag, msg)
    }

    fun v(tag: String?, msg: String?) {
        v(tag, msg, null)
    }

    fun v(tag: String?, msg: String?, o_o: Exception?) {
        print(TYPE.V, tag, msg, o_o)
    }

    fun w(params: Any?) {
        if (params == null) e("打印了一个空元素")
        else w(parse(params))
    }

    fun w(msg: String?) {
        w(defaultTag, msg)
    }

    fun w(tag: String?, msg: String?) {
        w(tag, msg, null)
    }

    fun w(tag: String?, msg: String?, o_o: Exception?) {
        print(TYPE.W, tag, msg, o_o)
    }

    fun d(params: Any?) {
        if (params == null) e("打印了一个空元素")
        else d(parse(params))
    }

    fun d(msg: String?) {
        d(defaultTag, msg)
    }

    fun d(tag: String?, msg: String?) {
        d(tag, msg, null)
    }

    fun d(tag: String?, msg: String?, o_o: Exception?) {
        print(TYPE.D, tag, msg, o_o)
    }

    fun i(params: Any?) {
        if (params == null) e("打印了一个空元素")
        else i(parse(params))
    }

    fun i(msg: String?) {
        i(defaultTag, msg)
    }

    fun i(tag: String?, msg: String?) {
        i(tag, msg, null)
    }

    fun i(tag: String?, msg: String?, o_o: Exception?) {
        print(TYPE.I, tag, msg, o_o)
    }

    fun parse(params: Any): String {
        var resultStr = ""
        val fields = params.javaClass.declaredFields
        fields.forEach {
            it.isAccessible = true
            resultStr += it.name + ":" +
                    try {
                        when (it.type.simpleName.toLowerCase()) {
                            String::class.java.simpleName.toLowerCase() -> it.get(params)
                            Int::class.java.simpleName.toLowerCase() -> it.getInt(params)
                            Short::class.java.simpleName.toLowerCase() -> it.getShort(params)
                            Long::class.java.simpleName.toLowerCase() -> it.getLong(params)
                            Char::class.java.simpleName.toLowerCase() -> it.getChar(params)
                            Double::class.java.simpleName.toLowerCase() -> it.getDouble(params)
                            Float::class.java.simpleName.toLowerCase() -> it.getFloat(params)
                            Byte::class.java.simpleName.toLowerCase() -> it.getByte(params)
                            Boolean::class.java.simpleName.toLowerCase() -> it.getBoolean(params)
                            else -> "未知类型"
                        }
                    } catch (O_O: Exception) {
                        "转换出错"
                    } + ","
        }
        return resultStr
    }

    private fun print(type: TYPE, tag: String?, msg: String?, O_O: Exception?) {
        if (isDebug) {
            when (type) {
                TYPE.E -> android.util.Log.e(tag, msg, O_O)
                TYPE.I -> android.util.Log.i(tag, msg, O_O)
                TYPE.D -> android.util.Log.d(tag, msg, O_O)
                TYPE.W -> android.util.Log.w(tag, msg, O_O)
                TYPE.V -> android.util.Log.v(tag, msg, O_O)
            }
        }
    }
}
