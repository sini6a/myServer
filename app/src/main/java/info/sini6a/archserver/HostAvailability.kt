package info.sini6a.archserver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

suspend fun isHostOnline(ip: String?) : Boolean {
        return GlobalScope.async(Dispatchers.IO) {
            var status: Boolean
            val socketAddress = InetSocketAddress(ip, 80)
            val socket = Socket()
            val timeout = 1000

            try {
                socket.connect(socketAddress, timeout)
                status = true
            } catch (e: IOException) {
                status = false
                println("Exception: $e")
            }

            socket.close()
            println("Status: $status")

            return@async status
        }.await()

}
