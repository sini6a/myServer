package info.sini6a.archserver

import android.content.Intent
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Wake-on-LAN Settings
        val ip : String? = sharedPreferences.getString("hostname", null)
        val mac = sharedPreferences.getString("mac_address", null)
        val port = sharedPreferences.getString("port", "9")?.toInt()

        // SSH Settings
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val sshPort = sharedPreferences.getString("ssh_port", "22")?.toInt()

        setContentView(R.layout.activity_main)

        button_refresh.setOnClickListener(View.OnClickListener {
            if(ip != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, R.string.updating_status, Toast.LENGTH_LONG).show()
                    GlobalScope.launch(Dispatchers.Main) {
                        val status = isHostOnline(ip)
                        println(status)
                        if (status) {
                            textView_status.setTextColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.green
                                )
                            )
                            textView_status.text = resources.getString(R.string.online)
                        }
                        else {
                            textView_status.setTextColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.red
                                )
                            )
                            textView_status.text = resources.getString(R.string.offline)
                        }
                    }
                    Toast.makeText(applicationContext, R.string.status_updated, Toast.LENGTH_LONG).show()
                }
            } else
                Toast.makeText(applicationContext, R.string.ip_not_specified, Toast.LENGTH_LONG).show()
        })

        button_wakeUp.setOnClickListener(View.OnClickListener {
            if(ip != null && mac != null && port != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, R.string.sending_wol_packets, Toast.LENGTH_LONG).show()
                    val status = wakeup(ip = ip, mac = mac, port = port)
                    if (status) {
                        Toast.makeText(
                            applicationContext,
                            R.string.wol_packets_send,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else
                        Toast.makeText(applicationContext, R.string.wol_packets_failed, Toast.LENGTH_LONG).show()
                }
            } else
                Toast.makeText(applicationContext, R.string.wol_preferences_not_set, Toast.LENGTH_LONG).show()
        })


        button_shutdown.setOnClickListener(View.OnClickListener {
            if(ip != null && username != null && password != null && sshPort != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, R.string.sending_shutdown_cmd, Toast.LENGTH_LONG).show()
                    val status = shutdown(ip, username, password, sshPort)
                    if (status) {
                        Toast.makeText(
                            applicationContext,
                            R.string.shutdown_cmd_send,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else
                        Toast.makeText(applicationContext, R.string.shutdown_cmd_failed, Toast.LENGTH_LONG).show()
                }
            } else
                Toast.makeText(applicationContext, R.string.ssh_preferences_not_set, Toast.LENGTH_LONG).show()
        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                this.startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_donate -> {
                this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/sini6a/2eur")))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



