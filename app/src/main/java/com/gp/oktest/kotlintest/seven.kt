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


//lateinit 的意思是：告诉编译器我没法第一时间就初始化，但我肯定会在使用它之前完成初始化的。
//它的作用就是让 IDE 不要对这个变量检查初始化和报错。

//field 本质上确实是一个 Java 中的 field，但对于 Kotlin 的语法来讲，
// 它和 Java 里面的 field 完全不是一个概念。在 Kotlin 里，它相当于每一个 var 内部的一个变量


//kotlin默认为public
class User {
    init {
        //初始化代码块，实例化时执行，先于次器执行，java 不需要加init
    }

    constructor() {

    }
}
//kotlin函数参数默认是val

val size: Int
    get() {
        return 5
    }

//object 创建一个类，并创建这个类对象，既有class 关键字功能，又实现单例。
//object: 创建匿名类

//一个类最多有一个伴生对象，可以有多个嵌套对象（object）。静态变量和方法companion object

class Sample {
    companion object {
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

val sequence = generateSequence(0) { it + 1 }
// 👆 lambda 表达式，负责生成第二个及以后的元素，it 表示前一个元素


//internal：内部，仅对 module 内可见。
//Kotlin 中如果不写可见性修饰符，就表示公开，和 Java 中 public 修饰符具有相同效果

//internal 表示修饰的类、函数仅对 module 内可见(java中方法的注释中添加一个@hide)，这里的 module 具体指的是一组共同编译的 kotlin 文件

// 常见的形式有：
//Android Studio 里的 module
//Maven project


//创建一个 Kotlin 类，这个类需要禁止外部通过构造器创建实例，并提供至少一种实例化方式。
class x {

    private constructor() {

    }

    companion object b {
        val xx = x()
    }

    fun newInstance(): x {
        return x()
    }
}

val xx = x.b.xx;

//分别用 Array、IntArray、List 实现 「保存 1-100_000 的数字，
// 并求出这些数字的平均值」，打印出这三种数据结构的执行时间

//IntArray背后是基本数据类型int的数组，最快是由于没有拆装箱
//List背后是ArrayList
//Array背后是Interger类型的数组
//由于求平均值的函数底层实现不同
fun main(args: Array<String>) {
    val maxCount = 100_000
    //Array 方式
    var timeArrayStart = LocalTime.now()
    var array = Array(maxCount) { i -> (i + 1) }
    var sum = 0
    array.forEach {
        sum += it
    }
//    val avgArray = array.average()
    val avgArray = BigDecimal(sum).divide(BigDecimal(array.size))
    val durationArray = Duration.between(timeArrayStart, LocalTime.now())
    Log.e("tags", "Array 平均值=$avgArray 用时=$durationArray")

    //IntArray 方式
    var timeIntArrStart = LocalTime.now()
    var intArray = IntArray(maxCount) { i -> (i + 1) }
    var sum1 = 0
    intArray.forEach {
        sum1 += it
    }
//    val avgIntArr = intArray.average()
    val avgIntArr = BigDecimal(sum1).divide(BigDecimal(intArray.size))
    val durationIntArr = Duration.between(timeIntArrStart, LocalTime.now())
    Log.e("tags", "IntArray 平均值=$avgIntArr 用时=$durationIntArr")

    //List 方式
    var timeListStart = LocalTime.now()
    var list = List(maxCount) { i -> (i + 1) }
    var sum2 = 0
    list.forEach {
        sum2 += it
    }
//    val avgList = list.average()
    val avgList = BigDecimal(sum2).divide(BigDecimal(list.size))
    val durationList = Duration.between(timeListStart, LocalTime.now())
    Log.e("tags", "List 平均值=$avgList 用时=$durationList")
}


//Kotlin 中一个类最多只能有 1 个主构造器（也可以没有），而次构造器是没有个数限制
class User1 constructor(name: String) {
    //                  👇 这里与构造器中的 name 是同一个
    var name: String = name

    //类的属性 name 可以引用构造器中的参数 name
    init {
        this.name = name
    }
}
//init 代码块是紧跟在主构造器之后执行的，init 代码块就充当了主构造器代码体的功能
//创建类的对象时，不管使用哪个构造器，都需要主构造器的参与
//在类的初始化过程中，首先执行的就是主构造器

//使用次构造器创建对象时，init 代码块是先于次构造器执行的


//主构造器里声明属性

class User2(var name: String) {
}
// 等价于：
class User3(name: String) {
    var name: String = name
}



//本地函数（嵌套函数）
//kotlin 内置的require函数

val intArray = intArrayOf(1, 2, 3)
//对每个元素进行过滤操作
val newList: List<Int> = intArray.filter { i ->
    i != 1 // 👈 过滤掉数组中等于 1 的元素
}
//遍历每个元素并执行给定表达式
val newList1: List<Int> = intArray.map { i ->
    i + 1 // 👈 每个元素加 1
}
//遍历每个元素，并为每个元素创建新的集合，最后合并到一个集合中
val newList2 = intArray.flatMap { i ->
    listOf("${i + 1}", "a") // 👈 生成新集合
}

//以上操作list也有



//IntRange ，CharRange  LongRange。
fun main() {
//Sequence 又被称为「惰性集合操作」
    val sequence1 = sequenceOf(1, 2, 3, 4)
    val result1: Sequence<Int> = sequence
            .map { i ->
                println("Map $i")
                i * 2
            }
            .filter { i ->
                println("Filter $i")
                i % 3  == 0
            }
//👇
    println(result1.first()) // 👈 只取集合的第一个元素
}
//println 执行时数据处理流程是这样的：
//
//取出元素 1 -> map 为 2 -> filter 判断 2 是否能被 3 整除
//取出元素 2 -> map 为 4 -> filter 判断 4 是否能被 3 整除
//...
//惰性指当出现满足条件的第一个元素的时候，Sequence 就不会执行后面的元素遍历了，即跳过了 4 的遍历。


//像 List 这种实现 Iterable 接口的集合类，每调用一次函数就会生成一个新的 Iterable，
// 下一个函数再基于新的 Iterable 执行，
// 每次函数调用产生的临时 Iterable 会导致额外的内存消耗，而 Sequence 在整个流程中只有一个


//Sequence 这种数据类型可以在数据量比较大或者数据量未知的时候，作为流式处理的解决方案



//?:
//Java 中的 equals ，在 Kotlin 中与之相对应的是 ==