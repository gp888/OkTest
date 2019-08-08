
import java.math.BigDecimal

fun main(args: Array<String>) {
    var a:Boolean = true//1byte
    var b:Byte = 10
    var short:Short = 20
    var char:Char = '我'
    var i:Int= 10//自动选择java基本类型
    var f:Float = 3.51234567889654f
    var d:Double = 1.333
    var l:Long = 20L

    i.hashCode()//自动转成Integer

    println(f)//.sout

    var bigde:BigDecimal = BigDecimal("f")//处理高精度
    println(bigde)

    var minB:Byte = Byte.MIN_VALUE
    var maxB:Byte = Byte.MAX_VALUE

    var minF:Float = Float.MIN_VALUE//不是最小值。。。
    var MINd:Double = Double.MIN_VALUE//不是最小值

    var tt = 10//js类型不安全

    var g:Int = 70
    g.toLong()

    //\n换行

    var ss = """|我
            |喝
        |茶"""//原样输出字符串

    ss.trimMargin("|")

    var sss = "444"

    ss === sss//引用地址，equals. == 值相等

    //元组数据
    //二元元组 Pair  2个数据
    //三元元组 Triple 3个数据

    var pair:Pair<String, Int> = Pair("44", 55);
    pair.second

    var triple = Triple("44", 40, "11313131313")
    triple.first


    //null
    var s: String? = null//String? 可空类型
    //s?.toInt()//?. 空安全调用符，如果s不为空，则执行。推荐
    //s!!.toInt()//!!非空断言，s一定不为空，可能产生空指针异常

    var a3:Int = s?.toInt()?:-1//s为空，返回-1 ：Elvis

    var c:String?
    c = readLine()

    //lateinit延迟初始化

    //位运算
    val FLAG1 = true
    val FLAG2 = false
    val bitwiseOr = FLAG1 or FLAG2
    val bitwiseAnd = FLAG1 and FLAG2
}