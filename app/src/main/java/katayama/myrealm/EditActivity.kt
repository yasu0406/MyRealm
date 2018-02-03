package katayama.myrealm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.realm.Realm
import katayama.myrealm.model.User

class EditActivity : AppCompatActivity(), View.OnClickListener {

    private var sendButton: Button? =null
    private var mTitle: EditText? = null
    private var mContent: EditText? = null
    private var user: User? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        // toolbar is setting
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.title_activity_edit)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        realm = Realm.getDefaultInstance()
        val intent = getIntent()
        val id = intent.getStringExtra("User")
        user = realm!!.where(User::class.java).equalTo("id", id).findFirst()
        realm!!.close()

        mTitle = findViewById(R.id.title)
        mContent = findViewById(R.id.content)
        mTitle!!.setText(user!!.title)
        mContent!!.setText(user!!.content)

        sendButton = findViewById(R.id.send_button)
        sendButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        realm = Realm.getDefaultInstance()
        realm!!.beginTransaction()

        mTitle = findViewById(R.id.title)
        mContent = findViewById(R.id.content)
        user!!.title = mTitle!!.text.toString()
        user!!.content = mContent!!.text.toString()

        realm!!.copyToRealmOrUpdate(user)
        realm!!.commitTransaction()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
