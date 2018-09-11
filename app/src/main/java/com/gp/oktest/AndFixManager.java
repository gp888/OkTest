package com.gp.oktest;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;


//andfix
public class AndFixManager {

    private static AndFixManager mInstance;

    private static PatchManager mPatchManager;

    private AndFixManager(){}

    public static AndFixManager getAndFixManager(){
        if (mInstance == null){
            synchronized (AndFixManager.class){
                if (mInstance == null)
                    mInstance = new AndFixManager();
            }
        }
        return mInstance;
    }

    public void initAndFix(Context context){
        //step.1 init PatchManager
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Utils.getVersionName());
        //step.2 load patch
        mPatchManager.loadPatch();
    }

    public void addPatch(String patchPath){
        if (mPatchManager != null) {
            try {
                mPatchManager.addPatch(patchPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
