package com.charlie.sawl.schedule

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charlie.sawl.R
import com.charlie.sawl.tools.RecyclerTouchListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleContents : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recyclerView = inflater.inflate(R.layout.recyclerview, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

        val mAdapter = ScheduleAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(activity!!, recyclerView, object: RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                try {
                    val date = mAdapter.getItemData(position).date
                    val mFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

                    val mCalendar = Calendar.getInstance()
                    mCalendar.time = mFormat.parse(date)
                    val touchTime = mCalendar.timeInMillis

                    var diff = touchTime - System.currentTimeMillis()

                    var isPast = false
                    if (diff < 0) {
                        diff = -diff
                        isPast = true
                    }

                    val diffDays = (diff / 86400000).toInt()
                    val futureDays = diffDays + 1
                    var mText = ""

                    when {
                        diffDays == 0 -> mText = getText(R.string.snackbar_today).toString()
                        isPast -> mText = getText(R.string.snackbar_past1).toString() + diffDays + getText(R.string.snackbar_past2).toString()
                        diffDays > 0 -> mText = getText(R.string.snackbar_future1).toString() + futureDays + getText(R.string.snackbar_future2).toString()
                    }

                    Snackbar.make(view, mText, Snackbar.LENGTH_SHORT).show()
                } catch (ignored: Exception) {}
            }

            override fun onLongClick(view: View?, position: Int) {}
        }))

        val args = arguments
        val month = args!!.getInt("month")

        when (month) {
            3 -> {
                mAdapter.addItem("3.1절", "2019.03.01 (목)", false, true)
                mAdapter.addItem("입학식", "2019.03.02 (금)")
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2019.03.08 (목)")
                mAdapter.addItem("퇴사", "2019.03.09 (금)", true, false)
                mAdapter.addItem("학부모 학교 설명회", "2019.03.16 (금)")
                mAdapter.addItem("기숙사 재난대피훈련", "2019.03.22 (목)")
                mAdapter.addItem("퇴사", "2019.03.23 (금)", true, false)
            }
            4 -> {
                mAdapter.addItem("퇴사", "2019.04.06 (금)", true, false)
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2019.04.11 (수)")
                mAdapter.addItem("영어 듣기 평가 (1학년)", "2019.04.17 (화)")
                mAdapter.addItem("영어 듣기 평가 (2학년)", "2019.04.18 (수)")
                mAdapter.addItem("영어 듣기 평가 (3학년)", "2019.04.19 (목)")
                mAdapter.addItem("1학기 중간고사", "2019.04.25 (수)")
                mAdapter.addItem("1학기 중간고사", "2019.04.26 (목)")
                mAdapter.addItem("1학기 중간고사, 퇴사", "2019.04.27 (금)", true, false)
            }
            5 -> {
                mAdapter.addItem("진로 인성 캠프 (1학년)", "2019.05.02 (수)")
                mAdapter.addItem("진로 인성 캠프 (1학년), 국제교류 시작 (2학년)", "2019.05.03 (목)")
                mAdapter.addItem("진로 인성 캠프 (1학년), 체험학습 (3학년), 퇴사", "2019.05.04 (금)", true, false)
                mAdapter.addItem("대체공휴일", "2019.05.07 (월)", false, true)
                mAdapter.addItem("1학년 퇴사, 3학년 잔류", "2019.05.11 (금)", true, false)
                mAdapter.addItem("국제교류 종료 (2학년)", "2019.05.12 (토)")
                mAdapter.addItem("퇴사", "2019.05.18 (금)", true, false)
                mAdapter.addItem("개교기념일", "2019.05.21 (월)", false, true)
                mAdapter.addItem("석가탄신일", "2019.05.22 (화)", false, true)
                mAdapter.addItem("입학설명회", "2019.05.26 (토)")
            }
            6 -> {
                mAdapter.addItem("퇴사", "2019.06.01 (금)", true, false)
                mAdapter.addItem("현충일", "2019.06.06 (수)", false, true)
                mAdapter.addItem("전국 연합 모의고사 (1,2학년), 모의 수능 (3학년)", "2019.06.07 (목)")
                mAdapter.addItem("퇴사", "2019.06.08 (금)", true, false)
                mAdapter.addItem("지방선거일", "2019.06.13 (수)", false, true)
                mAdapter.addItem("입학설명회", "2019.06.14 (목)")
            }
            7 -> {
                mAdapter.addItem("1학기 기말고사 ", "2019.07.03 (화)")
                mAdapter.addItem("1학기 기말고사", "2019.07.04 (수)")
                mAdapter.addItem("1학기 기말고사", "2019.07.05 (목)")
                mAdapter.addItem("1학기 기말고사, 퇴사", "2019.07.06 (금)", true, false)
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2019.07.11 (수)")
                mAdapter.addItem("퇴사", "2019.07.13 (금)", true, false)
                mAdapter.addItem("방학식, 퇴사", "2019.07.20 (금)", true, false)
            }
            8 -> {
                mAdapter.addItem("개학식 (3학년)", "2019.08.07 (화)")
                mAdapter.addItem("개학식 (1,2학년)", "2019.08.09 (목)")
                mAdapter.addItem("현충일", "2019.08.15 (수)", false, true)
                mAdapter.addItem("퇴사", "2019.08.17 (금)", true, false)
                mAdapter.addItem("퇴사", "2019.08.31 (금)", true, false)
            }
            9 -> {
                mAdapter.addItem("모의 수능 (3학년)", "2019.09.05 (수)")
                mAdapter.addItem("퇴사", "2019.09.14 (금)", true, false)
                mAdapter.addItem("영어듣기평가 (1학년)", "2019.09.18 (화)")
                mAdapter.addItem("영어듣기평가 (2학년)", "2019.09.19 (수)")
                mAdapter.addItem("영어듣기평가 (3학년), 기숙사 화재 대피 훈련", "2019.09.20 (목)")
                mAdapter.addItem("퇴사", "2019.09.21 (금)", true, false)
                mAdapter.addItem("추석", "2019.09.24 (월)", false, true)
                mAdapter.addItem("추석", "2019.09.25 (화)", false, true)
                mAdapter.addItem("대체공휴일", "2019.09.26 (수)", false, true)
            }
            10 -> {
                mAdapter.addItem("2학기 중간고사", "2019.10.02 (화)")
                mAdapter.addItem("개천절", "2019.10.03 (수)", false, true)
                mAdapter.addItem("2학기 중간고사", "2019.10.04 (목)")
                mAdapter.addItem("2학기 중간고사, 퇴사", "2019.10.05 (금)", true, false)
                mAdapter.addItem("재량휴업일", "2019.10.08 (월)", false, true)
                mAdapter.addItem("한글날", "2019.10.09 (화)", false, true)
                mAdapter.addItem("체육대회 (1,2학년), 퇴사", "2019.10.12 (금)", true, false)
                mAdapter.addItem("전국 연합 모의고사 (3학년)", "2019.10.16 (화)")
                mAdapter.addItem("퇴사", "2019.10.26 (금)", true, false)
            }
            11 -> {
                mAdapter.addItem("퇴사", "2019.11.02 (금)", true, false)
                mAdapter.addItem("수능", "2019.11.15 (목)")
                mAdapter.addItem("퇴사", "2019.11.16 (금)", true, false)
                mAdapter.addItem("2학기 기말고사 (3학년)", "2019.11.19 (월)")
                mAdapter.addItem("2학기 기말고사 (3학년)", "2019.11.20 (화)")
                mAdapter.addItem("전국 연합 모의고사 (1,2학년), 2학기 기말고사 (3학년)", "2019.11.21 (수)")
                mAdapter.addItem("2학기 기말고사 (3학년)", "2019.11.22 (목)")
                mAdapter.addItem("2학기 기말고사, 퇴사 (3학년), 잔류 (1,2학년)", "2019.11.23 (금)", true, false)
                mAdapter.addItem("잔류 (1,2학년), 3학년 영구퇴사", "2019.11.30 (금)", true, false)
            }
            12 -> {
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2019.12.04 (화)")
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2019.12.05 (수)")
                mAdapter.addItem("2학기 기말고사 (1,2학년)", "2019.12.06 (목)")
                mAdapter.addItem("2학기 기말고사 (1,2학년), 퇴사", "2019.12.07 (금)", true, false)
                mAdapter.addItem("퇴사", "2019.12.14 (금)", true, false)
                mAdapter.addItem("세계문화축제", "2019.12.20 (목)")
                mAdapter.addItem("퇴사", "2019.12.21 (금)", true, false)
                mAdapter.addItem("재량휴업일", "2019.12.24 (월)", true, false)
                mAdapter.addItem("크리스마스", "2019.12.25 (화)", false, true)
                mAdapter.addItem("솔빛오름제", "2019.12.27 (목)")
                mAdapter.addItem("방학식, 퇴사", "2019.12.28 (금)", true, false)
            }
            1 -> {
                mAdapter.addItem("보충수업 시작 (1,2학년)", "2020.01.07 (월)")
                mAdapter.addItem("퇴사", "2020.01.11 (금)", true, false)
                mAdapter.addItem("퇴사", "2020.01.18 (금)", true, false)
                mAdapter.addItem("보충수업 종료 (1,2학년), 퇴사", "2020.01.24 (목)")
                mAdapter.addItem("개학식 (1,2학년)", "2020.01.28 (월)")
                mAdapter.addItem("개학식 (3학년)", "2020.01.30 (수)")
                mAdapter.addItem("종업식 (1,2학년), 졸업식 (3학년), 퇴사", "2020.01.31 (목)", true, false)
            }
            2 -> {
                mAdapter.addItem("집중상담기간 & 솔스타트 시작", "2020.02.18 (월)")
                mAdapter.addItem("집중상담기간 & 솔스타트 종료, 퇴사", "2020.02.20 (수)", true, false)
            }
        }

        return recyclerView
    }

    companion object {
        fun getInstance(month: Int): Fragment {
            val mFragment = ScheduleContents()

            val args = Bundle()
            args.putInt("month", month)
            mFragment.arguments = args

            return mFragment
        }
    }
}
