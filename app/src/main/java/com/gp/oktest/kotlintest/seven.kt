import android.util.Log
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalTime

//内联函数 inline
//
//一个内联函数会在编译的时候被替换掉，而不是真正的方法调用。
//这在一些情况下可以减少内存分配和运行时开销。举个例子，如果我们有一个函数，
//只接收一个函数作为它的参数。如果是一个普通的函数，内部会创建一个含有那个函数的对象。
//另一方面，内联函数会把我们调用这个函数的地方替换掉，所以它不需要为此生成一个内部的对象。





//kotlin默认为public
class User{
    init {
        //初始化代码块，实例化时执行，先于构造器执行，java 不需要加init
    }

    constructor(){

    }
}
//kotlin函数参数默认是val

val size:Int
get(){return 5}

//object 创建一个类，并创建这个类对象，既有class 关键字功能，又实现单例。
//object: 创建匿名类

//一个类最多有一个伴生对象，可以有多个嵌套对象（object）。静态变量和方法companion object

class Sample{
    companion object{
        init {
            //静态初始化
        }
    }
}

//顶层声明 属性和函数不写在class/object里,在package下，属于包。和静态变量，静态函数一样是全局的


//如果想写工具类的功能，直接创建文件，写 top-level「顶层」函数。
//如果需要继承别的类或者实现接口，就用  object 或 companion object。


//常量const
//常量必须声明在对象（包括伴生对象）或者「top-level 顶层」中
//只有基本类型和 String 类型可以声明成常量


//List 以固定顺序存储一组元素，元素可以重复。
//Set 存储一组互不相等的元素，通常没有固定顺序。
//Map 存储 键-值 对的数据集合，键互不相等，但不同的键可以对应相同的值。





//在一些性能需求比较苛刻的场景，并且元素类型是基本类型时，用数组好一点。不过这里要注意一点，
// Kotlin 中要用专门的基本类型数组类 (IntArray FloatArray LongArray) 才可以免于装箱。



//kotlin 数组不支持协变，List Set支持。java 数组支持List不支持

//val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 3)
//中缀表达式 to


//容器类型 Sequence，它和 Iterable 一样用来遍历一组数据并可以对每个元素进行特定的处理
//sequenceOf("a", "b", "c")

//val list = listOf("a", "b", "c")
//list.asSequence()

//val sequence = generateSequence(0) { it + 1 }
// 👆 lambda 表达式，负责生成第二个及以后的元素，it 表示前一个元素




//internal：内部，仅对 module 内可见。
//Kotlin 中如果不写可见性修饰符，就表示公开，和 Java 中 public 修饰符具有相同效果

//internal 表示修饰的类、函数仅对 module 内可见(java中方法的注释中添加一个@hide)，这里的 module 具体指的是一组共同编译的 kotlin 文件

// 常见的形式有：
//Android Studio 里的 module
//Maven project



//创建一个 Kotlin 类，这个类需要禁止外部通过构造器创建实例，并提供至少一种实例化方式。
class x{

    private constructor(){

    }

    companion object b{
        val xx = x()
    }

    fun newInstance(): x{
        return x()
    }
}
val xx = x.b.xx;

//分别用 Array、IntArray、List 实现 「保存 1-100_000 的数字，
// 并求出这些数字的平均值」，打印出这三种数据结构的执行时间
val uu = arrayOf(100000);
val range = IntRange(1, 100000)
val ii = range.forEachIndexed { index, i ->
    uu[index] = i
}



val hh : Array<Int> = arrayOf(1, 100000);
val jj : IntArray = intArrayOf(1, 100000)

class Sample{
    val maxCount = 100_000
    //Array 方式
    var timeArrayStart = LocalTime.now()
    var array = Array(maxCount) { i -> (i + 1) }
    var totalArray = 0
    val q = array.forEach {
        totalArray += it
    }
//    val w = println(array.average())
    val avgArray = BigDecimal(totalArray).divide(BigDecimal(array.size))
    val durationArray = Duration.between(timeArrayStart, LocalTime.now())
    val e = Log.e("tags", "Array 平均值=$avgArray 用时=$durationArray")

    //IntArray 方式
    var timeIntArrStart = LocalTime.now()
    var intArray = IntArray(maxCount) { i -> (i + 1) }
    var totalIntArr = 0
    val r = intArray.forEach {
        totalIntArr += it
    }
//    val y = println(intArray.average())
    val avgIntArr = BigDecimal(totalIntArr).divide(BigDecimal(intArray.size))
    val durationIntArr = Duration.between(timeIntArrStart, LocalTime.now())
    val t = Log.e("tags", "IntArray 平均值=$avgIntArr 用时=$durationIntArr")

    //List 方式
    var timeListStart = LocalTime.now()
    var list = List(maxCount) { i -> (i + 1) }
    var totalList = 0
    var u = list.forEach {
        totalList += it
    }
    val avgList = BigDecimal(totalList).divide(BigDecimal(list.size))
    val durationList = Duration.between(timeListStart, LocalTime.now())
    val i = Log.e("tags", "List 平均值=$avgList 用时=$durationList")
}

