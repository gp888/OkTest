import kotlin.reflect.KProperty

fun main(args: Array<String>) {
    val son = Son()
    son.sayHello()

    val dog:Animal = Dog()
    dog.call()//智能类型推断
    println("${dog is Dog}")//is判断类型

    val bird1:Bird = BaiLing()
    call(bird1)

    //嵌套函数 嵌套类
    val inClass = OutClass.InClass()

    val inClass1 = OutClass1().InClass()
    inClass1.sayHello()


    val box = Box<String>("44")
    val box1 = Box<Int>(99)
    val box2 = Box<Boolean>(true)

    val data = MyData()
    data sayHello "李四"


    val father = Father1()
    father.wash()
    val fater2 = Father2(Son1())
    fater2.wash()


    val bigSon = BigSon()
    bigSon.money = 100
    bigSon.money -= 50

    val bigSon1 = BigSon()
    println(bigSon1.money)

    val human = HuMan()
    human.eat()

    val view:View = Button()
    view.showOff()
    val view1:Button = Button()
    view1.showOff()


    var c = C1()
    c.testFun()//调用成员函数


    val student3 = Student3("55", 50)
    student3.phone = "44"
    println(student3.phone)
    println(student3.name)

    //object
    Data.sayHi()
    println(Data.name)

    MyData1.sayhi()
    MyData1.banClass.sayhi()

    //java 单例
   Single.single.sayHi()

    val p1 = Person1.person1
    println(p1.title)
    println(p1.age)
}

interface CallBack{
    fun sayHello(){
        //接口中方法可以实现
        println("hehe")
    }
}

class Son:CallBack{

}

open abstract class Animal{
    abstract fun call()
}

class Dog:Animal(){
    override fun call() {
        println("heheh")
    }
}

open class Bird

class BaiLing:Bird(){
    fun jiJi(){
        println("jiji")
    }
}

fun call(bird:Bird){
    //java 写法
    if(bird is BaiLing){
//        val newBaiLing = bird as BaiLing //as 强制类型转换
//        newBaiLing.jiJi()
        bird.jiJi()//一但判断出类型之后，自动转换
    }
}

//嵌套类(静态内部类)
class OutClass{
    var name = "44"
    class InClass{
        fun sayHello(){
            //不能访问外部类变量，不依赖外部类环境
//            println(name)
        }
    }
}

//内部类,inner关键字,依赖外部类环境
class OutClass1{
    var name = "44"
    inner class InClass{
        var name = "88"
        fun sayHello(){
//            outClass.this.name
            println(name)//可以访问外部类的变量,打印88
            println(this@OutClass1.name)//打印44
        }
    }
}

//类泛型
open class Box<T>(var thing:T)
//父类有泛型，子类需指定泛型具体类型
class sonBox(var name:String):Box<String>(name)

//泛型函数
fun <T>parseType(data:T){
    when(data){
        is String -> println("字符串")
        is Boolean -> println("Boolean类型")
    }
}

//中缀表达式
class MyData{
    infix fun sayHello(name: String){
        println(name)
    }
}

//类代理 by
interface WashPower{
    fun wash()
}
class Son1:WashPower{
    override fun wash() {
        println("son wash")
    }
}
class Father1:WashPower by Son1()

class Father2(var son:Son1):WashPower by son

class Father3(var son:Son1):WashPower by son{
    override fun wash() {
        println("前")
        son.wash()
        println("后")
    }
}
class Father5(var wash:WashPower):WashPower by wash//竟然对。。。。

//属性委托
class BigSon{
    var money:Int by Father4()//get set方法由Father4实现
}

class Father4 {
    var sonMoney = 0
    operator fun getValue(bigSon: BigSon, property: KProperty<*>): Int {
        return sonMoney
    }

    operator fun setValue(bigSon: BigSon, property: KProperty<*>, i: Int) {
        this.sonMoney = i
    }
}
//属性延迟加载by lazy
// 用到时才创建
//只创建一次
//线程安全
val lazyValue:String by lazy {
    println("init")
    "hello"
}

//扩展函数，不用定义新的类，而增加新的方法
class HuMan
fun HuMan.eat(){
    println("敬业福")
}

//扩展函数是静态加载的
open class View
class Button:View()
fun View.showOff() = println("view")
fun Button.showOff() = println("button")

//如果成员函数和扩展函数相同，调用的是成员函数，优先
class C1{
    fun testFun(){
        println("c11")
    }
}

fun C1.testFun(){
    println("c11")
}

//扩展属性,需要通过get方法赋值
//扩展属性并不是一个字段，只是生成了一个getPhone()
//如果扩展属性是var，说明只是生成了get和set方法，并没有创建这个字段
//如果属性不可变，使用扩展属性比较方便

/**
 * 扩展并不会真正地往类中插入成员变量。
 * 因此，我们没有一个有效的方式让一个扩展属性拥有backing field，
 * 这就是扩展属性不允许被初始化的原因，所以声明扩展属性的时候我们就不得不自己实现setter/getter方法
 */
class Student3(var name: String, var age:Int)

var Student3.phone
get() = "45555"
set(value) {
    //没有field
//    field = value
    name = value
}

//object关键字，单例
//不创建建当前对象
//直接类.属性，访问字段
object Data{
    var name = "444"//属性过多，不推荐这种方式
    fun sayHi(){
        println("44")
    }
}
//伴生对象companion，创建了一个静态类
class MyData1{
    companion object banClass{//可指定名
        fun sayhi(){
            println("伴生对象")
        }
    }
}

//伴生单例，java 模式
class Single private constructor(){
     var name = "44"
     var age = 40
    companion object {
        //定义single 当使用时只会创建一次，by lazy线程安全
        val single by lazy { Single() }
    }
    fun sayHi(){
        println("555")
    }
}


//枚举
enum class Week{
    星期一, 星期二
}
fun todo(week:Week){
    when(week){
        Week.星期一 -> println("444")
    }
    //获取值
    val value:Array<Week> = Week.values()
    value.forEach {
        println(it)
    }
}

//enum不能传递name字段
enum class Person1(var title: String, var age:Int){
    person1("z3", 40),
    person2("z4", 44),
    person3("z5", 46),
}

//数据类-》 Bean类
//get set tostring copy hashcode equals都是现了
data class News(var title: String, var content:String)

//密封类
sealed class Stark{
    class RobStark:Stark()
    class SansaStark:Stark()
}

class JonSnow:Stark()

fun hasRightOfInheritance(stark:Stark):Boolean{
    when(stark){
        is Stark.RobStark-> println("有")
        is Stark.SansaStark-> println("有")
        else->println("没有")
    }
    return false
}

//listOf, arrayListOf, mutableListOf

