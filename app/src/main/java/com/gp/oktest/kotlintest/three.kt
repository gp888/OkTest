import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    //when多分支判断语句switch,转换为switch 和if else
    //todo1(5)


    //函数和对象 一等公民
    val a = 9
    val b = 8
    var sum = 0
    val add2Fun = ::add2//::获取add2函数的引用

    //第一种执行方式
    sum = add2Fun(a, b);
    //第二种执行方式
    sum = add2Fun.invoke(a, b)

    //定义函数变量，并直接定义函数
    val addFun3:(Int, Int) -> Int = {a, b -> a + b}//类型和函数
//    val addFun3 = {a:Int, b:Int -> a + b}
    sum = addFun3(a,b)
    sum = addFun3.invoke(a,b)



    var name = "66"
    var age = 30
    sayHello(age = age)//具名参数
    sayHello(age = 20,name="44")//具名参数顺序可以调换

    ///可变参数
    var a2 = 10
    var b2 = 20
    var c2 = 40
    println(newAdd(10, 20,30,40,50, par = "heeh"))

    //函数可以独立存在
    //嵌套函数，没有执行
    fun printHello(age:Int){
        //可以访问外部函数变量
        println("hello")
    }

    //异常
    var t = 10
    var u = 0
    try {
        println(div(t,u))
    }catch (e:Exception){
        println(e.message)
    }finally {

    }

    //kotlin 没有编译时异常提示
    val file = File("")
    val bfr = BufferedReader(FileReader(file))

    //递归
    fun fib(n:Int):Int{
        if (n==1)return 1
        if (n==2)return 1
        return fib(n-1) + fib(n-2)
    }

    var result = 0
    fun sum11(n:Int):Int{
        val range:IntRange = 1..n
        range.forEach {
            result += it
        }
        return result
    }

    fun sum22(n:Int):Int{
        if(n==1) return 1
        return sum22(n-1) + n
    }
    //迭代能实现的递归也能实现？

    //尾递归优化：将递归转换成迭代

    tailrec fun sum33(n:Int, result:Int):Int{
        if(n==1)return 1+result
        return sum33(n-1, n+result)
    }

    class Person{
        var age:Int = 0
        var name:String = "44"
        override fun toString(): String {
            return "Person(name='$name')"
        }
        //运算符重载
        operator fun inc():Person{
            age++
            return this
        }

    }

    var gg = 10
    var oo = 9
    gg + oo

    //+ (plus)- * /(div) ..(rangeTo) ++(inc)都对应函数
    //运算符重载
    //对象可相加

//操作符===和!==用来做身份检查（它们分别是Java中的==和!=），并且它们不能被重载。
}

fun todo1(age:Int):String{
//    when(age) {
//        in 7..11->return "吃饭"
//        12->return "吃kkk"
//        15->return "999"
//        18->return "jgjjd"
//        is Int -> return "是什么类型"//instanceOf
//        else->return "ffff"
//    }
//
//    when{
//        age == 4->return "hehe"
//        10>20->return "hhhhh"
//        else->return "ddd"
//    }


    val result = when(age) {
        in 7..11-> {
            println("chifna")
            "吃饭"//返回最后一行
        }
        12-> "吃kkk"
        15-> "999"
        18-> "jgjjd"
        is Int ->  "是什么类型"//instanceOf
        else-> "ffff"
    }
    return result
}

fun add(a:Int, b:Int):Int{
    return a+b
}
fun add1(a:Int, b:Int):Int = a+b
fun add2(a:Int, b:Int) = a+b


fun sayHello(name:String="zhang3", age:Int){//默认参数
    println("$name $age")
}

fun newAdd(vararg params:Int, par:String?):Int{//vararg可变参数，传递多个相同类型参数,IntArray
   //params 数组
    var sum = 0
    params.forEach {
        sum += it
    }
    return sum
}

fun div(a:Int, b:Int):Int{
    if (b==0){
        throw Exception("分母为0了")
    }else{
        return a/b
    }
}