fun main(args: Array<String>) {

    val person = Person()
    person.name = "55"
    println(person.name)

    person.yaowei = "uu66"
    println("腰围" + person.yaowei)


    //对象的主构函数
    val student = Student("33", 50)

    //init关键字
    val soldior = soldior("33", 50)

    var person33 = Person33("3g", 18)
}

//Ctrl alt l格式化
class Person{
    var name:String = "44"
//    get() = "里斯。"
    get() {
        println("炸不到了")
        return field//有个坑,不能直接返回name,stackoverflow因为递归调用自身
        //backing field的作用域仅仅存在于当前属性的setter/getter方法中，、
        // 它就像当前属性的影子一样。因此，翻译成影子属性也许更合适。
    }
    set(value) {
        println("赋值了")
        field = value//stackvoerflow,有个坑
    }
    val age = 30

    //默认自动实现了set get方法
//    fun getName():String{
//        return name
//    }

    var height = "175"
    private set//不能修改height,修改访问器可见性

    private val grade = 3//不能访问不能修改


    var yaowei: String = ""
        get() = field.toUpperCase()
        set(value){
            field = "YaoWei: $value"
        }
}

class Student(name:String, age:Int){//主构函数
    var name = ""
    var age = 10
    override fun toString(): String {
        return "Student($name)"
    }

    init {
        //保存主构函数的参数
        this.name = name
        this.age = age
    }
}

class soldior(name:String, age:Int){
    //次构函数,必须调主构
    constructor(name: String, age: Int, phone:String):this(name, age)
    //次构可以相互调用
    constructor(name: String, age: Int,phone: String,email:String):this(name, age, phone)

    init{
        //对象初始化时会执行的操作,在主构函数执行
        println("hehe")
    }
}


class Student1(name:String, age:Int){
    //直接赋值
var name = name
    var age = age
    override fun toString(): String {
        return "Student($name)"
    }
}


class Student2(var name:String, var age:Int){//主构函数使用var修饰，可以使用和修改
    var phone = ""//次构需要自己定义变量保存
    override fun toString(): String {
        return "Student($name $phone)"
    }
    //次构里面不能定义var val
    constructor(name: String, age: Int, phone:String):this(name, age){
        this.phone = phone
        println("后执行")
    }
}

//执行主构，次构函数都执行init方法
//调用次构，先调用主构，执行init里代码，后执行次构
//闭包：大括号

//class 默认是final不能被继承
open class Father{
    open var name = ""//必须加open才能被复写
    open var age = 0
    open fun sayHello(){
        println("hehe")
    }

    override fun toString(): String {
        return "Father(name='$name', age=$age)"
    }

}

class son:Father(){
    override var name: String = "5555"
    override var age: Int = 10
    override fun sayHello() {
//        super.sayHello()
    }
}

//继承主构

open class Human(var name:String, var age:Int)
//父类主构有参数，子类继承必须要传递给父类
class Man(var newName: String, newAge: Int) : Human(newName, newAge)
//通过子类的主构函数调用父类的主构函数

class Woman:Human{
    //通过次构函数调用父类的主构函数
    constructor(name: String, age: Int):super(name, age)
}

abstract class BatMan(name: String, age: Int){
    abstract fun eat()
}

//抽象类不用open也能继承
class ZhMan(name: String, age: Int) : BatMan(name, age),Ride,Drive{
    override fun drive() {
    }

    override var licence: String = "马车假照"

    override fun rideBike() {
        println("能力。。")
    }

    override fun eat() {
        println("33")
    }
}

interface Ride{
    fun rideBike()
}

//抽象类，事物共性，代表物质本质，只能继承一个抽象类
//接口，代表能力，可以继承多个

interface  Drive{
    var licence:String//子类实现，不必加open
    fun drive()
}


class Person33(val name: String = "44", val age: Int = 18)