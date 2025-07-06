package com.infobee.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val appID = "278330ecdc86e12b" // Replace with your App ID
    private val region = "IN" // Replace with your App Region
    private val authKey = "f2e107c9ae6f1f2c1c6cda03944a6ad08673437c" // Replace with your Auth Key or leave blank if you are authenticating using Auth Token

    private val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
        .setRegion(region)
        .setAppId(appID)
        .setAuthKey(authKey)
        .subscribePresenceForAllUsers()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CometChatUIKit.init(this, uiKitSettings, object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(successString: String?) {

                Log.d(TAG, "Initialization completed successfully")

                loginUser()
            }

            override fun onError(e: CometChatException?) {}
        })
    }



    private fun loginUser() {
        Log.d("Login", "loginUser() called")

        CometChat.login("cometchat-uid-1", authKey, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                Log.d("Login", "Login successful: ${user.uid}")
                startActivity(Intent(this@MainActivity, TabbedActivity::class.java))
                finish() // Optional: to prevent going back to login
            }

            override fun onError(e: CometChatException?) {
                Log.e("Login", "Login failed: ${e?.message}", e)
                Toast.makeText(this@MainActivity, "Login Failed: ${e?.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun loginUser() {
//        CometChatUIKit.login("cometchat-uid-1", object : CometChat.CallbackListener<User>() {
//            override fun onSuccess(user: User) {
//
//                // Launch Tab-Based Chat Experience (Chats, Calls, Users, Groups)
//                startActivity(Intent(this@MainActivity, TabbedActivity::class.java))
//            }
//
//            override fun onError(e: CometChatException) {
//                // Handle login failure (e.g. show error message or retry)
//                Log.e("Login", "Login failed: ${e.message}")
//            }
//        })
//    }
}

