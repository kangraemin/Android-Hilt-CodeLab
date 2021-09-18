/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.R
import com.example.android.hilt.data.Log
import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.di.InMemoryLogger
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */


/*
Android 클래스에 @AndroidEntryPoint 주석을 달면 Android 클래스 수명 주기를 따르는 종속 항목 컨테이너가 생성됩니다.

Hilt는 현재 Android 유형 중 Application(@HiltAndroidApp 사용), Activity, Fragment, View, Service, BroadcastReceiver를 지원합니다.

Hilt는 FragmentActivity(예: AppCompatActivity)를 확장하는 활동, 그리고 Android 플랫폼의 Fragment(현재 지원 중단됨)가 아닌 Jetpack 라이브러리 Fragment를 확장하는 프래그먼트만 지원합니다.
*/
@AndroidEntryPoint
class LogsFragment : Fragment() {

    /*
    @Inject 주석을 사용하여 Hilt에서 삽입하려는 다른 유형의 인스턴스(예: logger, dateFormatter)를 필드에 삽입하도록 할 수 있습니다.
    */
    @InMemoryLogger
    @Inject
    lateinit var logger: LoggerDataSource

    @Inject
    lateinit var dateFormatter: DateFormatter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            recyclerView.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
