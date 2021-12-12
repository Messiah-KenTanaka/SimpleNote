package mycompany.example.simplenote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import mycompany.example.simplenote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 画面遷移のコードを追加する
    private lateinit var binding: ActivityMainBinding
    // 広告
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 画面遷移のコードを追加する
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        // 広告
        val adView = AdView(this)

        adView.adSize = AdSize.SMART_BANNER

        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        // ca-app-pub-3324255919208405~1639328617 本物
        // ca-app-pub-3940256099942544/6300978111 サンプル

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        // 画面遷移のコードを追加する
        val naviController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(naviController)
        binding.fab.setOnClickListener { view ->
            naviController.navigate(R.id.action_to_noteEditFragment)
        }
        //区切り線
//        val lvMenu = findViewById<RecyclerView>(R.id.list)
//        val layout = LinearLayoutManager(applicationContext)
//        val decorator = DividerItemDecoration(applicationContext, layout.orientation)
//        lvMenu.addItemDecoration(decorator)

    }
    // 画面遷移のコードを追加する
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
    // 画面遷移のコードを追加する
    fun setFabVisible(visibility: Int) {
        binding.fab.visibility = visibility
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}