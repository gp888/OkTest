import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//kotlin默认的修饰符是public

//一等公民，类、对象、接口……也就是包成员

//private修饰的 继承这个类的子类也不能使用它。

//protected 这个修饰符只能被用在类或者接口中的成员上，对子类可见

//一个定义为internal的包成员的，对所在的整个module可见


//委托属性
class Example {
    var mvalue: String by NotNullDelegate<String>()
}
//自定义委托NotNullSingleValueVar
class NotNullDelegate<T>(): ReadWriteProperty<Any?, T> {
    var mValue:T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return mValue ?: throw IllegalStateException("属性 not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.mValue = if (this.mValue == null) value
        else throw IllegalStateException("属性 already initialized")
    }
}

//标准委托
//Lazy
//它包含一个lambda，当第一次执行getValue的时候这个lambda会被调用，所以这个属性可以被延迟初始化。之后的调用都只会返回同一个值
//class App : Application() {
//    val database: SQLiteOpenHelper by lazy {
//        MyDatabaseHelper(applicationContext)
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        val db = database.writableDatabase
//    }
//}


//Observable
//这个委托会帮我们监测我们希望观察的属性的变化。当被观察属性的set方法被调用的时候，它就会自动执行我们指定的lambda表达式。所以一旦该属性被赋了新的值，我们就会接收到被委托的属性、旧值和新值。

//class ViewModel(val db: MyDatabase) {
//    var myProperty by Delegates.observable("") {
//        d, old, new ->
//        db.saveChanges(this, new)
//    }
//}


//Vetoable
//
//这是一个特殊的observable，它让你决定是否这个值需要被保存。它可以被用于在真正保存之前进行一些条件判断。
//
//var positiveNumber = Delegates.vetoable(0) {
//    d, old, new ->
//    new >= 0
//}
//上面这个委托只允许在新的值是正数的时候执行保存



//Not Null

//class App : Application() {
//    companion object {
//        var instance: App by Delegates.notNull()
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//    }
//}


//从Map中映射值
//
//另外一种属性委托方式就是，属性的值会从一个map中获取value，属性的名字对应这个map中的key。
class Configuration(map: Map<String, Any?>) {
    val width: Int by map
    val height: Int by map
    val dp: Int by map
    val deviceName: String by map
}