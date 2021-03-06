package layout.com.anew.easyItalian

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.View
import com.example.youngkaaa.ycircleview.CircleView
import kotlinx.android.synthetic.main.content_main.*
import layout.com.anew.easyItalian.read.ReadActivity
import layout.com.anew.easyItalian.recite.ReciteWordAcitivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {

      //  val login = Intent(this,LoginActivity::class.java)
     //   startActivity(login)



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // find parent view to make profile clickable on first call
        // CHOICE make the profile to a circle
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val profile = headerView.findViewById<CircleView>(R.id.profile_picture) as CircleView

        profile.setOnClickListener(){
            Toast.makeText(this,"call 个人资料 activity",Toast.LENGTH_SHORT).show()

            //the following part to call PersonalInfoActivity
            val changeToPersonalInfoActivity = Intent()
            changeToPersonalInfoActivity.setClass(this,PersonalInfo::class.java)
            startActivity(changeToPersonalInfoActivity)


            drawer_layout.closeDrawer(GravityCompat.START) // close side bar
        }


        //search_new_word & learn_button & read_button

        searchWordButton.setOnClickListener(){
           Toast.makeText(this,"call 查单词 activity",Toast.LENGTH_SHORT).show()

        }
        buttonForLearn.setOnClickListener(){

         //   val changeToRecitePage = Intent()

         //   val firstReciteWord = ReciteWord()
         //   changeToRecitePage.setClass(this,firstReciteWord::class.java)
          //  startActivity(changeToRecitePage)

          //  Toast.makeText(this,"call 背单词 activity",Toast.LENGTH_SHORT).show()
            val changeToRecite = Intent()
            changeToRecite.setClass(this, ReciteWordAcitivity::class.java)
            startActivity(changeToRecite)
        }
        buttonForRead.setOnClickListener(){
           Toast.makeText(this,"call 读美文 activity",Toast.LENGTH_SHORT).show()
           val changeToRead = Intent();
            changeToRead.setClass(this, ReadActivity::class.java)
           startActivity(changeToRead)
        }
        //end

        // make sure the nav_view open&close in time
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_new_word -> {
                val changeToNewWords = Intent()
                changeToNewWords.setClass(this, WordsInList::class.java)
                startActivity(changeToNewWords)
                    Toast.makeText(this,"call 生词本 activity",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_finished_word-> {
                // new word ? from lyj
                val changeToNewWords = Intent()
                changeToNewWords.setClass(this, WordsInList::class.java)
                startActivity(changeToNewWords)
                Toast.makeText(this,"call 已完成单词 activity",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_coming_word -> {
                // same as above
               val changeToNewWords = Intent()
               changeToNewWords.setClass(this, WordsInList::class.java)
               startActivity(changeToNewWords)
                Toast.makeText(this,"call 未背单词 activity",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_setting -> {
                // setting from lyj
                val changeToSetting = Intent()
                changeToSetting.setClass(this, SettingActivity::class.java)
                startActivity(changeToSetting)
                Toast.makeText(this,"call 设置 activity",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                Toast.makeText(this,"call 关于 activity",Toast.LENGTH_SHORT).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
