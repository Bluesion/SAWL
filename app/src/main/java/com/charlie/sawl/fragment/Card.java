package com.charlie.sawl.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.charlie.sawl.R;
import java.io.IOException;
import java.util.Objects;

public class Card extends Fragment {

    private AppCompatButton nfcButton;
    private AppCompatTextView status, company, money;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    LinearLayout waitingLayout;
    RelativeLayout cardLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        waitingLayout = rootView.findViewById(R.id.waiting);
        cardLayout = rootView.findViewById(R.id.info);
        status = rootView.findViewById(R.id.status);
        nfcButton = rootView.findViewById(R.id.nfcButton);
        company = rootView.findViewById(R.id.company);
        money = rootView.findViewById(R.id.money);
        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        Intent localIntent = new Intent(getActivity(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(getActivity(), 0, localIntent, 0);

        return rootView;
    }

    private String getHexToDec(String paramString) {
        return String.valueOf(Long.parseLong(paramString, 16));
    }

    private String toHexString(byte[] paramArrayOfByte, int paramInt) {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < paramInt; i++)
            localStringBuilder.append("0123456789ABCDEF".charAt(0xF & paramArrayOfByte[i] >> 4)).append("0123456789ABCDEF".charAt(0xF & paramArrayOfByte[i]));
        return localStringBuilder.toString();
    }

    private void syncData(Intent paramIntent) {
        Tag localTag = paramIntent.getParcelableExtra("android.nfc.extra.TAG");
        if (localTag != null) {
            IsoDep localIsoDep = IsoDep.get(localTag);
            byte[] arrayOfByte1 = { 0, -92, 4, 0, 7, -44, 16, 0, 0, 3, 0, 1 };
            byte[] arrayOfByte2 = { 0, -92, 4, 0, 7, -44, 16, 0, 0, 20, 0, 1 };
            byte[] arrayOfByte3 = { -112, 76, 0, 0, 4 };
            try {
                localIsoDep.connect();
                byte[] arrayOfByte4 = localIsoDep.transceive(arrayOfByte1);
                byte[] arrayOfByte5 = localIsoDep.transceive(arrayOfByte2);
                byte[] arrayOfByte6 = localIsoDep.transceive(arrayOfByte3);
                byte[] arrayOfByte7 = localIsoDep.transceive(arrayOfByte3);
                if ((arrayOfByte4[(-2 + arrayOfByte4.length)] == -112) && (arrayOfByte4[(-1 + arrayOfByte4.length)] == 0)) {
                    String str5 = getHexToDec(toHexString(arrayOfByte7, -2 + arrayOfByte7.length));
                    waitingLayout.setVisibility(View.GONE);
                    cardLayout.setVisibility(View.VISIBLE);
                    company.setText("티머니");
                    money.setText(String.format("%s원", str5));
                    localIsoDep.close();
                } else if ((arrayOfByte5[(-2 + arrayOfByte5.length)] != -112) || (arrayOfByte5[(-1 + arrayOfByte5.length)] != 0)) {
                    localIsoDep.connect();
                    String str3 = getHexToDec(toHexString(arrayOfByte6, -2 + arrayOfByte6.length));
                    waitingLayout.setVisibility(View.GONE);
                    cardLayout.setVisibility(View.VISIBLE);
                    company.setText("캐시비");
                    money.setText(String.format("%s원", str3));
                    localIsoDep.close();
                } else {
                    localIsoDep.connect();
                    waitingLayout.setVisibility(View.GONE);
                    cardLayout.setVisibility(View.VISIBLE);
                    company.setText("알 수 없는 카드");
                    money.setText("지원하지 않는 카드입니다");
                    localIsoDep.close();
                }
            } catch (IOException e) {
                status.setText("카드를 대고 잠시만 기다려주세요.");
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    public void onResume() {
        super.onResume();
        NFCCheck();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, null, null);
            syncData(Objects.requireNonNull(getActivity()).getIntent());
        }
    }

    private void NFCCheck() {
        if (!NfcAdapter.getDefaultAdapter(getActivity()).isEnabled()) {
            status.setText("교통카드 잔액을 확인하기 위해서는 스마트폰의 NFC 기능이 활성화 되어야 합니다.\n아래 버튼을 눌러 NFC 기능을 켜주세요.");
            nfcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent localIntent1 = new Intent("android.settings.NFC_SETTINGS");
                    Objects.requireNonNull(getActivity()).startActivity(localIntent1);
                }
            });
        } else {
            waitingLayout.setVisibility(View.GONE);
            cardLayout.setVisibility(View.VISIBLE);
        }
    }
}