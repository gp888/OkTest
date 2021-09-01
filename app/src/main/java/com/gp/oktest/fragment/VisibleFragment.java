package com.gp.oktest.fragment;

import androidx.fragment.app.Fragment;

public class VisibleFragment extends Fragment {
    private boolean isCanShowing = true;

    @Override
    public void onResume() {
        super.onResume();
        isCanShowing = isVisible();
    }

    @Override
    public void onHiddenChanged(boolean hidden) { 
        isCanShowing = !hidden;
        onVisibleChanged(isVisibleOnScreen());
    }
    
     @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanShowing = isVisibleToUser;
        onVisibleChanged(isVisibleOnScreen());
    }
    
    protected void onVisibleChanged(boolean isVisible) {
    }

    @Override
    public void onStop() {
        super.onStop();
        isCanShowing = false;
    }

    // 判断可见性，对手动显示与PagerAdapter方式均有效，且跟随父fragment可见性状态
    public boolean isVisibleOnScreen() {
        if (isCanShowing && getUserVisibleHint() && isVisible()) {
            if (getParentFragment() == null) {
                return true;
            }

            if (getParentFragment() instanceof VisibleFragment) {
                return ((VisibleFragment) getParentFragment()).isVisibleOnScreen();
            } else {
                return getParentFragment().isVisible();
            }
        }
        return false;
    }
}
