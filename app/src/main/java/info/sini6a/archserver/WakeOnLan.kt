package info.sini6a.archserver

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.IOException
import java.lang.Exception
import java.net.*
import java.nio.channels.IllegalBlockingModeException

@ExperimentalUnsignedTypes
suspend fun wakeup(ip: String, mac : String, port : Int = 9) : Boolean {
    return GlobalScope.async(Dispatchers.IO) {
        var status : Boolean = false
        try {
            val macBytes = getMacBytes(mac)
            val bytes = UByteArray(size = 6 + 16 * macBytes.size)
            macBytes.forEachIndexed { index, _ ->
                bytes[index] = 0xff.toUByte()
            }

            for (i in 1..16) {
                macBytes.copyInto(bytes, i * 6, 0, macBytes.size)
            }


            try {
                val address = InetAddress.getByName(ip)
                val packet = DatagramPacket(bytes.toByteArray(), bytes.size, address, port)
                val socket = DatagramSocket()
                socket.send(packet)
                socket.close()
                println(bytes)
                println("Wake-on-lan packets send!")
                status = true
            } catch (e: UnknownHostException) {
                println("No accessible IP Address found: $e")
            } catch (e: SecurityException) {
                println("Security exception occurred: $e")
            } catch (e: SocketException) {
                println("Socket could not be opened or bound to specified port: $e")
            } catch (e: IOException) {
                println("Input/Output error: $e")
            } catch (e: PortUnreachableException) {
                println("Port is not reachable: $e")
            } catch (e: IllegalBlockingModeException) {
                println("Illegal blocking mode exception: $e")
            } catch (e: java.lang.IllegalArgumentException) {
                println("Illegal argument exception: $e")
            } catch (e: Exception) {
                println("Unknown error occurred: $e")
            }

        } catch (e: Exception) {
            println("Failed to send Wake-on-LAN packets: $e")
        }

        return@async status
    }.await()
}

@ExperimentalUnsignedTypes
private fun getMacBytes(mac : String) : UByteArray {
    val bytes : UByteArray = UByteArray(6)
    val hex : List<String> = mac.split(":", "-")
    if(hex.size != 6) {
        println(hex.size)
        println(hex)
        throw IllegalArgumentException("Invalid MAC Address.")
    }
    try {
        hex.forEachIndexed { index, _ ->
            println(hex)
            bytes.set(index, hex.get(index).toUByte(16))
            println("Byte: ${bytes[index]}")
        }
    } catch (e : NumberFormatException) {
        throw IllegalArgumentException("Invalid hex digit in MAC address.");
    }

    return bytes
}