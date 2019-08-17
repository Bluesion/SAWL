package com.charlie.sawl.card

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.charlie.sawl.R
import java.io.IOException

class Card : Fragment() {

    private var nfcButton: MaterialButton? = null
    private var status: TextView? = null
    private var company: TextView? = null
    private var money: TextView? = null
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private lateinit var waitingLayout: LinearLayout
    private lateinit var cardLayout: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_card, container, false)

        waitingLayout = rootView.findViewById(R.id.waiting)
        cardLayout = rootView.findViewById(R.id.info)
        status = rootView.findViewById(R.id.status)
        nfcButton = rootView.findViewById(R.id.nfcButton)
        company = rootView.findViewById(R.id.company)
        money = rootView.findViewById(R.id.money)
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        val localIntent = Intent(activity, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(activity, 0, localIntent, 0)

        return rootView
    }

    private fun getHexToDec(paramString: String): String {
        return java.lang.Long.parseLong(paramString, 16).toString()
    }

    private fun toHexString(paramArrayOfByte: ByteArray, paramInt: Int): String {
        val localStringBuilder = StringBuilder()
        for (i in 0 until paramInt)
            localStringBuilder.append("0123456789ABCDEF"[0xF and (paramArrayOfByte[i].toInt() shr 4)]).append("0123456789ABCDEF"[0xF and paramArrayOfByte[i].toInt()])
        return localStringBuilder.toString()
    }

    private fun syncData(paramIntent: Intent) {
        val localTag = paramIntent.getParcelableExtra<Tag>("android.nfc.extra.TAG")
        if (localTag != null) {
            val localIsoDep = IsoDep.get(localTag)
            val arrayOfByte1 = byteArrayOf(0, -92, 4, 0, 7, -44, 16, 0, 0, 3, 0, 1)
            val arrayOfByte2 = byteArrayOf(0, -92, 4, 0, 7, -44, 16, 0, 0, 20, 0, 1)
            val arrayOfByte3 = byteArrayOf(-112, 76, 0, 0, 4)
            try {
                localIsoDep.connect()
                val arrayOfByte4 = localIsoDep.transceive(arrayOfByte1)
                val arrayOfByte5 = localIsoDep.transceive(arrayOfByte2)
                val arrayOfByte6 = localIsoDep.transceive(arrayOfByte3)
                val arrayOfByte7 = localIsoDep.transceive(arrayOfByte3)
                if (arrayOfByte4[-2 + arrayOfByte4.size].toInt() == -112 && arrayOfByte4[-1 + arrayOfByte4.size].toInt() == 0) {
                    val str5 = getHexToDec(toHexString(arrayOfByte7, -2 + arrayOfByte7.size))
                    waitingLayout.visibility = View.GONE
                    cardLayout.visibility = View.VISIBLE
                    company!!.text = activity!!.getText(R.string.tmoney)
                    money!!.text = String.format(getText(R.string.format).toString(), str5)
                    localIsoDep.close()
                } else if (arrayOfByte5[-2 + arrayOfByte5.size].toInt() != -112 || arrayOfByte5[-1 + arrayOfByte5.size].toInt() != 0) {
                    localIsoDep.connect()
                    val str3 = getHexToDec(toHexString(arrayOfByte6, -2 + arrayOfByte6.size))
                    waitingLayout.visibility = View.GONE
                    cardLayout.visibility = View.VISIBLE
                    company!!.text = activity!!.getText(R.string.cashbee)
                    money!!.text = String.format(getText(R.string.format).toString(), str3)
                    localIsoDep.close()
                } else {
                    localIsoDep.connect()
                    waitingLayout.visibility = View.GONE
                    cardLayout.visibility = View.VISIBLE
                    company!!.text = activity!!.getText(R.string.unknown)
                    money!!.text = activity!!.getText(R.string.unsupport)
                    localIsoDep.close()
                }
            } catch (e: IOException) {
                status!!.text = activity!!.getText(R.string.wait)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        nfcCheck()
        if (nfcAdapter != null) {
            nfcAdapter!!.enableForegroundDispatch(activity, pendingIntent, null, null)
            syncData(activity!!.intent)
        }
    }

    private fun nfcCheck() {
        if (!NfcAdapter.getDefaultAdapter(activity).isEnabled) {
            status!!.text = activity!!.getText(R.string.card_message_1)
            nfcButton!!.setOnClickListener {
                val localIntent1 = Intent("android.settings.NFC_SETTINGS")
                activity!!.startActivity(localIntent1)
            }
        } else {
            waitingLayout.visibility = View.GONE
            cardLayout.visibility = View.VISIBLE
        }
    }
}