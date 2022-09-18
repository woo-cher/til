package com.study.til.design_pattern.factory._01_before;

import com.study.til.design_pattern.factory.Cold;
import com.study.til.design_pattern.factory.Digestive;
import com.study.til.design_pattern.factory.HeadachePill;
import com.study.til.design_pattern.factory.Medicine;

public class Pharmacy {
    public static void main(String[] args) {
        // 목이 아프다하면
        Medicine m1 = new Cold();
        m1.get();

        // 밥 먹고 체했다면
        Medicine m2 = new Digestive();
        m2.get();

        // 머리가 아프다면
        Medicine m3 = new HeadachePill();
        m3.get();
    }
}
