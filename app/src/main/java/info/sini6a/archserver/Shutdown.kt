package info.sini6a.archserver

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.lang.Exception

suspend fun shutdown(ip: String, username: String, password: String, ssh_port : Int = 22) : Boolean {
    return GlobalScope.async(Dispatchers.IO) {
        val user = username
        val host = ip
        val pwd = password
        val port = ssh_port
        var status = false

        try {
            val jsch = JSch()
            val session = jsch.getSession(user, host, port)
            session.setPassword(pwd)
            session.setConfig("StrictHostKeyChecking", "no")
            session.timeout = 10000
            session.connect()

            val channel = session.openChannel("exec") as ChannelExec
            channel.setCommand("sudo shutdown now")
            channel.setErrStream(System.out)
            channel.connect()
            delay(500)
            channel.disconnect()
            status = true
        } catch (e: JSchException) {
            println(e)
        }

        return@async status
    }.await()
}