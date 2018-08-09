package com.charlie.sawl.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.charlie.sawl.R;
import com.charlie.sawl.expandableview.CategoryAdapter;
import com.charlie.sawl.expandableview.CategoryList;
import com.charlie.sawl.expandableview.Contents;
import com.charlie.sawl.settings.theme.ThemeChanger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Notice extends AppCompatActivity {

    CategoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.setAppTheme(this);
        setContentView(R.layout.activity_notice);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Contents one_0 = new Contents("SAWL 앱은 안정성을 최우선 목표로 제작되었습니다. 최근 구글 정책을 참고하여 Android OS 4.4 (API 19) 미만 버전은 안정적으로 앱을 사용할 수 없다는 결론하에 API 19 이상의 OS에서만 SAWL 앱을 지원합니다.", "또한 개발 비용의 문제로 인해 안드로이드가 아닌 다른 OS에서의 SAWL 애플리케이션 제작 계획은 없습니다.");

        Contents two_0 = new Contents("1. android.permission.ACCESS_NETWORK_STATE", ": 저장된 급식 불러오기");
        Contents two_1 = new Contents("2. android.permission.INTERNET", ": 급식 다운로드");
        Contents two_2 = new Contents("3. android.permission.NFC", ": 버스카드 잔액 조회");
        Contents two_3 = new Contents("4. android.permission.RECEIVE_BOOT_COMPLETED", ": 위젯 기능");
        Contents two_4 = new Contents("5. android.permission.WRITE_EXTERNAL_STORAGE", ": 개인정보 초기화 기능");

        Contents three_0 = new Contents("급식 별점 기능은 이용률 저조와 개인정보 문제 때문에 4.0 버전부터 삭제되었습니다.", "한국 시간 기준, 2018년 7월 1일 0시부터 급식 별점 기능을 지원하지 않습니다. DB를 서버에서 완전히 삭제하였기 때문에 구버전 앱의 강제종료 현상이 발생할 수 있으니, 꼭 앱은 4.0 이상 버전으로 유지해주세요.");

        Contents update_0 = new Contents("1. 위젯 개편", "급식+시간표 통합 위젯이 삭제되고, 기존의 급식과 시간표 위젯은 ListView를 사용하여 보기 쉬워졌고, 새로고침 버튼도 추가하였습니다.");
        Contents update_1 = new Contents("2. 메모 기능 개편", "메모 기능이 대폭 개편되었습니다. 디자인을 갈아엎고, 새로운 기능들도 추가하였습니다. 또한 기존에 제대로 작동이 되지 않았던 알람 기능도 고쳤습니다.");
        Contents update_2 = new Contents("3. Adaptive Icon 지원", "안드로이드 8.0 이상인 기기에서 Adaptive Icon을 지원합니다. 기존 스퀘어클 모양이 질리셨다면, 이제 둥글둥글 원형 모양의 앱 아이콘을 감상해보세요.");
        Contents update_3 = new Contents("4. 홈페이지 파싱 개선", "학교 홈페이지 게시물이 제대로 보이지 않았던 버그를 수정하고 디자인을 개선하였습니다.");
        Contents update_4 = new Contents("5. 이미지 확장자 변경", "기존 이미지들을 벡터 이미지와 webp 이미지로 변환하여 앱의 크기를 줄이고 여러 기기에 맞는 유니버셜 디자인에 한 발자국 더 다가섰습니다.");
        Contents update_5 = new Contents("6. 라이브러리 업데이트", "앱에 사용된 라이브러리들을 업데이트하여 앱 안정성을 높였습니다.");
        Contents comment = new Contents("<개발자 코멘트>", "업데이트가 자꾸 늦어서 죄송합니다. 항상 늦는 업데이트를 기다려주셔서 정말로 감사합니다. 이번 업데이트는 디자인 쪽에 초점을 맞추고 진행하였습니다.");

        CategoryList notice_1 = new CategoryList("안드로이드 4.4 미만 / 타 OS 지원", Collections.singletonList(one_0));
        CategoryList notice_2 = new CategoryList("이 권한은 왜 필요한가요?", Arrays.asList(two_0, two_1, two_2, two_3, two_4));
        CategoryList notice_3 = new CategoryList("급식 별점 기능 공지", Collections.singletonList(three_0));
        CategoryList changelog = new CategoryList("4.5 업데이트 목록", Arrays.asList(update_0, update_1, update_2, update_3, update_4, update_5, comment));

        List<CategoryList> ContentsCategories = Arrays.asList(changelog, notice_3, notice_2, notice_1);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Notice.this));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAdapter = new CategoryAdapter(this, ContentsCategories);
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}