package com.min.infiniteviewpager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BannerAdapter(private var originalList: List<DemoItemDTO>) :
    RecyclerView.Adapter<BannerAdapter.MyViewHolder>() {

    // 무한 루프를 위한 가짜 데이터 2개를 추가한 리스트
    private var loopList: List<DemoItemDTO> = getLoopListFormat(originalList)

    /**
     * View Holder
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootContainer: View
        val bannerTitle: TextView

        init {
            rootContainer = itemView.findViewById(R.id.rootContainer)
            bannerTitle = itemView.findViewById(R.id.bannerTitle)
        }

        fun onBind(item: DemoItemDTO) {
            bannerTitle.text = item.title
            rootContainer.setBackgroundColor(Color.parseColor(item.bgColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_demo, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return loopList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = loopList[position]

        holder.onBind(item)
    }

    fun getLoopListFormat(originalList: List<DemoItemDTO>): List<DemoItemDTO> {
        return if (originalList.size >= 2) {
            originalList + listOf(originalList[0], originalList[1])
        } else { // 데이터가 1개 이하일 때는 루프 필요 없음
            originalList
        }
    }

    fun setListData(originalList: List<DemoItemDTO>) {
        this.originalList = originalList
        loopList = getLoopListFormat(originalList)
        notifyDataSetChanged()
    }

    fun getLoopList(): List<DemoItemDTO> {
        return loopList
    }

    fun getOriginalList(): List<DemoItemDTO> {
        return getOriginalList()
    }
}