fun main(args: Array<String>) {
    //集合创建
    println(list1[1])
    list2.add("44")
    list2.removeAt(0)
    println(list2[2])
    list3.add("55")
    list3.remove("y6")
    print("ttt" + list3)
    println(mulist)
    //不能直接初始化
    val arrayList = ArrayList<String>()
    arrayList.add("z33")

    //集合遍历
    for (s in list1){
        println(s)
    }
    for((index,s) in list1.withIndex()){
        println("$index $s")
    }
    list1.forEach {
        println(it)
    }
    list1.forEachIndexed(){ index, s ->
        println("$index $s")
    }

    //集合过滤
    val listFilter = list1.filter {
        it.equals("33")
    }
    println("listFilter:" + listFilter)

    //将过滤结果添加到集合中
    val listFilter1 = list1.filterTo(mutableListOf("haha")){
//        it.equals("33")
        it.startsWith("3")
    }
    println("listFilter1:" + listFilter1)

    list1.filterTo(list2){//直接加到list2中
        it.equals("33")
    }
    println("FilterTo list2:" + list2)

    list1.filterIndexed { index, s ->
        index % 2 == 0
    }

    //集合排序
    //排序
    println("list4原来：" + list4)
    val zhengList4 = list4.sorted()
    println("list4排序：" + zhengList4)
    //倒序
    val daoList4 = list4.sortedDescending()
    println("list4倒序：" + daoList4)


    val list3 = listOf("tzzz", "tony", "wangwu")
    val list4 = list3.sorted()
    val list5 = list3.sortedDescending()

    //对humanList 通过age字段排序正序
    val humanList1 = humanList.sortedBy {
        it.age
    }
    println("age排序：" + humanList1)
    //对humanList 通过age字段排序倒序
    val humanList2 = humanList.sortedByDescending {
        it.age
    }
    println("age倒序：" + humanList2)

    //自定义比较器，匿名类
    val newHuman = humanList.sortedWith(object:Comparator<Human1>{
        override fun compare(o1: Human1, o2: Human1): Int {
            val o1Age = o1.age
            val o2Age = o2.age
            return o2Age - o1Age
        }
    })
    println("自定义比较器" + newHuman)

    //集合分组
    val list6 = listOf("z3", "z4", "w5", "z3", "t6", "w6")
    val map = list6.groupBy {
//        if (it == "z3"){
//            "z3的key"
//        }else{
//            "其他的人"
//        }
        when{
            it.startsWith("z")->"张"
            it.startsWith("w")->"王"
            else->"qt"
        }
    }
    println(map)

    //joinToString将集合变成一个字符串
    val list7 = listOf("张三","威武","陈留")
    val newList2 = list7.joinToString(separator = "|", prefix = "(", postfix = ")")
    println(newList2)

    //map集合创建,map的key不能重复，词典
    val map1 = mapOf("z3" to 30, "w5" to 60)
    val map2 = mutableMapOf("中国" to "China", "英国" to "England")
    val map3 = HashMap<String, String>()

    println(map1)
    val eng = map1.get("中国")
    map2.put("日本", "Japan")
    map2.put("日本", "Japan")
    println(map2)

    //使用java的map
    val hashMap = HashMap<String, Int>()
    hashMap.put("z3", 40)

    //map集合遍历
    //entry
    for (entry in map2){
        val key = entry.key
        val value = entry.value
        println("entry:" + "key=$key value=$value")
    }
    //key
    for (key in map2.keys){
        println(key)
    }
    //values
    for (value in map2.values){
        println(value)
    }
    //key value
    for ((key, value) in map){

    }
    //foreach
    map2.forEach { t, u ->
        println(t + u)
    }
    //iterator
    val iterator = map2.iterator()
    while (iterator.hasNext()){
        val next = iterator.next()
        val key = next.key
        val value = next.value
    }

    //闭包 lambda表达式
    val t = test()
    t()
    t()
    t()
    test2()
    test2()
    test2()

    sum = add3(a, b)
    result = sub3(a, b)
    //高阶函数
    sum  = cacl(a, b, ::add3)//传递add函数
    result = cacl(a, b, ::sub3)

    //通过lambda表达式调用高阶函数，匿名函数
    //传递add3函数引用
    sum = cacl(a,b, {a,b->
        a+b
    })
    println(sum)
    result = cacl(a,b,{a,b->
        a-b
    })
    println(result)

    //如果lambda表达式在最后一个参数位置，可以简化
    sum = cacl(a,b){a,b->//匿名函数，传递add函数引用
        a+b
    }
    println(sum)
    result = cacl(a,b){a,b->
        a-b
    }
    println(result)

    //无参lambda表达式
     val hs = {
        println("无参lambda")
    }()
    //有参lambda表达式
    val hs2 = {a:Int,b:Int->
        a+b
    }(10,20)
    println("有参："+ hs2)

    //有返回值的lambda表达式，不需要return,返回值是最后一行
    val hs3 = {
        println("ni hao")
        "hehe"
        true
        20
    }()

    //通过变量保存lambda
    val function = {a:Int,b:Int->
        a+b
    }
    //调用
    function(10,20)

    //lambda表达式使用it
    //lambda 表达式只有一个参数，可以用it代替
    var c = 10
    newFun(c){
        println("只有一个参数" + it)
    }

    var funResult = newFun(c){
        val itt = it + 10
        println("只有一个参数2" + itt)
    }
    //lambda访问外部变量
    val outSite = {
        println(c)
    }()

    //四大表达式
    //1.Apply（使用this代表当前对象，返回值是当前对象）
    //2.Let(使用it代表当前对象，返回值是表达式最后一行)
    //3.With(使用this代表当前对象，返回值是最后一行)
    //4.Run(代码块)

    val list8 = mutableListOf("z3", "w4")
    //apply
    //T.apply,每个对象都有apply函数
    //apply里面传递的是一个函数T.()->Unit 这个函数是T 这个对象的扩展函数
    //this代表list集合
    //调用集合里面方法可以直接调用
    //apply函数返回的即是当前结合对象
    list8.apply {
        this//代表list8集合
        add("u8")
        add("upp8")
        add("u8")
        remove("u8")
    }.add("00o")
    println(list8)
    //let
    //T.let let函数是扩展函数
    //(T)-> R  let传递的是函数，函数参数是当前对象
    //let返回的就是lambda表达式返回值
    val letResult = list8.let {
        it.add("i9")
        it.add("tt8")
        "heeh"
    }
    println(letResult)
    //with
    /**
     * with包含在Kotlin的标准库中。它接收一个对象和一个扩展函数作为它的参数，
     * 然后使这个对象扩展这个函数。这表示所有我们在括号中编写的代码都是作为对象（第一个参数）的一个扩展函数，
     * 我们可以就像作为this一样使用所有它的public方法和属性。当我们针对同一个对象做很多操作的时候这个非常有利于简化代码。
     */

    //with不是扩展函数，是一个单独的函数
    //with第二个参数是传递的对象扩展函数T.()->R
    //this代表当前传递的对象
    //可以直接调用对象里的方法
    //with返回值是lambda表达式返回值
    val withResult = with(list8){
        add("666")
        add("888")
        "鸡鸡"
    }
    println(withResult)
    //Run
    //T.run()扩展函数
    //T.()->R 参数是扩展函数
    //this代表对象
    //可以直接调用对象里面方法
    val runResult = list8.run {
        add("hhh")
        add("0000000")
        "shuangji"
    }
    println(runResult)

}

//listOf不可变集合，不能添加不可修改
val list1 = listOf<String>("33", "37", "55", "88")
//mutableListOf可变集合
val list2 = mutableListOf<String>("55", "77", "2355")
val list4 = mutableListOf<Int>(44, 99, 1009)

val list3 = arrayListOf("5t", "y6", "h4")
//通过构造方法创造集合
val mulist = MutableList(10){
    "33";"666"
}



//自定义比较器
//list2.sortedWith(object: Comparator<String>{
//    override fun compare(o1: String, o2: String): Int {
//        return o1.compareTo(o2)
//    }
//})

class Human1(var name:String, var age:Int){
    override fun toString(): String {
        return "Human1($name $age)"
    }
}
val humanList = listOf(Human1("zhangsan", 30), Human1("lisi", 33), Human1("wangwu", 56))


//闭包,闭包让函数有了状态
fun test():()->Unit{
    var a = 10
   return {
       println(a++)
   }
}
fun test2(){
    var a = 10
    println(a)
    a++
}

//高阶函数，将函数作为参数或返回值的函数
//定义一个函数，可以传递不同函数，按照函数处理
fun cacl(a:Int, b:Int, action:(Int, Int)->Int):Int{
    return action(a,b)
}
//Lambda表达式
//Lambda就是匿名函数调用全面高阶函数
//sum = newCacl(a, b,{m,n->
//    m+n
//})


var a  = 10
var b = 20
var sum  =0
var result = 0

fun add3(a:Int, b:Int):Int{
    return a+b
}

fun sub3(a:Int, b:Int):Int{
    return a-b
}

//lambda调用全面的高阶函数，去掉()
//sum = newCacl(a,b){m,n->
//    m+n
//}

fun newFun(a:Int, action: (Int) -> Unit){
    action(a+10)
}


//setOf, arrayListOf, hashSetOf

