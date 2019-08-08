
fun main(args: Array<String>) {
    sayHello()
    sayHello("hehe")
    val  leng = getNameLength("xiao lll")


    val s = "白雪却嫌春色晚"
    for(c in s) {
        println(c)
    }

    for ((index, c) in s.withIndex()) {
        println("$index $c")
    }

    s.forEach {//高级for循环
        println(it)
    }

    s.forEachIndexed { index, c ->
        println("$index $c")
    }

    for(c in s) {
        if (c == '色') {
            break
            //continue
        }
        println(c)
    }

    val s1 = "abc"
    val s2 = "123"

    //跳出标签
    loop333@for (c1 in s1) {
        for (c2 in s2) {
            if (c1 == 'b' && c2 == '2') {
                break@loop333
            }
            println("$c1 $c2")
        }
    }


    var a = 100
//    while (a > 0) {
//        println(a)
//        a = a - 1
//    }

    //不满足条件也会执行一次
    do {
        println(a)
        a = a - 1
    }while (a > 0)

    //IntRange
    val range = 1..100//[1,100]
    var range2 = IntRange(1, 100)//[1,100]
    var range3 = 1 until 100//[1, 100)
    //LongRange
    val longRange = 1L..100L
    //CharRange
    val charRange = 'a'..'z'

    //区间遍历
    for(bb in range) {
        println(bb)
    }

    for ((index, cc) in range.withIndex()) {
        println("$index $cc")
    }

    range.forEach {
        println(it)
    }

    range.forEachIndexed { index, i ->
        println("$index $i")
    }


    val range4 = 10 downTo 1//反向区间
    range.reversed()

    sub(6, 9)


    //数组
    val arr1 = arrayOf("22", "55", "77")
    val arr2 = arrayOf("22", "55", "77", 40)

    //BooleanArray
    //ByteArray
    //ShortArray
    //CharArray
    //Int, Float, Double, LongArray
    //没有StringArray


    val intArray1 = IntArray(10)//Int数组长度为10

    val intArray2 = IntArray(10) {//每个元素都是0
        0
    }

    for((index, i) in arr1.withIndex()) {
        println("$index $i")
    }

    arr1.forEach {
        println(it)
    }
    arr1.forEachIndexed { index, s ->
        println("$index $s")
    }

    //数组元素赋值
    arr1[1] = "我们"
    arr1.set(1, "666")

    val index1  = arr1.indexOf("22")//返回第一个22的角标
    arr1.lastIndexOf("22")//返回最后一个22的角标

    val index3 = arr1.indexOfFirst { //如果是第一个22就返回角标
        it == "22"
    }


//    if(n in 1.rangeTo(5)) {
//
//    }
    //字符串模板可以调用函数

    var sum = 0
    var flag = 0
    var workArray = 1..100
    workArray.forEach {
        if (it % 7 != 0) {
            sum += it
            flag ++
            if (flag == 4) {
                println(it)
                flag = 0
            } else{
                print(it)
            }
        }
    }
}

fun sayHello():Unit {//相当于void
    println("hello")
}

fun sayHello(name:String) {
    println("hello $name")
}

fun getNameLength(name:String):Int {
    return name.length
}

fun haha():String {
    return "haah"
}

fun sub(m:Int, n:Int):Int {
    //if语句最后一行是返回值，Any 所有类型父类
//    return if (m > n) {
//        m - n
//    }else{
//        n - m
//    }

    val result = if (m > n) {
        println(" m > n")
        m - n
    }else{
        n - m
    }
    return result
}