package com.gp.oktest.kotlintest

import kotlin.properties.Delegates

/**
 * Created by guoping on 2017/9/1.
 */

open class FirstClass(var str: String) {
//    protected  //同一个文件中或子类可见,不可修饰类
//    internal //同一个模块中可见,若类不加修饰符，则默认为该修饰符，作用域为同一个应用的所有模块，起保护作用，防止模块外被调用。
//    类默认是final的， open类可继承
//    Byte 8位， Short 16位， Int 32位，Long 64位， Float 32位， Double 64位
    //shl 左移<<，shr 右移>> ,ushr  无符号右移,高位补0>>>, and or xor 按位与&，或|，异或^

    //逻辑操作符：or() == ||, and() == &&, xor() == 两边相反时为true, not()取反
    var f: Float = 32.0f
    fun main(args: Array<String>) {
        str = "hehesss"
        println(str)
    }

    //Array 提供了get(index),set(index, value),iterator
    val iArray: IntArray = intArrayOf(1, 2, 3, 4)
    val sArray: Array<String> = Array<String>(4, {i -> i.toString()})

    val anyArray: Array<Any> = arrayOf(1, "2", 3L, 3.0,  4.1f)
    val lArray: LongArray = longArrayOf(1L, 2L, 3L)


    fun max1(a: Int, b:Int)= if (a > b) {
        println(a)
        a
    } else {
        println(b)
        b
    }

    val String.lashChar: Char
    get() = this[this.length - 1]

    fun isOdd(x: Int) = x % 2 != 0
    val numbers = listOf(1, 2, 3)

}

open class People constructor(var id: String, var name: String) {
    var customName = name.toUpperCase()//初始化属性

    //次构造函数，使用constructor前缀声明，必须调用primary constructor,使用this关键字
    constructor(id: String, name: String, age:Int) :this(id, name) {
        println("secondary constructor")
    }

    init {
        println("初始化，可以使用primary constructor的参数" + id + name)
    }

    open fun study() {
        println("study")
    }

    //伴生对象，static
    companion object {
        val ID = 1
    }
}

open class Student(id: String, name: String) : People(id, name) {
    var test:Number = 3

    private var name1: String?
        get() {
            return name1
        }
        set(value) {
            name1 = value
        }

    override fun study() {
        super.study()

//        staff.name = "hehe"
//        var staff1 = staff.copy()
//        var staff2 = staff.copy(name = "ccc", position = "kotlin")
//        println("name:${staff2.name} position:${staff2.position} age${staff2.age}")

        var anotherStaff = Staff("gp", "android", "22")
        println("staff toString: ${staff.toString()}")
        println("anotherStaff toString(): ${anotherStaff.toString()}")
        println("staff.hashCode(): ${staff.hashCode()}")
        println("anotherStaff hashCode(): ${anotherStaff.hashCode()}")
        println("staff is equals anotherStaff ? ${staff.equals(anotherStaff)}")


        var employee = Employee("nxin")
        employee.print()
        employee.println()
        println(employee.toString1())
        println(employee.lastName)


        show()
        var water = Water()
        println(water.name)
        println(water.name)
        water.values = "1111"
        water.values = "2222"
        water.values = "3333"
        println(water.values)
        println(water.values)
        water.weight = 2
        println(water.weight)
    }

}

//数据类，编译器默认会生成四个方法 equals(), hashCode(), toString, copy()
data class Staff<T>(var name: String, val position: String, var age: T)
var staff = Staff("gp", "android", 22)


//object expressions 匿名内部类
open class KeyBoard{
    open fun onKeyEvent(code: Int):Unit = Unit
}

var key = object: KeyBoard() {
    override open fun onKeyEvent(code: Int): Unit = Unit
}

enum class Color {
    RED,BLACK,BLUE,GREEN,WHITE
}

fun display() {
    var color: Color = Color.BLACK

    var color1: Color = Color.valueOf("BLACK")

    var arrayColor: Array<Color> = Color.values()

    println(color.name)
    println(color.ordinal)//color的索引
}

class Employee(var name: String) {
    fun print() {
        println("Employee")
    }
}

//扩展函数：待扩展类名.自定义函数名(参数可为空)
/**
 * 扩展函数作用域，受函数的visibility modifiers影响
 * 扩展函数并没有对原类做修改，而是为被扩展类的对象添加新的函数
 */
fun Employee.println() {
    println("println:Employee name is $name")
}

//可以扩展空对象
fun Any?.toString1(): String {
    if (this == null)
        return "toString1: null"
    else
        return "toString1" + this.toString()
}

//扩展属性,只能声明为val,不允许存在初始化器
val Employee.lastName: String
    get() {
        return "get:" + name
    }


interface Base {
    fun display()
}

open class BaseImpl: Base {
    override fun display() = println("just display me")
}

//代理类
class Drived(base: Base): Base by base

//使用
fun show() {
    var b = BaseImpl()
    var drived = Drived(b)
    drived.display()
}

/**
 * 代理类型
 * 懒加载：Lazy
 * 观察者：Delegates.observable()
 * 非空属性：Delegates.notNull<>()
 */
class Water {
    public var weight: Int by Delegates.notNull()

    public val name: String by lazy {
        println("Lazy.......")
        "gpgp"
    }

    public var values: String by Delegates.observable("init value") {
        d, old, new ->
        println("$d-->$old-->$new")
    }
}

//单例,object关键字，访问直接使用类名，不能通过构造函数进行访问，不允许有构造，Singleton.doSomething()
object Singleton {
    fun doSomething() {
        println("doSomething")
    }
}

/**
 * 实例化的时候单例是懒加载的，当使用的时候才去加载；而对象表达式实在初始化的地方去加载
 *
 * 当在类内部使用object 关键词定义对象时，允许直接通过外部类的类名访问内部对象，进而访问其相关属性和方法
 * 可以使用companion修饰单例，则访问其属性或方法时，允许省略单例名
 * MyClass.doSomething()//访问内部单例对象方法
 */
class myClass {
    companion object Singleton{
        fun doSomething() {
            println("doSomething")
        }
    }
}