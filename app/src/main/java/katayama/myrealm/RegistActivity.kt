package katayama.myrealm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import katayama.myrealm.model.User
import java.util.*

class RegistActivity : AppCompatActivity(), View.OnClickListener {
    private var mTitle: EditText? = null
    private var mContent: EditText? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)

        realm = Realm.getDefaultInstance()
        // toolbar is setting
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.title_activity_regist)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)



        var sendButton: Button? = findViewById(R.id.send_button)
        sendButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        // Realmをデフォルトで参照する
        realm = Realm.getDefaultInstance()
        mTitle = findViewById(R.id.title)
        mContent = findViewById(R.id.content)

        if((mTitle?.text.toString().equals("") || mTitle?.text.toString() == null) &&
                (mContent?.text.toString().equals("") || mContent?.text.toString() == null)) {

            Toast.makeText(this,"入力してください", Toast.LENGTH_SHORT).show()
        } else if(mTitle?.text.toString().equals("") || mTitle?.text.toString() == null) {

            Toast.makeText(this, "タイトルを入力してください", Toast.LENGTH_SHORT).show()
        } else if(mContent?.text.toString().equals("") || mContent?.text.toString() == null) {

            Toast.makeText(this, "テキストを入力してください", Toast.LENGTH_SHORT).show()
        } else {

            realm!!.executeTransaction {
                var user = realm!!.createObject(User::class.java, UUID.randomUUID().toString())
                // EditTextの値を取得
                user.title = mTitle?.text.toString()
                user.content = mContent?.text.toString()
                    // Realmにデータをコピー
                    realm!!.copyToRealm(user)

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Realm close
     **/
    override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

}
