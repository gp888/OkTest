package com.gp.oktest.kotlintest

import androidx.appcompat.app.AppCompatActivity

/**
 * ViewPager对Fragment的支持非常的简单，整体流程：
        setAdapter时会基于当前position进行初始化当前Fragment
        接下来会基于mOffscreenPageLimit的值对需要“预加载”的Fragment进行初始化
        初始化该初始化的Fragment之后，调用commit()通知FragmentManager去attach Fragment



 */
class TestViewPagerActivity : AppCompatActivity() {
//    private lateinit var adapter: ViewPagerAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_view_pager)
//        adapter = ViewPagerAdapter(fragmentData, supportFragmentManager)
//        vp.adapter = adapter
//    }
//
//    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//        override fun getItem(position: Int): Fragment {
//            return when (position) {
//                0 -> TestFragment1.newInstance("页面-1")
//                1 -> TestFragment2.newInstance("页面-2")
//                else -> TestFragment3.newInstance("页面-3")
//            }
//        }
//
//        override fun getCount(): Int {
//            return 3
//        }
//    }
}

//FragmentPagerAdapter基于当前position进行初始化Fragment的逻辑

//基于一套规则生成的tag，通过findFragmentByTag()来找是否已经生成过Fragment。
//如果没有，调用getItem()，拿到我们自己重写后return的Fragment实例。


//public Object instantiateItem(@NonNull ViewGroup container, int position) {
//    if (mCurTransaction == null) {
//        mCurTransaction = mFragmentManager.beginTransaction();
//    }
//    // 基于position找到itemId，这方法的默认实现就是position
//    final long itemId = getItemId(position);
//
//    // 生成一个tag
//    String name = makeFragmentName(container.getId(), itemId);
//    // 通过上边生成的tag，在fragmentManager中试图找到一个Fragment的实例
//    Fragment fragment = mFragmentManager.findFragmentByTag(name);
//    // 如果找到，直接调用attach
//    if (fragment != null) {
//        if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
//        mCurTransaction.attach(fragment);
//    } else {
//        // 否则调用getItem()，基于我们自己的实现拿到Fragment实例。
//        fragment = getItem(position);
//        if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
//        mCurTransaction.add(container.getId(), fragment,
//                makeFragmentName(container.getId(), itemId));
//    }
//    if (fragment != mCurrentPrimaryItem) {
//        fragment.setMenuVisibility(false);
//        if (mBehavior == BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//            mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
//        } else {
//            fragment.setUserVisibleHint(false);
//        }
//    }
//    return fragment;
//}




//populate()方法中基于mOffscreenPageLimit来决定预加载position左右俩边多少个Fragment，1就意味着左右各预加载1个。
//由于mOffscreenPageLimit最小是1的原因，所以我们一次至少要加载2个Fragment

//一般都会用onHiddenChanged()/setUserVisibleHint()等方法来尝试做可见性的逻辑回调

//@Deprecated
//public FragmentStatePagerAdapter(@NonNull FragmentManager fm) {
//    this(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);
//}

//只有在滑动这个Fragment上时才会调这个Fragment的onResume()方法。
//
//但是注意是回调onResume()。而onResume之前的方法，已经在getItem()中实例化Fragment的时候调完了。




//public int getItemPosition(@NonNull Object object) {
//    return POSITION_NONE;
//}
// POSITION_UNCHANGED时，意味着当前视图没有发生改变，return POSITION_NONE意味着发生改变。

//这个方法只会在ViewPager的dataSetChanged()中被调用，因此我们可以确认重写这个方法只会在主动尝试更新ViewPager时生效。


//使用notifyDataSetChanged()去刷新ViewPager时，getItemPosition()的返回时决定当前的Fragment是否需要被remove。
// 因此当我们不需要remove当前的Fragment时，则return POSITION_UNCHANGED（这样此Fragment就不会发生任何状态变化），
// 否者则return POSITION_NONE（这样此Fragment就会被remove，然后重新初始化新的Fragment）