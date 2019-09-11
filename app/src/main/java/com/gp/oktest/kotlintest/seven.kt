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