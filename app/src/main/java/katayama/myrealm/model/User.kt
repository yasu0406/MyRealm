package katayama.myrealm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by katayama on 2017/11/22.
 */
open class User(@PrimaryKey var id:String? = null, var title: String? = null, var content: String? = null): RealmObject()