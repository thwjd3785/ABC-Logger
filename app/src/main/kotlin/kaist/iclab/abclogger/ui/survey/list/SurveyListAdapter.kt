package kaist.iclab.abclogger.ui.survey.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kaist.iclab.abclogger.BR
import kaist.iclab.abclogger.R
import kaist.iclab.abclogger.SurveyEntity
import kaist.iclab.abclogger.databinding.SurveyListItemBinding

class SurveyListAdapter: PagedListAdapter<SurveyEntity, SurveyListAdapter.ViewHolder>(DIFF_CALLBACK) {
    var onItemClick : ((item: SurveyEntity?, binding: SurveyListItemBinding) -> Unit)? = null

    override fun getItemId(position: Int): Long = getItem(position)?.id ?: -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : SurveyListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.survey_list_item, parent,false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.binding.entity = item
        holder.binding.isAvailable = item.isAvailable()

        holder.setOnClick {
            onItemClick?.invoke(item, holder.binding)
        }

        ViewCompat.setTransitionName(
                holder.binding.txtHeader,
                "${PREFIX_TITLE_VIEW}_${item.id}"
        )
        ViewCompat.setTransitionName(
                holder.binding.txtMessage,
                "${PREFIX_MESSAGE_VIEW}_${item.id}"
        )
        ViewCompat.setTransitionName(
                holder.binding.txtDeliveredTime,
                "${PREFIX_DELIVERED_TIME_VIEW}_${item.id}"
        )
    }

    class ViewHolder (val binding: SurveyListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun setOnClick(onClick: (() -> Unit)? = null) {
            itemView.setOnClickListener { onClick?.invoke() }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SurveyEntity>() {
            override fun areItemsTheSame(oldItem: SurveyEntity, newItem: SurveyEntity): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SurveyEntity, newItem: SurveyEntity): Boolean = oldItem == newItem
        }

        const val PREFIX_TITLE_VIEW = "TITLE_VIEW"
        const val PREFIX_MESSAGE_VIEW = "MESSAGE_VIEW"
        const val PREFIX_DELIVERED_TIME_VIEW = "DELIVERED_TIME_VIEW"
    }
}