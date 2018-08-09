package com.charlie.sawl.schedule;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.charlie.sawl.R;
import com.charlie.sawl.tools.RecyclerItemClickListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleContents extends Fragment {

    public static Fragment getInstance(int month) {
        ScheduleContents mFragment = new ScheduleContents();

        Bundle args = new Bundle();
        args.putInt("month", month);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final ScheduleAdapter mAdapter = new ScheduleAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View mView, int position) {
                try {
                    String date = mAdapter.getItemData(position).date;
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);

                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.setTime(mFormat.parse(date));
                    long touchTime = mCalendar.getTimeInMillis();

                    long diff = (touchTime - System.currentTimeMillis());

                    boolean isPast = false;
                    if (diff < 0) {
                        diff = -diff;
                        isPast = true;
                    }

                    int diffDays = (int) (diff / (86400000));
                    int futureDays = diffDays+1;
                    String mText = "";

                    if (diffDays == 0) {
                        mText = "오늘 일정입니다.";
                    } else if (isPast) {
                        mText = "선택하신 날짜는 " + diffDays + "일전 날짜입니다.";
                    } else if (diffDays > 0) {
                        mText = "선택하신 날짜까지 " + futureDays + "일 남았습니다.";
                    }

                    Snackbar.make(mView, mText, Snackbar.LENGTH_SHORT).show();
                } catch (Exception ignored) {}
            }
        }));

        Bundle args = getArguments();
        int month = args.getInt("month");

        switch (month) {
            case 3:
                mAdapter.addItem("3.1절", "2018.03.01 (목)", false, true);
                mAdapter.addItem("입학식", "2018.03.02 (금)");
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2018.03.08 (목)");
                mAdapter.addItem("퇴사", "2018.03.09 (금)", true, false);
                mAdapter.addItem("학부모 학교 설명회", "2018.03.16 (금)");
                mAdapter.addItem("기숙사 재난대피훈련", "2018.03.22 (목)");
                mAdapter.addItem("퇴사", "2018.03.23 (금)", true, false);
                break;
            case 4:
                mAdapter.addItem("퇴사", "2018.04.06 (금)", true, false);
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2018.04.11 (수)");
                mAdapter.addItem("영어 듣기 평가 (1학년)", "2018.04.17 (화)");
                mAdapter.addItem("영어 듣기 평가 (2학년)", "2018.04.18 (수)");
                mAdapter.addItem("영어 듣기 평가 (3학년)", "2018.04.19 (목)");
                mAdapter.addItem("1학기 중간고사", "2018.04.25 (수)");
                mAdapter.addItem("1학기 중간고사", "2018.04.26 (목)");
                mAdapter.addItem("1학기 중간고사, 퇴사", "2018.04.27 (금)", true, false);
                break;
            case 5:
                mAdapter.addItem("진로 인성 캠프 (1학년)", "2018.05.02 (수)");
                mAdapter.addItem("진로 인성 캠프 (1학년), 국제교류 시작 (2학년)", "2018.05.03 (목)");
                mAdapter.addItem("진로 인성 캠프 (1학년), 체험학습 (3학년), 퇴사", "2018.05.04 (금)", true, false);
                mAdapter.addItem("대체공휴일", "2018.05.07 (월)", false, true);
                mAdapter.addItem("1학년 퇴사, 3학년 잔류", "2018.05.11 (금)", true, false);
                mAdapter.addItem("국제교류 종료 (2학년)", "2018.05.12 (토)");
                mAdapter.addItem("퇴사", "2018.05.18 (금)", true, false);
                mAdapter.addItem("개교기념일", "2018.05.21 (월)", false, true);
                mAdapter.addItem("석가탄신일", "2018.05.22 (화)", false, true);
                mAdapter.addItem("입학설명회", "2018.05.26 (토)");
                break;
            case 6:
                mAdapter.addItem("퇴사", "2018.06.01 (금)", true, false);
                mAdapter.addItem("현충일", "2018.06.06 (수)", false, true);
                mAdapter.addItem("전국 연합 모의고사 (1,2학년), 모의 수능 (3학년)", "2018.06.07 (목)");
                mAdapter.addItem("퇴사", "2018.06.08 (금)", true, false);
                mAdapter.addItem("지방선거일", "2018.06.13 (수)", false, true);
                mAdapter.addItem("입학설명회", "2018.06.14 (목)");
                break;
            case 7:
                mAdapter.addItem("1학기 기말고사 ", "2018.07.03 (화)");
                mAdapter.addItem("1학기 기말고사", "2018.07.04 (수)");
                mAdapter.addItem("1학기 기말고사", "2018.07.05 (목)");
                mAdapter.addItem("1학기 기말고사, 퇴사", "2018.07.06 (금)", true, false);
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2018.07.11 (수)");
                mAdapter.addItem("퇴사", "2018.07.13 (금)", true, false);
                mAdapter.addItem("방학식, 퇴사", "2018.07.20 (금)", true, false);
                break;
            case 8:
                mAdapter.addItem("개학식 (3학년)", "2018.08.07 (화)");
                mAdapter.addItem("개학식 (1,2학년)", "2018.08.09 (목)");
                mAdapter.addItem("현충일", "2018.08.15 (수)", false, true);
                mAdapter.addItem("퇴사", "2018.08.17 (금)", true, false);
                mAdapter.addItem("퇴사", "2018.08.31 (금)", true, false);
                break;
            case 9:
                mAdapter.addItem("모의 수능 (3학년)", "2018.09.05 (수)");
                mAdapter.addItem("퇴사", "2018.09.14 (금)", true, false);
                mAdapter.addItem("영어듣기평가 (1학년)", "2018.09.18 (화)");
                mAdapter.addItem("영어듣기평가 (2학년)", "2018.09.19 (수)");
                mAdapter.addItem("영어듣기평가 (3학년), 기숙사 화재 대피 훈련", "2018.09.20 (목)");
                mAdapter.addItem("퇴사", "2018.09.21 (금)", true, false);
                mAdapter.addItem("추석", "2018.09.24 (월)", false, true);
                mAdapter.addItem("추석", "2018.09.25 (화)", false, true);
                mAdapter.addItem("대체공휴일", "2018.09.26 (수)", false, true);
                break;
            case 10:
                mAdapter.addItem("2학기 중간고사", "2018.10.02 (화)");
                mAdapter.addItem("개천절", "2018.10.03 (수)", false, true);
                mAdapter.addItem("2학기 중간고사", "2018.10.04 (목)");
                mAdapter.addItem("2학기 중간고사, 퇴사", "2018.10.05 (금)", true, false);
                mAdapter.addItem("재량휴업일", "2018.10.08 (월)", false, true);
                mAdapter.addItem("한글날", "2018.10.09 (화)", false, true);
                mAdapter.addItem("체육대회 (1,2학년), 퇴사", "2018.10.12 (금)", true, false);
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2018.10.16 (화)");
                mAdapter.addItem("퇴사", "2018.10.26 (금)", true, false);
                break;
            case 11:
                mAdapter.addItem("퇴사", "2018.11.02 (금)", true, false);
                mAdapter.addItem("수능", "2018.11.15 (목)");
                mAdapter.addItem("퇴사", "2018.11.16 (금)", true, false);
                mAdapter.addItem("2학기 기말고사 (3학년)", "2018.11.19 (월)");
                mAdapter.addItem("2학기 기말고사 (3학년)", "2018.11.20 (화)");
                mAdapter.addItem("전국 연합 모의고사 (1,2학년), 2학기 기말고사 (3학년)", "2018.11.21 (수)");
                mAdapter.addItem("2학기 기말고사 (3학년)", "2018.11.22 (목)");
                mAdapter.addItem("2학기 기말고사, 퇴사 (3학년), 잔류 (1,2학년)", "2018.11.23 (금)", true, false);
                mAdapter.addItem("잔류 (1,2학년), 3학년 영구퇴사", "2018.11.30 (금)", true, false);
                break;
            case 12:
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2018.12.04 (화)");
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2018.12.05 (수)");
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2018.12.06 (목)");
                mAdapter.addItem("2학기 기말고사 (1,2학년), 퇴사", "2018.12.07 (금)", true, false);
                mAdapter.addItem("퇴사", "2018.12.14 (금)", true, false);
                mAdapter.addItem("세계문화축제", "2018.12.20 (목)");
                mAdapter.addItem("퇴사", "2018.12.21 (금)", true, false);
                mAdapter.addItem("재량휴업일", "2018.12.24 (월)", true, false);
                mAdapter.addItem("크리스마스", "2018.12.25 (화)", false, true);
                mAdapter.addItem("솔빛오름제", "2018.12.27 (목)");
                mAdapter.addItem("방학식, 퇴사", "2018.12.28 (금)", true, false);
                break;
            case 1:
                mAdapter.addItem("보충수업 시작 (1,2학년)", "2019.01.07 (월)");
                mAdapter.addItem("퇴사", "2019.01.11 (금)", true, false);
                mAdapter.addItem("퇴사", "2019.01.18 (금)", true, false);
                mAdapter.addItem("보충수업 종료 (1,2학년), 퇴사", "2019.01.24 (목)");
                mAdapter.addItem("개학식 (1,2학년)", "2019.01.28 (월)");
                mAdapter.addItem("개학식 (3학년)", "2019.01.30 (수)");
                mAdapter.addItem("종업식 (1,2학년), 졸업식 (3학년), 퇴사", "2019.01.31 (목)", true, false);
                break;
            case 2:
                mAdapter.addItem("집중상담기간 & 솔스타트 시작", "2019.02.18 (월)");
                mAdapter.addItem("집중상담기간 & 솔스타트 종료, 퇴사", "2019.02.20 (수)", true, false);
                break;
        }

        return recyclerView;
    }
}
