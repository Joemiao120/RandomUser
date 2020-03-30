package android.myc.usergenerator.bean

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class UserBean {
    //    @PrimaryKey(autoGenerate = true)
//    var uid: Long = 0

    var gender: String? = null
    @PrimaryKey
    @NonNull
    @Embedded(prefix = "user_")
    var name: Name? = null
    @Embedded
    var location: Location? = null
    var email: String? = null
    @Embedded
    var login: Login? = null
    @Embedded(prefix = "dob_")
    var dob: Registered? = null
    @Embedded(prefix = "reg_")
    var registered: Registered? = null
    var phone: String? = null
    var cell: String? = null
    @Embedded
    var id: Id? = null
    @Embedded
    var picture: Picture? = null
    var nat: String? = null


    class Picture {
        var large: String? = null
        var medium: String? = null
        var thumbnail: String? = null
    }


    class Name {
        @NonNull
        var title: String? = null
        @NonNull
        var first: String? = null
        @NonNull
        var last: String? = null

        override fun toString(): String {
            return "$title $first $last"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Name

            if (title != other.title) return false
            if (first != other.first) return false
            if (last != other.last) return false

            return true
        }
    }

    class Location {
        @Embedded
        var street: Street? = null
        var city: String? = null
        var state: String? = null
        var country: String? = null
        var postcode: String? = null


        class Street {
            var number: Long? = 0
            @ColumnInfo(name = "street_name")
            var name: String? = null

            override fun toString(): String {
                return "$number $name"
            }

        }

        override fun toString(): String {
            return "$street \n $city $state \n $country $postcode"
        }
    }

    class Registered {
        var date: String? = null
        var age: Int? = 0
    }

    class Id {
        @ColumnInfo(name = "id_name")
        var name: String? = null
        var value: String? = null
    }

    class Login {
        var uuid: String? = null
        var username: String? = null
        var password: String? = null
        var md5: String? = null
        var sha1: String? = null
        var sha256: String? = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserBean

//        if (uid != other.uid) return false
        if (gender != other.gender) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (cell != other.cell) return false
        if (id != other.id) return false
        if (picture != other.picture) return false

        return true
    }

}







