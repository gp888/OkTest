package com.gp.oktest.kotlintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.gp.oktest.R
import kotlinx.android.synthetic.main.activity_second.*//不用findView
import org.jetbrains.anko.*

/**
 * Created by guoping on 2017/9/4.
 */

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_second)
//        tv_hehe.text = "改变了"


        //anko layout
//        verticalLayout {
//            val textView = textView("封魔") {
//                textSize = sp(10).toFloat()
//                textColor = context.resources.getColor(R.color.colorAccent)
//            }.lparams {
//                margin = dip(10)
//                height = dip(40)
//                width = matchParent
//            }
//            val name = editText("EditText")
//            button("Button") {
//                onClick { toast("${name.text}") }
//            }
//        }

        UI().setContentView(this@SecondActivity)
    }


//    lambda 表达式总是被大括号括着，
//    其参数（如果有的话）在 -> 之前声明（参数类型可以省略），
//    函数体（如果存在的话）在 -> 后面。
    inner class UI: AnkoComponent<SecondActivity> {
        override fun createView(ui: AnkoContext<SecondActivity>): View {
            return with(ui) {
                verticalLayout {
                    val textView = textView("杰克有魔法") {
                        textSize = sp(10).toFloat()
                        textColor = context.resources.getColor(R.color.colorAccent)
                    }.lparams {
                        leftMargin = dip(10)
                        height = dip(40)
                        width = matchParent
                    }

                    val name = editText("hehehe")
                    button("button") {
                        setOnClickListener {
                            view:View -> toast("Hello, ${textView.text}")
                        }
                    }
                }
            }
        }

    }
}
