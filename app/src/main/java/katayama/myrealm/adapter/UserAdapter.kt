package katayama.myrealm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import katayama.myrealm.R
import katayama.myrealm.model.User


/**
 * Created by katayama on 2017/11/25.
 */
class UserAdapter : BaseAdapter {

    private var mLayoutInflater: LayoutInflater? = null
    private var mUserList: List<User>? = null

    constructor(context: Context) {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setUserList(userList: List<User>) {
        mUserList = userList
    }

    override fun getCount(): Int {
        return mUserList!!.size
    }

    override fun getItem(position: Int): Any {
        return mUserList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = mLayoutInflater!!.inflate(R.layout.list_item, null)
        }

        val textView1: TextView = convertView!!.findViewById(R.id.title)
        val textView2: TextView = convertView!!.findViewById(R.id.content)

        textView1.setText(mUserList?.get(position)!!.title)
        textView2.setText(mUserList?.get(position)!!.content)

        return convertView
    }
}