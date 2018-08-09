package com.charlie.sawl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import com.charlie.sawl.expandableview.CategoryAdapter;
import com.charlie.sawl.expandableview.CategoryList;
import com.charlie.sawl.expandableview.Contents;
import java.util.Arrays;
import java.util.List;

public class QuickConnect extends Fragment {

    CategoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quickconnect, container, false);

        Contents one_0 = new Contents("1학년 부장 (대표 연락처)", "031-259-0610");
        Contents one_1 = new Contents("1-1 담임", "031-259-0611");
        Contents one_2 = new Contents("1-2 담임", "031-259-0612");
        Contents one_3 = new Contents("1-3 담임", "031-259-0613");
        Contents one_4 = new Contents("1-4 담임", "031-259-0614");
        Contents one_5 = new Contents("1-5 담임", "031-259-0615");
        Contents one_6 = new Contents("1-6 담임", "031-259-0616");
        Contents one_7 = new Contents("1-7 담임", "031-259-0617");
        Contents one_8 = new Contents("1-8 담임", "031-259-0618");

        Contents two_0 = new Contents("2학년 부장 (대표 연락처)", "031-259-0620");
        Contents two_1 = new Contents("2-1 담임", "031-259-0621");
        Contents two_2 = new Contents("2-2 담임", "031-259-0622");
        Contents two_3 = new Contents("2-3 담임", "031-259-0623");
        Contents two_4 = new Contents("2-4 담임", "031-259-0624");
        Contents two_5 = new Contents("2-5 담임", "031-259-0625");
        Contents two_6 = new Contents("2-6 담임", "031-259-0626");
        Contents two_7 = new Contents("2-7 담임", "031-259-0627");
        Contents two_8 = new Contents("2-8 담임", "031-259-0628");

        Contents three_0 = new Contents("3학년 부장 (대표 연락처)", "031-259-0630");
        Contents three_1 = new Contents("3-1 담임", "031-259-0631");
        Contents three_2 = new Contents("3-2 담임", "031-259-0632");
        Contents three_3 = new Contents("3-3 담임", "031-259-0633");
        Contents three_4 = new Contents("3-4 담임", "031-259-0634");
        Contents three_5 = new Contents("3-5 담임", "031-259-0635");
        Contents three_6 = new Contents("3-6 담임", "031-259-0636");
        Contents three_7 = new Contents("3-7 담임", "031-259-0637");
        Contents three_8 = new Contents("3-8 담임", "031-259-0638");

        Contents dorm_0 = new Contents("사감실", "031-259-0642");
        Contents dorm_1 = new Contents("기숙사부", "031-259-0640");
        Contents dorm_2 = new Contents("행정사감", "031-259-0641");

        Contents etc_0 = new Contents("행정실", "031-259-0502");
        Contents etc_1 = new Contents("보건실", "031-259-0555");
        Contents etc_2 = new Contents("입학홍보부", "031-259-0560");
        Contents etc_3 = new Contents("학생인권부", "031-259-0530");
        Contents etc_4 = new Contents("급식실", "031-259-0540");
        Contents etc_5 = new Contents("도서실", "031-259-0660");
        Contents etc_6 = new Contents("상담실 (위클래스)", "031-259-0671");

        Contents site_1 = new Contents("SAWLDOS", "http://211.51.255.102/login/login.php");
        Contents site_2 = new Contents("학교 홈페이지", "http://sawl.hs.kr/sys/");
        Contents site_3 = new Contents("기숙사 카페", "http://cafe.naver.com/swfl");
        Contents site_4 = new Contents("동아리 카페", "http://cafe.naver.com/sawlclubs");
        Contents site_5 = new Contents("수원외고 도서 검색", "http://reading.gglec.go.kr/r/reading/search/schoolSearchForm.jsp");
        Contents site_6 = new Contents("방과후 사이트", "http://sawl.schm.co.kr/");

        CategoryList first_grade = new CategoryList("1학년부", Arrays.asList(one_0, one_1, one_2, one_3, one_4, one_5, one_6, one_7, one_8));
        CategoryList second_grade = new CategoryList("2학년부", Arrays.asList(two_0, two_1, two_2, two_3, two_4, two_5, two_6, two_7, two_8));
        CategoryList third_grade = new CategoryList("3학년부", Arrays.asList(three_0, three_1, three_2, three_3, three_4, three_5, three_6, three_7, three_8));
        CategoryList dorm = new CategoryList("기숙사", Arrays.asList(dorm_0, dorm_1, dorm_2));
        CategoryList etc = new CategoryList("그 외 연락처", Arrays.asList(etc_0, etc_1, etc_2, etc_3, etc_4, etc_5, etc_6));
        CategoryList site = new CategoryList("사이트", Arrays.asList(site_1, site_2, site_3, site_4, site_5, site_6));

        final List<CategoryList> ContentsCategories = Arrays.asList(first_grade, second_grade, third_grade, dorm, etc, site);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAdapter = new CategoryAdapter(getActivity(), ContentsCategories);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}