package ru.nsu.alphacontest.ui

import android.hardware.SensorListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.nsu.alphacontest.R


class MainActivity : AppCompatActivity(R.layout.activity_main), SensorListener {

    private var lastUpdate = System.currentTimeMillis()
    private var x = 0f
    private var y = 0f
    private var z = 0f
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorMgr = getSystemService(SENSOR_SERVICE) as? SensorManager
        sensorMgr?.registerListener( this,
            SensorManager.SENSOR_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_GAME);
    }


    override fun onSensorChanged(sensor: Int, values: FloatArray) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            // only allow one update every 100ms.
            if (curTime - lastUpdate > 100) {
                val diffTime: Long = curTime - lastUpdate
                lastUpdate = curTime
                x = values[SensorManager.DATA_X]
                y = values[SensorManager.DATA_Y]
                z = values[SensorManager.DATA_Z]
                val speed: Float = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000
                if (speed > SHAKE_THRESHOLD) {
                    Log.d("sensor", "shake detected w/ speed: $speed")
                    val request =
                        NavDeepLinkRequest.Builder.fromUri("alfa-cards://profile_fragment".toUri()).build()
                    findNavController(R.id.nav_host_fragment).navigate(
                        request = request,
                    )
                }
                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }

    override fun onAccuracyChanged(sensor: Int, accuracy: Int) {
    }

    companion object {
        const val SHAKE_THRESHOLD = 7000
    }
}