package android.myc.usergenerator.adapter

import android.content.Context
import android.myc.usergenerator.R
import android.myc.usergenerator.bean.UserBean
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(
    var mContext: Context,
    var mList: MutableList<UserBean>? = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {

        const val TYPE_NORMAL_ITEM = 100000001
        const val TYPE_BOTTOM_REFRESH_ITEM = 100000002
    }

    private var opened = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == TYPE_BOTTOM_REFRESH_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.footer, parent, false)
            BottomRefreshViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_rv_layout, parent, false)
            NormalViewHolder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mList!!.size) {
            TYPE_NORMAL_ITEM
        } else {
            TYPE_BOTTOM_REFRESH_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BottomRefreshViewHolder) {

        }

        if (holder is NormalViewHolder) {
            if (opened != -1 && position != opened) {
                holder.llDetail.visibility = View.GONE
            } else if (opened == position) {
                holder.llDetail.visibility = View.VISIBLE
            }
            val bean = mList!![position]
            holder.tvName.text = bean.name.toString()
            holder.tvGender.text = bean.gender
            holder.tvBirthDate.text = bean.dob!!.date
            Glide.with(mContext).load(bean.picture!!.thumbnail).into(holder.ivProfile)
            holder.tvAddress.text = bean.location.toString()
            holder.rlInfo.setOnClickListener {
                if (holder.llDetail.visibility == View.VISIBLE) {
                    holder.llDetail.visibility = View.GONE
                    opened = -1
                } else if (holder.llDetail.visibility == View.GONE) {
                    holder.llDetail.visibility = View.VISIBLE
                    Glide.with(mContext).load(bean.picture!!.large).into(holder.ivLarge)
                    val old = opened
                    opened = position
                    if (old != -1) notifyItemChanged(old)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return if (mList != null) {
            mList!!.size + 1
        } else 0
    }


    inner class NormalViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var ivProfile: CircleImageView = itemView.findViewById(R.id.profile_image)
        var ivLarge: CircleImageView = itemView.findViewById(R.id.iv_larger)
        var tvName: TextView = itemView.findViewById(R.id.user_name)
        var tvGender: TextView = itemView.findViewById(R.id.user_gender)
        var tvBirthDate: TextView = itemView.findViewById(R.id.user_birth_date)
        var tvAddress: TextView = itemView.findViewById(R.id.address)
        var rlInfo: RelativeLayout = itemView.findViewById(R.id.info_layout)
        var llDetail: LinearLayout = itemView.findViewById(R.id.detail_layout)

    }


    inner class BottomRefreshViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}