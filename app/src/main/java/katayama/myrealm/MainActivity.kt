package katayama.myrealm

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import io.realm.Realm
import io.realm.RealmResults
import katayama.myrealm.adapter.UserAdapter
import katayama.myrealm.model.User





class MainActivity: AppCompatActivity(), View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private var realm: Realm? = null
    private var listview: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization to the Realm
        Realm.init(this)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(this)

        // ListView
        listview = findViewById(R.id.mlistView)
        val mUserAdapter = UserAdapter(this@MainActivity)

        // ListViewを押したときの処理
        listview!!.setOnItemClickListener(this)

        // ListViewを長押ししたときの処理
        listview!!.setOnItemLongClickListener(this)

        reloadListView(mUserAdapter)

    }

    /**
     * Realm close
     **/
    override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

    override fun onClick(v: View) {
        val intent = Intent(this, RegistActivity::class.java)
        when (v) {
            findViewById<View>(R.id.fab) ->
                startActivity(intent)
        }


    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {

        realm = Realm.getDefaultInstance()
        val mUserAdapter = UserAdapter(this@MainActivity)
        // タスクを削除する
        val user = parent!!.adapter.getItem(position) as User

        // ダイアログを表示する
        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("削除")
        builder.setMessage(user.title + "を削除しますか")
        builder.setPositiveButton("OK", DialogInterface.OnClickListener() { dialog, which ->
            val results = realm!!.where(User::class.java).equalTo("id", user.id).findAll()

            realm!!.beginTransaction()
            results.deleteAllFromRealm()
            realm!!.commitTransaction()

            reloadListView(mUserAdapter)
        })
        builder.setNegativeButton("CANCEL", null)

        val dialog = builder.create()
        dialog.show()

        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var user = parent!!.adapter.getItem(position) as User

        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("User", user.id)
        startActivity(intent)
    }

    private fun reloadListView(mUserAdapter: UserAdapter) {
        // Male to the reference realm by default
        val realm = Realm.getDefaultInstance()

        val userRealmList:RealmResults<User> = realm!!.where(User::class.java).findAll()

        mUserAdapter.setUserList(realm.copyFromRealm(userRealmList))
        listview!!.adapter = mUserAdapter
        mUserAdapter.notifyDataSetChanged()
    }

}
