package com.marlonmafra.coronavirustrackingapp.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.features.home.MainActivity
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: SplashViewModelFactory
    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this, factory).get(
            SplashViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CoronaTrackingApplication.appComponent.inject(this)
        splashViewModel.load()
        splashViewModel.locations.observe(this, Observer { response ->
            goToHomeActivity(response)
        })
    }

    private fun goToHomeActivity(response: TrackingResponse) {
        startActivity(MainActivity.newInstance(this, response))
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        finish()
    }
}
